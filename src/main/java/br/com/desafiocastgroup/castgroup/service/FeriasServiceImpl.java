package br.com.desafiocastgroup.castgroup.service;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.desafiocastgroup.castgroup.dao.FeriasDao;
import br.com.desafiocastgroup.castgroup.dao.FuncionarioDao;
import br.com.desafiocastgroup.castgroup.exception.ProcessException;
import br.com.desafiocastgroup.castgroup.model.Ferias;
import br.com.desafiocastgroup.castgroup.model.Funcionario;
import br.com.desafiocastgroup.castgroup.util.Disco;
import br.com.desafiocastgroup.castgroup.util.Email;
import br.com.desafiocastgroup.castgroup.util.Mensagem;
import br.com.desafiocastgroup.castgroup.util.Util;

@Service
public class FeriasServiceImpl implements FeriasService{
	
	private FeriasDao feriasDao;
	private FuncionarioDao funcionarioDao;
	private MessageSource messageSource;
	private Disco disco;
	private Email email;
	
	@Autowired
	private FeriasServiceImpl(FeriasDao feriasDao, FuncionarioDao funcionarioDao, MessageSource messageSource, Disco disco, Email email) {
		this.feriasDao = feriasDao;
		this.funcionarioDao = funcionarioDao;
		this.messageSource = messageSource;
		this.disco = disco;
		this.email = email;
	}

	@Override
	public BufferedImage salvar(Ferias ferias) {
		validarFerias(ferias);
		this.feriasDao.salvar(ferias);
		BufferedImage generateQRCodeImage = gerarQrCode(ferias.getFuncionario());
		enviarQrCodePorEmail(generateQRCodeImage);
		return generateQRCodeImage;
	}

	@Override
	public List<Ferias> listarTodos() {
		return this.feriasDao.listarTodos();
	}

	@Override
	public void remover(Ferias ferias) {
		this.feriasDao.remover(ferias);
	}

	@Override
	public List<Ferias> buscarFeriasPorMatriculaFuncionario(String matricula) {
		Funcionario funcionario = this.funcionarioDao.buscarPorMatricula(matricula);
		if (funcionario != null) {
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
		removeFotoFuncionario(funcionario);
		String informacoes = Util.converterObjectToStringJson(funcionario);
		return Util.generateQRCodeImage(informacoes);
	}

	private void enviarQrCodePorEmail(BufferedImage generateQRCodeImage) {

		if (generateQRCodeImage != null) {
			
			String arquivoSalvo = this.disco.salvarQrCode(generateQRCodeImage);
			
			Mensagem mensagem = new Mensagem();
			
			mensagem.setRemetente("fakenews.desafio.empresa@gmail.com");
			mensagem.setAssunto("QR Code Funcionário Féras");
			
	        StringBuilder corpo = new StringBuilder();
	        corpo.append("<html>Olá novo funcionário de férias !!!<br>");
	        corpo.append("para mais informações aproxime a camera do seu celular para imagem abaixo.<br>");
	        corpo.append("<img src=\"cid:qrcode\" width=\"30%\" height=\"30%\" /><br>");
	        corpo.append("</html>");
			mensagem.setCorpo(corpo.toString());
			
	        Map<String, String> imagensCorpo = new HashMap<>();
	        imagensCorpo.put("qrcode", arquivoSalvo);
	        mensagem.setImagensCorpo(imagensCorpo);
	        
	        List<String> anexos = new ArrayList<>();
	        anexos.add(arquivoSalvo);
	        mensagem.setAnexos(anexos);
	        
	        List<String> destinatarios = new ArrayList<>();
	        destinatarios.add("fakenews.desafio.empresa@gmail.com");
	        mensagem.setDestinatarios(destinatarios);
	        
			this.email.enviar(mensagem);
			
		}
		
	}

	private void removeFotoFuncionario(Funcionario funcionario) {
		 
		funcionario.setCaminhoFoto("");
	}

}
