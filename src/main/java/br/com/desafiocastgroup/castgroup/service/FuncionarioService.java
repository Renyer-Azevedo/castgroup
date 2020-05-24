package br.com.desafiocastgroup.castgroup.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.com.desafiocastgroup.castgroup.model.Funcionario;

public interface FuncionarioService {
	
	Funcionario salvar(Funcionario funcionario, MultipartFile arquivo);
	List<Funcionario> listarTodos();
	Funcionario buscarPorId(Long id);
	List<Funcionario> buscarFuncionariosDevemSolicitarFerias(int meses);

}
