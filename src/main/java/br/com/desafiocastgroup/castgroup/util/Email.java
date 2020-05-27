package br.com.desafiocastgroup.castgroup.util;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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

	private JavaMailSender javaMailSender;
	
	@Autowired
	public Email(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

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
	
	private void anexarImagensAoCorpoEmail(Multipart multipart, Map<String,Arquivo> imagensCorpo) {
		
		try {
			
	        if (imagensCorpo != null && !imagensCorpo.isEmpty()) {
	        	
	            Set<String> setImageID = imagensCorpo.keySet();
	             
	            for (String contentId : setImageID) {
	            	
	                MimeBodyPart imagePart = new MimeBodyPart();
	                Arquivo arquivo = imagensCorpo.get(contentId);
	                imagePart.setHeader("Content-ID", "<" + contentId + ">");
	                imagePart.setHeader(arquivo.getContentType(),"image/"+arquivo.getFileType());
	                imagePart.setFileName(arquivo.getFileName().concat(".").concat(arquivo.getFileType()));
	                DataSource fds = new FileDataSource(arquivo.getFile());
	                imagePart.setDataHandler(new DataHandler(fds));
	                imagePart.setDisposition(MimeBodyPart.INLINE);
	
	                multipart.addBodyPart(imagePart);
	            }
	        }
	        
		} catch (MessagingException e) {
			throw new ProcessException(e.getMessage());
		}
		
	}
	
	private void anexarArquivos(Multipart multipart, List<Arquivo> arquivos) {
		
		try {
			
	        if (arquivos != null) {
	        	
	            for (Arquivo arquivo : arquivos) {
	                MimeBodyPart attachPart = new MimeBodyPart();
	                attachPart.setFileName(arquivo.getFileName().concat(".").concat(arquivo.getFileType()));
	                DataSource fds = new FileDataSource(arquivo.getFile());
	                attachPart.setDataHandler(new DataHandler(fds));
	                multipart.addBodyPart(attachPart);
	            }
	            
	        }
	        
		} catch (MessagingException e) {
			throw new ProcessException(e.getMessage());
		}
		
	}

}
