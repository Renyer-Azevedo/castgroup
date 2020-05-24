package br.com.desafiocastgroup.castgroup.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.util.StdConverter;

import br.com.desafiocastgroup.castgroup.model.Equipe;
import br.com.desafiocastgroup.castgroup.service.EquipeService;

@Component
public class StringToEquipeConverter {
	
    private StringToEquipeConverter() {
		super();
	}

	public static class StringToEquipeDeserializationConverter extends StdConverter<String, Equipe> {
    	
    	@Autowired
    	private EquipeService equipeService;

        @Override
        public Equipe convert(String id) {
    		if (id.isEmpty()) {
    			return null;
    		}

    		return this.equipeService.buscarPorId(Long.valueOf(id));
        }

    }

}
