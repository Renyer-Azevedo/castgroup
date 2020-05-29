package br.com.desafiocastgroup.castgroup.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafiocastgroup.castgroup.model.Usuario;
import br.com.desafiocastgroup.castgroup.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	private UsuarioService usuarioService;
	
	@Autowired
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> salvar(@Valid @RequestBody Usuario usuario) {
		return new ResponseEntity<>(this.usuarioService.salvar(usuario), HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/login")
	public ResponseEntity<String> login(@RequestParam(name = "email") String email, @RequestParam(name = "password") String senha) {
		return new ResponseEntity<>(this.usuarioService.login(email,senha), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Usuario>> buscarTodos() {
		return new ResponseEntity<>(this.usuarioService.listarTodos(), HttpStatus.OK);
	}
	
}
