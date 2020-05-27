package br.com.desafiocastgroup.castgroup.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.module.SimpleModule;

import br.com.desafiocastgroup.castgroup.converter.FuncionarioSerializer;
import br.com.desafiocastgroup.castgroup.dao.FeriasDao;
import br.com.desafiocastgroup.castgroup.exception.ProcessException;
import br.com.desafiocastgroup.castgroup.model.Ferias;
import br.com.desafiocastgroup.castgroup.model.Funcionario;
import br.com.desafiocastgroup.castgroup.util.Arquivo;
import br.com.desafiocastgroup.castgroup.util.Email;
import br.com.desafiocastgroup.castgroup.util.Mensagem;
import br.com.desafiocastgroup.castgroup.util.Util;

@Service
public class FeriasServiceImpl implements FeriasService{
	
	private FeriasDao feriasDao;
	private FuncionarioService funcionarioService;
	private MessageSource messageSource;
	private Email email;
	@Value("${mail.rementente}")
	private String remetente;
	@Value("${mail.destinatario}")
	private String destinatario;
	
	@Autowired
	public FeriasServiceImpl(FeriasDao feriasDao, FuncionarioService funcionarioService, MessageSource messageSource, Email email) {
		this.feriasDao = feriasDao;
		this.funcionarioService = funcionarioService;
		this.messageSource = messageSource;
		this.email = email;
	}

	@Override
	public BufferedImage salvar(Ferias ferias) {
		validarFerias(ferias);
		this.feriasDao.salvar(ferias);
		Funcionario funcionario = this.funcionarioService.buscarPorId(ferias.getFuncionario().getId());
		funcionario.getFerias().add(ferias);
		BufferedImage generateQRCodeImage = gerarQrCode(funcionario);
		enviarQrCodePorEmail(generateQRCodeImage);
		return generateQRCodeImage;
	}

	@Override
	public List<Ferias> listarTodos() {
		List<Ferias> ferias = this.feriasDao.listarTodos();
		if (Util.isListaVazia(ferias)) {
			throw new ProcessException(HttpStatus.NOT_FOUND, messageSource.getMessage("ferias.notfound", null, Locale.getDefault()));
		}
		return this.feriasDao.listarTodos();
	}

	@Override
	public void remover(Long id) {
		if (!Util.islongNull(id)) {
			this.feriasDao.remover(id);
		}
	}

	@Override
	public List<Ferias> buscarFeriasPorMatriculaFuncionario(String matricula) {
		
		if (!Util.isStringVazia(matricula)) {
		
			Funcionario funcionario = this.funcionarioService.buscarPorMatricula(matricula);
			
			if (funcionario == null || Util.isListaVazia(funcionario.getFerias())) {
				throw new ProcessException(HttpStatus.NOT_FOUND, messageSource.getMessage("ferias.notfound", null, Locale.getDefault()));
			}
			
			return funcionario.getFerias();
		}
		return new ArrayList<>();
	}
	
	private void validarFerias(Ferias ferias) {
		
		// 1 ano
		final int limiteMinimoFerias = 12;
		Funcionario funcionario = ferias.getFuncionario();
		
		if (funcionario == null || Util.buscarDiferencaEntreDatasEmMeses(funcionario.getDataContratacao(),LocalDate.now()) < limiteMinimoFerias) {
			throw new ProcessException(HttpStatus.UNPROCESSABLE_ENTITY, messageSource.getMessage("ferias.limite.minimo", null, Locale.getDefault()));
		}
		
		Ferias ultimaFerias = buscarUltimaFerias(funcionario.getFerias());
		
		if (ultimaFerias != null && Util.buscarDiferencaEntreDatasEmMeses(ultimaFerias.getDataFim(),ferias.getDataInicio()) < limiteMinimoFerias) {
			throw new ProcessException(HttpStatus.UNPROCESSABLE_ENTITY, messageSource.getMessage("ferias.intervalo.minimo", null, Locale.getDefault()));
		}
		
		if (buscarQuantidadeFuncionariosEquipe(funcionario) <= 4 && !validarDiaFeriasCoincidenteFuncionarios(ferias,buscarFuncionariosEquipe(funcionario))) {
			throw new ProcessException(HttpStatus.UNPROCESSABLE_ENTITY, messageSource.getMessage("ferias.dias.coincidente", null, Locale.getDefault()));
		}
		
	}

	private boolean validarDiaFeriasCoincidenteFuncionarios(Ferias ferias,
			List<Funcionario> listaFuncionarios) {
		
		if (Util.isListaVazia(listaFuncionarios)) {
			return true;
		}
		
		for (Funcionario funcionario : listaFuncionarios) {
			
			if (!funcionario.equals(ferias.getFuncionario()) && !Util.isListaVazia(funcionario.getFerias())) {
				
				Ferias feriasFuncionarioEquipe = buscarUltimaFerias(funcionario.getFerias());
				
				if (feriasFuncionarioEquipe != null &&
					((Util.compararData(ferias.getDataInicio(), feriasFuncionarioEquipe.getDataInicio()) >= 0 && Util.compararData(ferias.getDataInicio(), feriasFuncionarioEquipe.getDataFim()) <= 0) 
					|| (Util.compararData(ferias.getDataFim(), feriasFuncionarioEquipe.getDataInicio()) >= 0 && Util.compararData(ferias.getDataFim(), feriasFuncionarioEquipe.getDataFim()) <= 0))) {
					
					return false;
					
				}
				
			}
			
		}
		
		return true;
	}

	private List<Funcionario> buscarFuncionariosEquipe(Funcionario funcionario) {
		
		if (funcionario.getEquipe() != null && !Util.isListaVazia(funcionario.getEquipe().getFuncionarios())) {
			return funcionario.getEquipe().getFuncionarios();
		}
		
		return new ArrayList<>();
	}

	private int buscarQuantidadeFuncionariosEquipe(Funcionario funcionario) {
		if (funcionario.getEquipe() != null && !Util.isListaVazia(funcionario.getEquipe().getFuncionarios())) {
			return funcionario.getEquipe().getFuncionarios().size();
		}
		
		return 0;
	}
	
	private Ferias buscarUltimaFerias(List<Ferias> listaferias) {
		
		if (!Util.isListaVazia(listaferias)) {
			int tamanho = listaferias.size();
			return listaferias.get(tamanho - 1);
		}
		
		return null;
	}
	
	private BufferedImage gerarQrCode(Funcionario funcionario) {
		List<String> exclusoes = new ArrayList<>();
		exclusoes.add("foto");
		exclusoes.add("ferias");
		SimpleModule module = new SimpleModule();
		module.addSerializer(Funcionario.class, new FuncionarioSerializer());
		String informacoes = Util.converterObjectToStringJson(funcionario,exclusoes,module);
		return Util.generateQRCodeImage(informacoes);
	}

	private void enviarQrCodePorEmail(BufferedImage generateQRCodeImage) {

		if (generateQRCodeImage != null) {
			
			final String fileName = "QrCode";
			final String type = "png";
			final String contentType = "Content-Type";
			
			
			Mensagem mensagem = new Mensagem();
			
			mensagem.setRemetente(this.remetente);
			mensagem.setAssunto("Funcionário de Férias");
			
	        StringBuilder corpo = new StringBuilder();
	        corpo.append("<html>Olá novo funcionário de férias !!!<br>");
	        corpo.append("para mais informações aproxime a camera do seu celular na imagem abaixo.<br>");
	        corpo.append("<img src=\"cid:qrcode\" width=\"30%\" height=\"30%\" /><br>");
	        corpo.append("</html>");
			mensagem.setCorpo(corpo.toString());
			
			File file = Util.converterBufferedImageToFile(generateQRCodeImage, fileName, type);
			Arquivo arquivo = new Arquivo(fileName, type, contentType, file);
			
	        Map<String,Arquivo> imagensCorpo = new HashMap<>();
	        imagensCorpo.put(fileName, arquivo);
	        mensagem.setImagensCorpo(imagensCorpo);
	        
	        List<String> destinatarios = new ArrayList<>();
	        destinatarios.add(this.destinatario);
	        mensagem.setDestinatarios(destinatarios);
	        
			this.email.enviar(mensagem);
			
		}
		
	}

}
