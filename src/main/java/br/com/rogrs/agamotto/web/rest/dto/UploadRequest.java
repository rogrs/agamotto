package br.com.rogrs.agamotto.web.rest.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadRequest {

    private MultipartFile file;
    private String tipo;

}
