package br.com.desafiocastgroup.castgroup.converter;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import br.com.desafiocastgroup.castgroup.model.Equipe;
import br.com.desafiocastgroup.castgroup.model.Funcionario;
import br.com.desafiocastgroup.castgroup.util.Util;

public class EquipeSerializer extends StdSerializer<Equipe>{

	private static final long serialVersionUID = -8519784434595620636L;
	
    public EquipeSerializer() {
        this(null);
    }
   
    public EquipeSerializer(Class<Equipe> t) {
        super(t);
    }

	@Override
	public void serialize(Equipe value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		
		gen.writeStartObject();
		gen.writeStringField("equipe", value.getNome());
		if(Util.isListaVazia(value.getFuncionarios())) {
			gen.writeStringField("funcionario", "Sem funcionários");
		}
		else {
			for (Funcionario funcionario : value.getFuncionarios()) {
				gen.writeStringField("funcionario", funcionario.getNome().concat(" - ".concat(funcionario.getMatricula())));
			}
		}
		gen.writeEndObject();
		
	}

}
