package br.com.desafiocastgroup.castgroup.service;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.desafiocastgroup.castgroup.dao.FeriasDao;
import br.com.desafiocastgroup.castgroup.dao.FuncionarioDao;
import br.com.desafiocastgroup.castgroup.exception.ProcessException;
import br.com.desafiocastgroup.castgroup.model.Ferias;
import br.com.desafiocastgroup.castgroup.model.Funcionario;
import br.com.desafiocastgroup.castgroup.util.Util;

@Service
public class FeriasServiceImpl implements FeriasService{
	
	private FeriasDao feriasDao;
	private FuncionarioDao funcionarioDao;
	
	@Autowired
	private FeriasServiceImpl(FeriasDao feriasDao, FuncionarioDao funcionarioDao) {
		this.feriasDao = feriasDao;
		this.funcionarioDao = funcionarioDao;
	}

	@Override
	public BufferedImage salvar(Ferias ferias) {
		validarFerias(ferias);
		this.feriasDao.salvar(ferias);
		return gerarQrCode(ferias.getFuncionario());
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
		
		if (funcionario == null || Util.buscarDiferencaEntreDatasEmMeses(LocalDate.now(), funcionario.getDataContratacao()) < limiteMinimoFerias) {
			throw new ProcessException(HttpStatus.UNPROCESSABLE_ENTITY, "{ferias.limite.minimo}");
		}
		
		if (buscarQuantidadeFuncionariosEquipe(funcionario) <= 4 && !validarDiaFeriasCoincidenteFuncionarios(ferias,buscarFuncionariosEquipe(funcionario))) {
			throw new ProcessException(HttpStatus.UNPROCESSABLE_ENTITY, "{ferias.dias.coincidente}");
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

	private void removeFotoFuncionario(Funcionario funcionario) {
		 
		funcionario.setCaminhoFoto("");
	}

}
