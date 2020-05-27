package br.com.desafiocastgroup.castgroup.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.util.StdConverter;

import br.com.desafiocastgroup.castgroup.util.Util;

@Component
public class StringToDateConverter extends StdConverter<String, LocalDate>{

	public StringToDateConverter() {
		super();
	}

	@Override
	public LocalDate convert(String date) {
		
		if(!Util.isDate(date)) {
			return null;
		}

		return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
	}


}
