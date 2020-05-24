package br.com.desafiocastgroup.castgroup.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.desafiocastgroup.castgroup.model.Funcionario;

public interface FuncionarioRepository  extends CrudRepository<Funcionario, Long>{
	
	Funcionario findByMatricula(String matricula);
    
    @Query(value = "SELECT SCHEMA.SEQUENCE_NAME.nextval FROM dual", nativeQuery = true)
    BigDecimal getNextValMySequence();

}
