package br.com.desafiocastgroup.castgroup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import br.com.desafiocastgroup.castgroup.exception.Error;

@Getter
@AllArgsConstructor
public class ErrorDto {

    private final String message;
    private final int code;
    private final String status;
    private final String objectName;
    private final List<Error> errors;
}
