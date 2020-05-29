package br.com.desafiocastgroup.castgroup.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.desafiocastgroup.castgroup.dao.UsuarioDao;
import br.com.desafiocastgroup.castgroup.exception.ProcessException;
import br.com.desafiocastgroup.castgroup.model.Usuario;
import br.com.desafiocastgroup.castgroup.util.Util;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	private UsuarioDao usuarioDao;
	private MessageSource messageSource;
	private AuthenticationManager authenticationManager;
	private JwtTokenService jwtTokenService;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UsuarioServiceImpl(UsuarioDao usuarioDao, MessageSource messageSource, AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, PasswordEncoder passwordEncoder) {
		this.usuarioDao = usuarioDao;
		this.messageSource = messageSource;
		this.authenticationManager = authenticationManager;
		this.jwtTokenService = jwtTokenService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public String salvar(Usuario usuario) {
		usuario.setPassword(this.passwordEncoder.encode(usuario.getPassword()));
		this.usuarioDao.salvar(usuario);
		return  this.jwtTokenService.generateToken(usuario.getEmail());
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

	@Override
	public String login(String email, String senha) {

	    try {
	    	
	        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, senha));
	        return this.jwtTokenService.generateToken(email);
	        
	      } catch (AuthenticationException e) {
	    	  throw new ProcessException(HttpStatus.UNAUTHORIZED, messageSource.getMessage("usuario.notauthorized", null, Locale.getDefault()));
	      }
		
	}

}
