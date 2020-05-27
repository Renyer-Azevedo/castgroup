package br.com.desafiocastgroup.castgroup.converter;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import br.com.desafiocastgroup.castgroup.model.Funcionario;
import br.com.desafiocastgroup.castgroup.util.Util;

public class FuncionarioSerializer extends StdSerializer<Funcionario>{

	private static final long serialVersionUID = -8519784434595620636L;
	
    public FuncionarioSerializer() {
        this(null);
    }
   
    public FuncionarioSerializer(Class<Funcionario> t) {
        super(t);
    }

	@Override
	public void serialize(Funcionario value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		
		final String virgula = ",";
		final String espaco = " ";
		
		gen.writeStartObject();
		gen.writeStringField("funcionario", value.getNome());
		gen.writeStringField("matricula", value.getMatricula());
		gen.writeStringField("endereco", value.getEndereco().getRua().concat(virgula.concat(espaco).concat(String.valueOf(value.getEndereco().getNumero()).concat(virgula).concat(espaco)
										.concat(value.getEndereco().getComplemento().concat(virgula).concat(espaco).concat(value.getEndereco().getBairro().concat(virgula).concat(espaco)))
										.concat(value.getEndereco().getCidade().concat(virgula).concat(espaco).concat(value.getEndereco().getEstado())))));
		gen.writeStringField("dataNascimento", value.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		gen.writeStringField("dataContratacao", value.getDataContratacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		gen.writeStringField("equipe", value.getEquipe().getNome());
		if (Util.isListaVazia(value.getFerias())) {
			gen.writeStringField("ferias", "Não");
		}
		else {
			gen.writeStringField("ferias", value.getFerias().get(value.getFerias().size()-1).getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " até " + value.getFerias().get(value.getFerias().size()-1).getDataFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		}
		
		gen.writeEndObject();
		
	}

}
