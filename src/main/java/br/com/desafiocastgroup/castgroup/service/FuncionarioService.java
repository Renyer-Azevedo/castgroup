package br.com.desafiocastgroup.castgroup.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import br.com.desafiocastgroup.castgroup.model.Funcionario;

@Transactional(readOnly = true)
public interface FuncionarioService {
	
	@Transactional(readOnly = false)
	Funcionario salvar(Funcionario funcionario);
	List<Funcionario> listarTodos();
	Funcionario buscarPorId(Long id);
	List<Funcionario> buscarFuncionariosDevemSolicitarFerias(int meses);
	Funcionario buscarPorMatricula(String matricula);

}
