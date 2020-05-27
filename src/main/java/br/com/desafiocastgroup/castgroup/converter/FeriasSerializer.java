package br.com.desafiocastgroup.castgroup.converter;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import br.com.desafiocastgroup.castgroup.model.Ferias;

public class FeriasSerializer extends StdSerializer<Ferias>{

	private static final long serialVersionUID = -8519784434595620636L;
	
    public FeriasSerializer() {
        this(null);
    }
   
    public FeriasSerializer(Class<Ferias> t) {
        super(t);
    }

	@Override
	public void serialize(Ferias value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		
		gen.writeStartObject();
		gen.writeStringField("funcionario", value.getFuncionario().getNome());
		gen.writeStringField("matricula", value.getFuncionario().getMatricula());
		gen.writeStringField("dataInicio", value.getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		gen.writeStringField("dataFim", value.getDataFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		gen.writeEndObject();
		
	}

}
