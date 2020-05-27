package br.com.desafiocastgroup.castgroup.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@Column(nullable = false)
	@NotBlank(message = "{endereco.rua.notempty}")
	private String rua;
	
	@Column(nullable = false)
	@NotNull(message = "{endereco.numero.notempty}")
	private int numero;
	
	private String complemento;
	
	@Column(nullable = false)
	@NotBlank(message = "{endereco.bairro.notempty}")
	private String bairro;
	
	@Column(nullable = false)
	@NotBlank(message = "{endereco.cidade.notempty}")
	private String cidade;
	
	@Column(nullable = false)
	@NotBlank(message = "{endereco.estado.notempty}")
	private String estado;
	
}
