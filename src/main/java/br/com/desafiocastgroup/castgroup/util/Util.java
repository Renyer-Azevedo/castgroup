package br.com.desafiocastgroup.castgroup.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import br.com.desafiocastgroup.castgroup.exception.ProcessException;

public class Util {
	
	private static final String DATE="\\d{4}-\\d{2}-\\d{2}";

	private Util() {
		super();
	}

	public static int calcularIdade(LocalDate date) {
		
		if (date != null) {
			return Period.between(date, LocalDate.now()).getYears();
		}
		return 0;
	}
	
	public static boolean isListaVazia(List<?> lista){		
		return lista == null || lista.isEmpty();
	}
	
	public static int buscarDiferencaEntreDatasEmMeses(LocalDate date, LocalDate anotherDate) {
	
		if (date != null && anotherDate != null) {
			return (int) Period.between(date, anotherDate).toTotalMonths();
		}
		
		return 0;
		
	}
	
	public static int compararData(LocalDate date, LocalDate anotherDate) {
		
		if (date.isBefore(anotherDate)) 
			return -1;
		else if (date.equals(anotherDate))
			return 0;
		else 
			return 1;
		
	}
	
	public static BufferedImage generateQRCodeImage(String barcodeText){
		
		try {
		    QRCodeWriter barcodeWriter = new QRCodeWriter();
		    BitMatrix bitMatrix = 
		      barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);
		    return MatrixToImageWriter.toBufferedImage(bitMatrix);
		} catch (WriterException e) {
			throw new ProcessException(e.getMessage());
		}
	}
	
	@SuppressWarnings("serial")
	public static String converterObjectToStringJson(Object object, List<String> listaExclusoes, SimpleModule moduleSerializer) {
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector(){
			    @Override
			    public boolean hasIgnoreMarker(final AnnotatedMember m) {

				    List<String> exclusions = listaExclusoes;
				    if (isListaVazia(exclusions)) {
				    	return false;
				    }
				    return exclusions.contains(m.getName()) || super.hasIgnoreMarker(m);
				    
			    }
			});
			if (moduleSerializer != null) {
				mapper.registerModule(moduleSerializer);
			}
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new ProcessException(e.getMessage());
		}
	}
	
    public static boolean isStringVazia(String string)
    {
        return string == null || string.trim().equals("");
    }
	
    public static int gerarNumeroAleatorio() {
    	Random random = new Random();
    	return random.nextInt(999999999);
    }
    
    public static boolean islongNull(Long numero)
    {
        return !(numero != null && numero.longValue() != 0);
    }
    
	public static boolean isDate(String string)
	{
		return !isStringVazia(string) && string.matches(DATE);
	}
	
	public static byte[] converterBufferedImageToBytes(BufferedImage bufferedImage, String type) {
		
		try {
		
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write( bufferedImage, type, byteArrayOutputStream );
			byteArrayOutputStream.flush();
			byte[] imageInByte = byteArrayOutputStream.toByteArray();
			byteArrayOutputStream.close();
			return imageInByte;
			
		} catch (IOException e) {
			throw new ProcessException(e.getMessage());
		}
		
	}
	
	public static File converterBufferedImageToFile(BufferedImage bufferedImage, String filename, String type) {
		
		try {
		
			File outputfile = new File(filename.concat(".").concat(type));
			ImageIO.write(bufferedImage, type, outputfile);
			return outputfile;
			
		} catch (IOException e) {
			throw new ProcessException(e.getMessage());
		}
		
	}
    
}
