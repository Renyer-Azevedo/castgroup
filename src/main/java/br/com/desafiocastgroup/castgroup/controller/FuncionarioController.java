package br.com.desafiocastgroup.castgroup.controller;

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

import br.com.desafiocastgroup.castgroup.model.Funcionario;
import br.com.desafiocastgroup.castgroup.service.FuncionarioService;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
	
	private FuncionarioService funcionarioService;
	
	@Autowired
	public FuncionarioController(FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Funcionario> salvar(@Valid @RequestBody Funcionario funcionario){
		return new ResponseEntity<>(this.funcionarioService.salvar(funcionario), HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Funcionario>> buscarTodos() {
		return new ResponseEntity<>(this.funcionarioService.listarTodos(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/ferias/expirada")
	public ResponseEntity<List<Funcionario>> buscarFuncionariosDevemSolicitarFerias(@RequestParam(name = "meses") int meses) {
		return new ResponseEntity<>(this.funcionarioService.buscarFuncionariosDevemSolicitarFerias(meses), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
	public ResponseEntity<Funcionario> buscarFuncionario(@PathVariable(value = "id") long id) {
		return new ResponseEntity<>(this.funcionarioService.buscarPorId(id), HttpStatus.OK);
	}

}
