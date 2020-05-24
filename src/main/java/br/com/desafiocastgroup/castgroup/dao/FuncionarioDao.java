package br.com.desafiocastgroup.castgroup.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.desafiocastgroup.castgroup.model.Funcionario;
import br.com.desafiocastgroup.castgroup.repository.FuncionarioRepository;

@Repository
@Transactional(readOnly = true)
public class FuncionarioDao {

	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	public FuncionarioDao(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}
	
	@Transactional(readOnly = false)
	public Funcionario salvar(Funcionario funcionario) {
		return this.funcionarioRepository.save(funcionario);
	}
	
	public List<Funcionario> listarTodos(){
		return (List<Funcionario>) this.funcionarioRepository.findAll();
	}
	
	public Funcionario buscarPorId(Long id) {
		Optional<Funcionario> value = this.funcionarioRepository.findById(id);
		if (value.isPresent()) {
			return value.get();
		}
		return null;
	}
	
	public List<Funcionario> listarQueDevemSolicitarFerias(List<Long> ids) {
		return (List<Funcionario>) this.funcionarioRepository.findAllById(ids);
	}
	
	public Long count() {
		return this.funcionarioRepository.count();
	}
	
	public Funcionario buscarPorMatricula(String matricula) {
		return this.funcionarioRepository.findByMatricula(matricula);
	}
	
	public BigDecimal getNextValMySequence() {
		return this.funcionarioRepository.getNextValMySequence();
	}
	
	public Boolean isExistFuncionario(Funcionario funcionario) {
		return this.funcionarioRepository.existsById(funcionario.getId());
	}
	
}
