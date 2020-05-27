package br.com.desafiocastgroup.castgroup.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.desafiocastgroup.castgroup.dao.UsuarioDao;
import br.com.desafiocastgroup.castgroup.exception.ProcessException;
import br.com.desafiocastgroup.castgroup.model.Usuario;
import br.com.desafiocastgroup.castgroup.util.Util;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	private UsuarioDao usuarioDao;
	private MessageSource messageSource;
	
	@Autowired
	public UsuarioServiceImpl(UsuarioDao usuarioDao, MessageSource messageSource) {
		this.usuarioDao = usuarioDao;
		this.messageSource = messageSource;
	}

	@Override
	public Usuario salvar(Usuario usuario) {
		return this.usuarioDao.salvar(usuario);
	}

	@Override
	public List<Usuario> listarTodos() {
		List<Usuario> usuarios = this.usuarioDao.listarTodos();
		if (Util.isListaVazia(usuarios)) {
			throw new ProcessException(HttpStatus.NOT_FOUND, messageSource.getMessage("usuario.notfound", null, Locale.getDefault()));
		}
		return usuarios;
	}

	@Override
	public Usuario buscarPorEmail(String email) {
		Usuario usuario = this.usuarioDao.buscarPorEmail(email);
		if (usuario == null) {
			throw new ProcessException(HttpStatus.NOT_FOUND, messageSource.getMessage("usuario.notfound", null, Locale.getDefault()));
		}
		return usuario;
	}

}
