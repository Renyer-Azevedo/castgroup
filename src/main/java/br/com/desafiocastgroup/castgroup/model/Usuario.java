package br.com.desafiocastgroup.castgroup.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import br.com.desafiocastgroup.castgroup.validator.Unique;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Usuario {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    @NotBlank(message = "{usuario.email.notempty}")
    @Unique(message = "{usuario.email.unique}")
    @Email
	private String email;
    
    @Column(nullable = false)
    @NotBlank(message = "{usuario.password.notempty}")
	private String password;

}
