package br.com.desafiocastgroup.castgroup.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.util.StdConverter;

import br.com.desafiocastgroup.castgroup.model.Funcionario;
import br.com.desafiocastgroup.castgroup.service.FuncionarioService;

@Component
public class StringToFuncionarioConverter {
	
    private StringToFuncionarioConverter() {
		super();
	}

	public static class StringToFuncionarioDeserializationConverter extends StdConverter<String, Funcionario> {
    	
    	@Autowired
    	private FuncionarioService funcionarioService;

        @Override
        public Funcionario convert(String id) {
    		if (id.isEmpty()) {
    			return null;
    		}

    		return this.funcionarioService.buscarPorId(Long.valueOf(id));
        }

    }

}
