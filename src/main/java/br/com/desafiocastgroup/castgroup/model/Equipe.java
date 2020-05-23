package br.com.desafiocastgroup.castgroup.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotNull(message = "{equipe.id.notempty}")
    private Long id;
    
    @Column(nullable = false)
    @NotBlank(message = "{equipe.nome.notempty}")
	private String nome;
    
	@OneToMany(mappedBy = "equipe")
	private List<Funcionario> funcionarios;

}
