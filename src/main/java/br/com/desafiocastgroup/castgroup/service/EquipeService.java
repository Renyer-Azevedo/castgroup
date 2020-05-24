package br.com.desafiocastgroup.castgroup.service;

import java.util.List;

import br.com.desafiocastgroup.castgroup.model.Equipe;

public interface EquipeService {
	
	Equipe salvar(Equipe equipe);
	List<Equipe> listarTodos();
	Equipe buscarPorId(Long id);

}
