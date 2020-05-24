package br.com.desafiocastgroup.castgroup.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.desafiocastgroup.castgroup.dao.FuncionarioDao;
import br.com.desafiocastgroup.castgroup.model.Ferias;
import br.com.desafiocastgroup.castgroup.model.Funcionario;
import br.com.desafiocastgroup.castgroup.util.Disco;
import br.com.desafiocastgroup.castgroup.util.Util;

@Service
public class FuncionarioServiceImpl implements FuncionarioService{

	private FuncionarioDao funcionarioDao;
	private Disco disco;
	
	@Autowired
	public FuncionarioServiceImpl(FuncionarioDao funcionarioDao, Disco disco) {
		this.funcionarioDao = funcionarioDao;
		this.disco = disco;
	}
	
	@Override
	public Funcionario salvar(Funcionario funcionario, MultipartFile arquivo) {
		salvarArquivo(funcionario,arquivo);
		gerarMatriculafuncionario(funcionario);
		return this.funcionarioDao.salvar(funcionario);
	}

	@Override
	public List<Funcionario> listarTodos() {
		return this.funcionarioDao.listarTodos();
	}

	@Override
	public Funcionario buscarPorId(Long id) {
		return this.funcionarioDao.buscarPorId(id);
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
	
	private boolean verificarFuncionarioDeveSolicitarFerias(Funcionario funcionario, int meses) {
		
		//2 anos
		final int limiteFerias = 24;
		Ferias ferias = buscarUltimaFerias(funcionario);
		
		//caso funcionário tenha férias
		if (ferias != null) {
			return (Util.buscarDiferencaEntreDatasEmMeses(LocalDate.now(),ferias.getDataFim()) + meses) >= limiteFerias;
		}
		//verifica pelo dia de contratação
		return (Util.buscarDiferencaEntreDatasEmMeses(LocalDate.now(), funcionario.getDataContratacao()) + meses) >= limiteFerias;
		
	}

	private Ferias buscarUltimaFerias(Funcionario funcionario) {
		
		if (!Util.isListaVazia(funcionario.getFerias())) {
			int tamanho = funcionario.getFerias().size();
			return funcionario.getFerias().get(tamanho - 1);
		}
		
		return null;
	}

	private void salvarArquivo(Funcionario funcionario, MultipartFile arquivo) {
		
		if (!arquivo.isEmpty()) {
			String caminho = this.disco.salvarFoto(arquivo);
			salvarCaminhoFotoFuncionario(funcionario,caminho);
		}
		
	}

	private void salvarCaminhoFotoFuncionario(Funcionario funcionario, String caminho) {
		
		if (funcionario != null) {
			funcionario.setCaminhoFoto(caminho);
		}
	}
	
	private void gerarMatriculafuncionario(Funcionario funcionario) {
		
		if (!Util.islongNull(funcionario.getId()) && !this.funcionarioDao.isExistFuncionario(funcionario)) {
			funcionario.setMatricula(this.funcionarioDao.getNextValMySequence().toString());
		}
		
	}


}
