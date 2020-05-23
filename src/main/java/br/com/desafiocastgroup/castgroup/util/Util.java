package br.com.desafiocastgroup.castgroup.util;

import java.time.LocalDate;
import java.time.Period;

public class Util {

	public static int calcularIdade(LocalDate date) {
		
		if (date != null) {
			return Period.between(date, LocalDate.now()).getYears();
		}
		return 0;
	}
	
}
