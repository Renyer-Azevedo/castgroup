package br.com.desafiocastgroup.castgroup.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.desafiocastgroup.castgroup.model.Equipe;
import br.com.desafiocastgroup.castgroup.repository.EquipeRepository;

@Repository
@Transactional(readOnly = false)
public class EquipeDao {
	
	private EquipeRepository equipeRepository;
	
	@Autowired
	public EquipeDao(EquipeRepository equipeRepository) {
		this.equipeRepository = equipeRepository;
	}
	
	public Equipe salvar(Equipe equipe) {
		return this.equipeRepository.save(equipe);
	}
	
	public List<Equipe> listarTodos(){
		return (List<Equipe>) this.equipeRepository.findAll();
	} 
 
}
