package br.com.desafiocastgroup.castgroup.util;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import br.com.desafiocastgroup.castgroup.exception.ProcessException;

public class Util {

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
			return Period.between(date, anotherDate).getMonths();
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
	
	public static String converterObjectToStringJson(Object object) {
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(object);
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
    
}
