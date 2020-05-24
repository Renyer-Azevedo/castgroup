package br.com.desafiocastgroup.castgroup.service;

import java.awt.image.BufferedImage;
import java.util.List;

import br.com.desafiocastgroup.castgroup.model.Ferias;

public interface FeriasService {

	BufferedImage salvar(Ferias ferias);
	List<Ferias> listarTodos();
	void remover(Ferias ferias);
	List<Ferias> buscarFeriasPorMatriculaFuncionario(String matricula);
	
}
