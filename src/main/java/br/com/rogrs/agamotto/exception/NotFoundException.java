package br.com.rogrs.agamotto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException{

    public NotFoundException(HttpStatus status) {
        super(status);
    }

    public NotFoundException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public NotFoundException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }


    
}
