package br.com.desafiocastgroup.castgroup.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import br.com.desafiocastgroup.castgroup.model.Usuario;

@Transactional(readOnly = true)
public interface UsuarioService {

	@Transactional(readOnly = false)
	String salvar(Usuario usuario);
	List<Usuario> listarTodos();
	Usuario buscarPorEmail(String email);
	String login(String email, String senha);
}
