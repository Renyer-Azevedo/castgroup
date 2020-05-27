package br.com.desafiocastgroup.castgroup.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.util.StdConverter;

import br.com.desafiocastgroup.castgroup.model.Equipe;
import br.com.desafiocastgroup.castgroup.service.EquipeService;
import br.com.desafiocastgroup.castgroup.util.Util;

@Component
public class StringToEquipeConverter extends StdConverter<String, Equipe> {
	
	private EquipeService equipeService;
	
	@Autowired
    public StringToEquipeConverter(EquipeService equipeService) {
    	this.equipeService = equipeService;
	}

	@Override
    public Equipe convert(String id) {
    	
		if (Util.isStringVazia(id)) {
			return null;
		}

		return this.equipeService.buscarPorId(Long.valueOf(id));
    }

}
