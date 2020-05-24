package br.com.desafiocastgroup.castgroup.service;

import java.util.List;

import br.com.desafiocastgroup.castgroup.model.Usuario;

public interface UsuarioService {

	Usuario salvar(Usuario usuario);
	List<Usuario> listarTodos();
	Usuario buscarPorEmail(String email);
}
