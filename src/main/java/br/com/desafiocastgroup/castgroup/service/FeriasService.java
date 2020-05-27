package br.com.desafiocastgroup.castgroup.service;

import java.awt.image.BufferedImage;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import br.com.desafiocastgroup.castgroup.model.Ferias;

@Transactional(readOnly = true)
public interface FeriasService {

	@Transactional(readOnly = false)
	BufferedImage salvar(Ferias ferias);
	List<Ferias> listarTodos();
	@Transactional(readOnly = false)
	void remover(Long id);
	List<Ferias> buscarFeriasPorMatriculaFuncionario(String matricula);
	
}
