package br.com.desafiocastgroup.castgroup.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.desafiocastgroup.castgroup.model.Funcionario;
import br.com.desafiocastgroup.castgroup.util.Util;

public class FuncionarioValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		
		return Funcionario.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Funcionario funcionario = (Funcionario) target;
		
		if (Util.calcularIdade(funcionario.getDataNascimento()) < 18) {
			errors.rejectValue("dataNascimento", "dataNascimento", "{funcionario.datanascimento.older}");
		}
		else if (funcionario.getDataContratacao().isBefore(funcionario.getDataNascimento())) {
			errors.rejectValue("dataContratacao", "dataContratacao", "{funcionario.datacontratacao.lessthan}");
		}
		
	}

}
