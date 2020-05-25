package br.com.desafiocastgroup.castgroup.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.desafiocastgroup.castgroup.exception.ProcessException;

@Component
public class Disco {
	
	@Value("${upload.disco.raiz}")
	private String diretorioRaiz;
	
	@Value("${upload.disco.diretorio.fotos}")
	private String diretorioFotos;
	
	@Value("${upload.disco.diretorio.qrcode}")
	private String diretorioQrCode;
	
	public String salvarFoto(MultipartFile arquivo) {
		return this.salvar(this.diretorioFotos, arquivo);
	}
	
	public String salvar(String diretorio, MultipartFile arquivo) {
		
		try {
			Path diretorioPath = Paths.get(this.diretorioRaiz, diretorio);
			Path arquivoPath = diretorioPath.resolve(arquivo.getOriginalFilename());
			Files.createDirectories(diretorioPath);
			arquivo.transferTo(arquivoPath.toFile());
			return diretorioPath.toAbsolutePath().toString();
		} catch (IOException e) {
			throw new ProcessException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e.getMessage());
		}	
	
		
	}
	
	public String salvarQrCode(BufferedImage generateQRCodeImage) {
			
		try {
			final String arquivo = "qrcode.png";
			final String path = this.diretorioRaiz + this.diretorioQrCode + arquivo;
			File file  = new File(path);
			ImageIO.write(generateQRCodeImage, "png", file);
			return path;
		} catch (IOException e) {
			throw new ProcessException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e.getMessage());
		}
		
	}

}
