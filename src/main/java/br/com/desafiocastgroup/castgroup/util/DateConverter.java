package br.com.desafiocastgroup.castgroup.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class DateConverter  extends StdDeserializer<LocalDate>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2580020958372713283L;

	public DateConverter() {
        super(LocalDate.class);
    }

	@Override
	public LocalDate deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException {
		
        String value = p.readValueAs(String.class);
        try {
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(value, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
        
	}

}
