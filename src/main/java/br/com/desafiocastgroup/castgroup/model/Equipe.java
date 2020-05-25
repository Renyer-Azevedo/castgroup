package br.com.desafiocastgroup.castgroup.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import br.com.desafiocastgroup.castgroup.validator.Unique;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Equipe {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    @NotBlank(message = "{equipe.nome.notempty}")
    @Unique(message = "{equipe.nome.unique}")
	private String nome;
    
	@OneToMany(mappedBy = "equipe")
	private List<Funcionario> funcionarios;

}
			