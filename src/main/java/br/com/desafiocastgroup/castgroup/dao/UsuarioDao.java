package br.com.desafiocastgroup.castgroup.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.desafiocastgroup.castgroup.model.Usuario;
import br.com.desafiocastgroup.castgroup.repository.UsuarioRepository;

@Repository
@Transactional(readOnly = false)
public class UsuarioDao {
	
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	public UsuarioDao(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	public Usuario salvar(Usuario usuario) {
		return this.usuarioRepository.save(usuario);
	}
	
	public List<Usuario> listarTodos(){
		return (List<Usuario>) this.usuarioRepository.findAll();
	}

}
