package br.com.desafiocastgroup.castgroup.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.desafiocastgroup.castgroup.dto.ErrorDto;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	
    	List<ErrorObject> errors = e.getBindingResult().getFieldErrors().stream()
																		         .map(error -> new ErrorObject(error.getDefaultMessage(), error.getField(), error.getRejectedValue()))
																		         .collect(Collectors.toList());
    	
    	ErrorDto errorResponse = new ErrorDto("Requisição possui campos inválidos", status.value(),status.getReasonPhrase(), e.getBindingResult().getObjectName(), errors);
    	return new ResponseEntity<>(errorResponse, status);
    }
    
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
    	return new ResponseEntity<>(new ErrorDto(ex.getMessage(),INTERNAL_SERVER_ERROR.value(),INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getClass().getSimpleName(), null), INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(ProcessException.class)
    protected ResponseEntity<Object> handleProcessException(ProcessException processException, WebRequest request) {
    	return new ResponseEntity<>(new ErrorDto(processException.getMessage(),processException.getHttpStatus().value(),processException.getHttpStatus().getReasonPhrase(), processException.getClass().getSimpleName(), null), processException.getHttpStatus());
    }
    
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> onConstraintValidationException(ConstraintViolationException e) {
    	
      List<ErrorObject> errors = e.getConstraintViolations().stream()
	  															   .map(error -> new ErrorObject(error.getMessage(),error.getPropertyPath().toString(),error.getInvalidValue()))
	  															   .collect(Collectors.toList());
      
      ErrorDto errorResponse = new ErrorDto("Requisição possui campos inválidos", BAD_REQUEST.value(),BAD_REQUEST.getReasonPhrase(), e.getClass().getName(), errors);
      return new ResponseEntity<>(errorResponse, BAD_REQUEST);
      
    }
    
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	return new ResponseEntity<>(new ErrorDto(ex.getMessage(),INTERNAL_SERVER_ERROR.value(),INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getClass().getSimpleName(), null), INTERNAL_SERVER_ERROR);
    }
	
}
