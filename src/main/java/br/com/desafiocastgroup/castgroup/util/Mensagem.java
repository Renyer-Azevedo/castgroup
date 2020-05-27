package br.com.desafiocastgroup.castgroup.util;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Mensagem {
	
	private String remetente;
	private List<String> destinatarios;
	private String assunto;
	private String corpo;
	private Map<String,Arquivo> imagensCorpo;
	private List<String> cco;
	private List<Arquivo> anexos;

}
