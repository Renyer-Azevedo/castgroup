package br.com.desafiocastgroup.castgroup.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.desafiocastgroup.castgroup.exception.ProcessException;

@Component
public class Disco {
	
	@Value("${upload.disco.raiz}")
	private String raiz;
	
	@Value("${upload.disco.diretorio.fotos}")
	private String diretorioFotos;
	
	public String salvarFoto(MultipartFile arquivo) {
		return this.salvar(this.diretorioFotos, arquivo);
	}
	
	public String salvar(String diretorio, MultipartFile arquivo) {
		Path diretorioPath = Paths.get(this.raiz, diretorio);
		Path arquivoPath = diretorioPath.resolve(arquivo.getOriginalFilename());
		
		try {
			Files.createDirectories(diretorioPath);
			arquivo.transferTo(arquivoPath.toFile());			
		} catch (IOException e) {
			throw new ProcessException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e.getMessage());
		}	
	
		return diretorioPath.toAbsolutePath().toString();
		
	}

}
