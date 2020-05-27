package br.com.desafiocastgroup.castgroup.util;

import java.io.File;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Arquivo {
	
    private String fileName;
    private String fileType;
    private String contentType;
    private File file;

}
