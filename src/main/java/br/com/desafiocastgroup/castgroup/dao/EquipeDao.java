package br.com.desafiocastgroup.castgroup.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.desafiocastgroup.castgroup.model.Equipe;
import br.com.desafiocastgroup.castgroup.repository.EquipeRepository;

@Repository
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
	
	public Equipe buscarPorId(Long id) {
		Optional<Equipe> value = this.equipeRepository.findById(id);
		if (value.isPresent()) {
			return value.get();
		}
		return null;
	}
	
	public boolean existirEquipePorNome(String nome) {
		return this.equipeRepository.existsByNome(nome);
	}
 
}
