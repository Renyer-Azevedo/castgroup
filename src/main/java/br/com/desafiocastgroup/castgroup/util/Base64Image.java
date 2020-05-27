package br.com.desafiocastgroup.castgroup.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

public class Base64Image {
	
    private byte[] imageBytes;
    private String fileName;
    private String fileType;
    private int contentLength;
    private String contentType;
    private InputStream file;
    private boolean hasErrors;
    private String errorMessage;
    private static final List<String> VALID_FILE_TYPES = new ArrayList<>(3);

    static {
        VALID_FILE_TYPES.add("jpg");
        VALID_FILE_TYPES.add("jpeg");
        VALID_FILE_TYPES.add("png");
    }

    public Base64Image() {
		super();
	}

	public Base64Image(String base64ImageEncoded, String fileName) {
        this.fileName = fileName;
        build(base64ImageEncoded);
    }

    private void build(String base64ImageEncoded) {
    	
        String[] base64Components = base64ImageEncoded.split(",");

        if (base64Components.length != 2) {
            this.hasErrors = true;
            this.errorMessage = "Invalid base64 data: " + base64ImageEncoded;
        }

        if (!this.hasErrors) {
            String base64Data = base64Components[0];
            this.fileType = base64Data.substring(base64Data.indexOf('/') + 1, base64Data.indexOf(';'));

            if (!VALID_FILE_TYPES.contains(fileType)) {
                this.hasErrors = true;
                this.errorMessage = "Invalid file type: " + fileType;
            }

            if (!this.hasErrors) {
                String base64Image = base64Components[1];
                this.imageBytes = DatatypeConverter.parseBase64Binary(base64Image);
                this.file = new ByteArrayInputStream(this.imageBytes);
                this.contentLength = this.imageBytes.length;
                this.contentType = "image/"+this.fileType; 
            }
        }
    	
    }

	public byte[] getImageBytes() {
		return imageBytes;
	}

	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public InputStream getFile() {
		return file;
	}

	public void setFile(InputStream file) {
		this.file = file;
	}

	public boolean isHasErrors() {
		return hasErrors;
	}

	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
