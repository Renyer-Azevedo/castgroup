package br.com.desafiocastgroup.castgroup.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.desafiocastgroup.castgroup.model.Ferias;

public class FeriasValidator  implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Ferias.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Ferias ferias = (Ferias) target;
		
		if (ferias.getDataInicio().equals(ferias.getDataFim()) || ferias.getDataInicio().isAfter(ferias.getDataFim())) {
			errors.rejectValue("dataInicio", "dataInicio", "{funcionario.periodo.invalid}");
		}
		
	}

}
