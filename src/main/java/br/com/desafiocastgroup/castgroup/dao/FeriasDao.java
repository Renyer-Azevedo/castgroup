package br.com.desafiocastgroup.castgroup.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.desafiocastgroup.castgroup.model.Ferias;
import br.com.desafiocastgroup.castgroup.repository.FeriasRepository;

@Repository
@Transactional(readOnly = true)
public class FeriasDao {

	private FeriasRepository feriasRepository;
	
	@Autowired
	public FeriasDao(FeriasRepository feriasRepository) {
		this.feriasRepository = feriasRepository;
	}
	
	@Transactional(readOnly = false)
	public Ferias salvar(Ferias ferias) {
		return this.feriasRepository.save(ferias);
	}
	
	public List<Ferias> listarTodos(){
		return (List<Ferias>) this.feriasRepository.findAll();
	}
	
	@Transactional(readOnly = false)
	public void remover(Ferias ferias) {
		this.feriasRepository.delete(ferias);
	}
	
	public List<Ferias> listarPorIds(List<Long> ids) {
		return (List<Ferias>) this.feriasRepository.findAllById(ids);
	}
	
}
