package br.com.desafiocastgroup.castgroup.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.desafiocastgroup.castgroup.dao.FuncionarioDao;
import br.com.desafiocastgroup.castgroup.exception.ProcessException;
import br.com.desafiocastgroup.castgroup.model.Ferias;
import br.com.desafiocastgroup.castgroup.model.Funcionario;
import br.com.desafiocastgroup.castgroup.util.AwsSaver;
import br.com.desafiocastgroup.castgroup.util.Base64Image;
import br.com.desafiocastgroup.castgroup.util.Util;

@Service
public class FuncionarioServiceImpl implements FuncionarioService{

	private FuncionarioDao funcionarioDao;
	private AwsSaver fileSaver;
	private MessageSource messageSource;
	
	@Autowired
	public FuncionarioServiceImpl(FuncionarioDao funcionarioDao, AwsSaver fileSaver, MessageSource messageSource) {
		this.funcionarioDao = funcionarioDao;
		this.fileSaver = fileSaver;
		this.messageSource = messageSource;
	}
	
	@Override
	public Funcionario salvar(Funcionario funcionario) {
		gerarMatriculafuncionario(funcionario);
		salvarFotoAws(funcionario);
		return this.funcionarioDao.salvar(funcionario);
	}

	@Override
	public List<Funcionario> listarTodos() {
		List<Funcionario> funcionarios = this.funcionarioDao.listarTodos();
		if (Util.isListaVazia(funcionarios)) {
			throw new ProcessException(HttpStatus.NOT_FOUND, messageSource.getMessage("funcionario.notfound", null, Locale.getDefault()));
		}
		return funcionarios;
	}

	@Override
	public Funcionario buscarPorId(Long id) {
		Funcionario funcionario = this.funcionarioDao.buscarPorId(id);
		if (funcionario == null) {
			throw new ProcessException(HttpStatus.NOT_FOUND, messageSource.getMessage("funcionario.notfound", null, Locale.getDefault()));
		}
		return funcionario;
	}

	@Override
	public List<Funcionario> buscarFuncionariosDevemSolicitarFerias(int meses) {
		
		List<Funcionario> todosFuncionarios = this.listarTodos();
		List<Funcionario> funcionariosDevemSolicitar = new ArrayList<>();
		
		if (!Util.isListaVazia(todosFuncionarios)) {
			
			for (Funcionario funcionario : todosFuncionarios) {
				if (verificarFuncionarioDeveSolicitarFerias(funcionario,meses)) {
					funcionariosDevemSolicitar.add(funcionario);
				}
			}
			
		}
		
		return funcionariosDevemSolicitar;
	}
	
	@Override
	public Funcionario buscarPorMatricula(String matricula) {
		Funcionario funcionario = this.funcionarioDao.buscarPorMatricula(matricula);
		if (funcionario == null) {
			throw new ProcessException(HttpStatus.NOT_FOUND, messageSource.getMessage("funcionario.notfound", null, Locale.getDefault()));
		}
		return funcionario;
	}
	
	private boolean verificarFuncionarioDeveSolicitarFerias(Funcionario funcionario, int meses) {
		
		//2 anos
		final int limiteFerias = 24;
		Ferias ferias = buscarUltimaFerias(funcionario);
		
		//caso funcionário tenha férias
		if (ferias != null) {
			return (Util.buscarDiferencaEntreDatasEmMeses(ferias.getDataFim(),LocalDate.now()) + meses) >= limiteFerias;
		}
		//verifica pelo dia de contratação
		return (Util.buscarDiferencaEntreDatasEmMeses(funcionario.getDataContratacao(),LocalDate.now()) + meses) >= limiteFerias;
		
	}

	private Ferias buscarUltimaFerias(Funcionario funcionario) {
		
		if (!Util.isListaVazia(funcionario.getFerias())) {
			int tamanho = funcionario.getFerias().size();
			return funcionario.getFerias().get(tamanho - 1);
		}
		
		return null;
	}
	
	private void gerarMatriculafuncionario(Funcionario funcionario) {

		funcionario.setMatricula(String.valueOf((this.funcionarioDao.count() + 1)));
		
	}
	
	private void salvarFotoAws(Funcionario funcionario) {
		
		final String fileName = "foto"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
		
		if (!Util.isStringVazia(funcionario.getFoto())) {
			
			Base64Image base64Image = new Base64Image(funcionario.getFoto(), fileName);
			if (base64Image.isHasErrors()) {
				throw new ProcessException(base64Image.getErrorMessage());
			}
			final String caminho = this.fileSaver.write(base64Image);
			atualizarCaminhoFoto(funcionario,caminho);
			
		}
		
	}

	private void atualizarCaminhoFoto(Funcionario funcionario, String caminho) {
		
		funcionario.setFoto(caminho);
		
	}


}
