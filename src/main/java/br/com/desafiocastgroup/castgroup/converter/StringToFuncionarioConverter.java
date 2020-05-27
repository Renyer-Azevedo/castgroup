package br.com.desafiocastgroup.castgroup.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.util.StdConverter;

import br.com.desafiocastgroup.castgroup.model.Funcionario;
import br.com.desafiocastgroup.castgroup.service.FuncionarioService;
import br.com.desafiocastgroup.castgroup.util.Util;

@Component
public class StringToFuncionarioConverter extends StdConverter<String, Funcionario> {
	
	private FuncionarioService funcionarioService;
	
	@Autowired
    public StringToFuncionarioConverter(FuncionarioService funcionarioService) {
    	this.funcionarioService = funcionarioService;
	}

	@Override
    public Funcionario convert(String id) {
    	
		if (Util.isStringVazia(id)) {
			return null;
		}

		return this.funcionarioService.buscarPorId(Long.valueOf(id));
    }

}
