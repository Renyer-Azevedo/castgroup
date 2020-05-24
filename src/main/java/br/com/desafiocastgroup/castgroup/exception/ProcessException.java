package br.com.desafiocastgroup.castgroup.exception;

import org.springframework.http.HttpStatus;

public class ProcessException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5259920780770313953L;
	private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    
    public ProcessException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
    
    public ProcessException(String message) {
        super(message);
    }

}
