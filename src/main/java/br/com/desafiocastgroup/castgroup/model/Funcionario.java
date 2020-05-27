package br.com.desafiocastgroup.castgroup.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.desafiocastgroup.castgroup.converter.StringToDateConverter;
import br.com.desafiocastgroup.castgroup.converter.StringToEquipeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Funcionario {
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
	private String matricula;
    
    @Column(nullable = false)
    @NotBlank(message = "{funcionario.nome.notempty}")
	private String nome;
    
    @Column(nullable = false, columnDefinition = "DATE")
    @NotNull(message = "{funcionario.datanascimento.notempty}")
	@PastOrPresent(message = "{funcionario.datanascimento.pastorpresent}")
	@DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd")
    @JsonDeserialize(converter = StringToDateConverter.class)
	private LocalDate dataNascimento;
    
	@OneToOne(cascade = CascadeType.ALL)
	private Endereco endereco;
	
    @Column(nullable = false, columnDefinition = "DATE")
    @NotNull(message = "{funcionario.datacontratacao.notempty}")
	@PastOrPresent(message = "{funcionario.datacontratacao.pastorpresent}")
	@DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd")
    @JsonDeserialize(converter = StringToDateConverter.class)
	private LocalDate dataContratacao;
    

	private String foto;
	
    @NotNull(message = "{funcionario.pertence.equipe}")
    @JsonDeserialize(converter = StringToEquipeConverter.class)
	@ManyToOne
	private Equipe equipe;
	
	@OneToMany(mappedBy = "funcionario")
	private List<Ferias> ferias;

}
