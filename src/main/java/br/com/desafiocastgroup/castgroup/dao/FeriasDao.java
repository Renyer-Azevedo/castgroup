package br.com.desafiocastgroup.castgroup.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.desafiocastgroup.castgroup.model.Ferias;
import br.com.desafiocastgroup.castgroup.repository.FeriasRepository;

@Repository
public class FeriasDao {

	private FeriasRepository feriasRepository;
	
	@Autowired
	public FeriasDao(FeriasRepository feriasRepository) {
		this.feriasRepository = feriasRepository;
	}
	
	public Ferias salvar(Ferias ferias) {
		return this.feriasRepository.save(ferias);
	}
	
	public List<Ferias> listarTodos(){
		return (List<Ferias>) this.feriasRepository.findAll();
	}

	public void remover(Long id) {
		this.feriasRepository.deleteById(id);
	}
	
	public List<Ferias> listarPorIds(List<Long> ids) {
		return (List<Ferias>) this.feriasRepository.findAllById(ids);
	}
	
}
