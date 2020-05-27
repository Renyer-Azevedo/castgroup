package br.com.desafiocastgroup.castgroup.controller;

import java.awt.image.BufferedImage;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafiocastgroup.castgroup.model.Ferias;
import br.com.desafiocastgroup.castgroup.service.FeriasService;

@RestController
@RequestMapping("/ferias")
public class FeriasController {
	
	private FeriasService feriasService;
	
	@Autowired
	public FeriasController(FeriasService feriasService) {
		this.feriasService = feriasService;
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<BufferedImage> salvar(@Valid @RequestBody Ferias ferias){
		return new ResponseEntity<>(this.feriasService.salvar(ferias), HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Ferias>> buscarTodos() {
		return new ResponseEntity<>(this.feriasService.listarTodos(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/funcionario/matricula")
	public ResponseEntity<List<Ferias>> buscarFeriasPorMatriculaFuncionario(@RequestParam(name = "matricula") String matricula) {
		return new ResponseEntity<>(this.feriasService.buscarFeriasPorMatriculaFuncionario(matricula), HttpStatus.OK);
	}
	
    @RequestMapping( method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    public ResponseEntity<Object> remover(@PathVariable(value = "id") long id)
    {
    	this.feriasService.remover(id);
    	return new ResponseEntity<>(HttpStatus.OK);
    }

}
