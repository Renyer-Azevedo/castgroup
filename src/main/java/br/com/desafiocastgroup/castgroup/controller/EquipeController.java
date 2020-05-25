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
import org.springframework.web.bind.annotation.RestController;

import br.com.desafiocastgroup.castgroup.model.Equipe;
import br.com.desafiocastgroup.castgroup.service.EquipeService;

@RestController
@RequestMapping("/equipes")
public class EquipeController {
	
	private EquipeService equipeService;
	
	@Autowired
	public EquipeController(EquipeService equipeService) {
		this.equipeService = equipeService;
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Equipe> salvar(@Valid @RequestBody Equipe equipe) {
		return new ResponseEntity<>(this.equipeService.salvar(equipe), HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Equipe>> buscarTodos() {
		return new ResponseEntity<>(this.equipeService.listarTodos(), HttpStatus.OK);
	}

}
