package br.com.desafiocastgroup.castgroup.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Error {

    private final String message;
    private final String field;
    private final Object parameter;
}
