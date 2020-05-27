package br.com.desafiocastgroup.castgroup.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import br.com.desafiocastgroup.castgroup.model.Equipe;

@Transactional(readOnly = true)
public interface EquipeService {
	
	@Transactional(readOnly = false)
	Equipe salvar(Equipe equipe);
	List<Equipe> listarTodos();
	Equipe buscarPorId(Long id);
	boolean existirEquipePorNome(String nome);

}
