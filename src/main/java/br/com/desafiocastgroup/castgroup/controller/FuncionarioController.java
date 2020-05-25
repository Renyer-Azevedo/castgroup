package br.com.desafiocastgroup.castgroup.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Funcionario> salvar(@Valid Funcionario funcionario, MultipartFile foto){
		System.out.println(funcionario);
		return new ResponseEntity<>(new Funcionario(), HttpStatus.CREATED);
		//return new ResponseEntity<>(this.funcionarioService.salvar(equipe), HttpStatus.CREATED);
	}

}
