package br.com.desafiocastgroup.castgroup.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.desafiocastgroup.castgroup.model.Funcionario;

public interface FuncionarioRepository  extends CrudRepository<Funcionario, Long>{
	
	Funcionario findByMatricula(String matricula);

}
