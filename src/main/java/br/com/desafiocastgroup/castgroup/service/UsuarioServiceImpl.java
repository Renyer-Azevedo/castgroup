package br.com.desafiocastgroup.castgroup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafiocastgroup.castgroup.dao.UsuarioDao;
import br.com.desafiocastgroup.castgroup.model.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	private UsuarioDao usuarioDao;
	
	@Autowired
	public UsuarioServiceImpl(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	@Override
	public Usuario salvar(Usuario usuario) {
		return this.usuarioDao.salvar(usuario);
	}

	@Override
	public List<Usuario> listarTodos() {
		return this.usuarioDao.listarTodos();
	}

	@Override
	public Usuario buscarPorEmail(String email) {
		return this.usuarioDao.buscarPorEmail(email);
	}

}
