package br.com.desafiocastgroup.castgroup.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.desafiocastgroup.castgroup.service.EquipeService;

@Component
public class EquipeUniqueValidator implements ConstraintValidator<Unique,String>{

	private EquipeService equipeService;
	
	@Autowired
	public EquipeUniqueValidator(EquipeService equipeService) {
		this.equipeService = equipeService;
	}
	
	public EquipeUniqueValidator() {
		super();
	}

	@Override
    public void initialize(Unique unique) {
        unique.message();
    }

	@Override
	public boolean isValid(String nome, ConstraintValidatorContext context) {
		return !(this.equipeService != null && this.equipeService.existirEquipePorNome(nome));
	}

}
