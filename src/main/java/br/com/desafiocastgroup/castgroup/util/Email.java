package br.com.desafiocastgroup.castgroup.util;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.desafiocastgroup.castgroup.exception.ProcessException;

@Component
public class Email {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void enviar(Mensagem mensagem) {
        
        try {
        	
        	MimeMessage msg = javaMailSender.createMimeMessage();
        	
			msg.setFrom(mensagem.getRemetente());
			
			 if(!Util.isListaVazia(mensagem.getDestinatarios())) {
		        InternetAddress[] addressesTO = { new InternetAddress(StringUtils.arrayToDelimitedString(mensagem.getDestinatarios().toArray(), ",")) };
		        msg.setRecipients(MimeMessage.RecipientType.TO, addressesTO);
			 }
	        
	        if(!Util.isListaVazia(mensagem.getCco())) {
	        	InternetAddress[] addressesCC = { new InternetAddress(StringUtils.arrayToDelimitedString(mensagem.getCco().toArray(), ",")) };
	        	msg.setRecipients(MimeMessage.RecipientType.CC, addressesCC);
	        }
	        
	        msg.setSubject(mensagem.getAssunto());
	        msg.setSentDate(new Date());
	        
	        MimeBodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setContent(mensagem.getCorpo(), "text/html");
	 
	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(messageBodyPart);
	 
	        anexarImagensAoCorpoEmail(multipart, mensagem.getImagensCorpo());
	        anexarArquivos(multipart, mensagem.getAnexos());
	 
	        msg.setContent(multipart);
	        
	        javaMailSender.send(msg);
        
		} catch (MessagingException e) {
			throw new ProcessException(e.getMessage());
		}
        
	}
	
	private void anexarImagensAoCorpoEmail(Multipart multipart, Map<String, String> imagensCorpo) {
		
		try {
			
	        if (imagensCorpo != null && !imagensCorpo.isEmpty()) {
	        	
	            Set<String> setImageID = imagensCorpo.keySet();
	             
	            for (String contentId : setImageID) {
	                MimeBodyPart imagePart = new MimeBodyPart();
	                imagePart.setHeader("Content-ID", "<" + contentId + ">");
	                imagePart.setDisposition(MimeBodyPart.INLINE);
	                 
	                String imageFilePath = imagensCorpo.get(contentId);
	                
	                imagePart.attachFile(imageFilePath);
	
	                multipart.addBodyPart(imagePart);
	            }
	        }
	        
		} catch (MessagingException | IOException e) {
			throw new ProcessException(e.getMessage());
		}
		
	}
	
	private void anexarArquivos(Multipart multipart, List<String> arquivos) {
		
		try {
			
	        if (!Util.isListaVazia(arquivos)) {
	        	
	            for (String filePath : arquivos) {
	                MimeBodyPart attachPart = new MimeBodyPart();
                    attachPart.attachFile(filePath);
	                multipart.addBodyPart(attachPart);
	            }
	            
	        }
	        
		} catch (MessagingException | IOException e) {
			throw new ProcessException(e.getMessage());
		}
		
	}

}
