package br.com.desafiocastgroup.castgroup.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.desafiocastgroup.castgroup.converter.DateConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Ferias {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @ManyToOne
	Funcionario funcionario;
	
    @Column(nullable = false, columnDefinition = "DATE")
    @NotNull(message = "{ferias.datainicio.notempty}")
	@PastOrPresent(message = "{ferias.datainicio.pastorpresent}")
	@DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = DateConverter.class)
	private LocalDate dataInicio;
	
    @Column(nullable = false, columnDefinition = "DATE")
    @NotNull(message = "{ferias.datafim.notempty}")
	@PastOrPresent(message = "{ferias.datafim.pastorpresent}")
	@DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = DateConverter.class)
	private LocalDate dataFim;

}
