package br.com.desafiocastgroup.castgroup.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.desafiocastgroup.castgroup.converter.StringToDateConverter;
import br.com.desafiocastgroup.castgroup.converter.StringToFuncionarioConverter;
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
public class Ferias {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @NotNull(message = "{ferias.tem.funcionario}")
    @JsonDeserialize(converter = StringToFuncionarioConverter.class)
    @ManyToOne
	Funcionario funcionario;
	
    @Column(nullable = false, columnDefinition = "DATE")
    @NotNull(message = "{ferias.datainicio.notempty}")
    @FutureOrPresent(message = "{ferias.datainicio.pastorpresent}")
	@DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd")
    @JsonDeserialize(converter = StringToDateConverter.class)
	private LocalDate dataInicio;
	
    @Column(nullable = false, columnDefinition = "DATE")
    @NotNull(message = "{ferias.datafim.notempty}")
    @FutureOrPresent(message = "{ferias.datafim.pastorpresent}")
	@DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd")
    @JsonDeserialize(converter = StringToDateConverter.class)
	private LocalDate dataFim;

}
