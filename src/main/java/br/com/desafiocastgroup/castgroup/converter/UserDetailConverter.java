package br.com.desafiocastgroup.castgroup.converter;

import java.util.ArrayList;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.desafiocastgroup.castgroup.dao.UsuarioDao;
import br.com.desafiocastgroup.castgroup.model.Usuario;

@Component
public class UserDetailConverter implements UserDetailsService{

	private UsuarioDao usuarioDao;
	private MessageSource messageSource;
	
	@Autowired
	private UserDetailConverter(UsuarioDao usuarioDao, MessageSource messageSource) {
		this.usuarioDao = usuarioDao;
		this.messageSource = messageSource;
	}

	@Override
	public UserDetails loadUserByUsername(String email) {
		
		Usuario usuario = this.usuarioDao.buscarPorEmail(email);
		
		if (usuario == null) {
			throw new UsernameNotFoundException(messageSource.getMessage("usuario.email.notfound", null, Locale.getDefault()));
		}
		
		return new User(usuario.getEmail(), usuario.getPassword(), new ArrayList<>());
		
	}

}
