package br.com.rogrs.agamotto.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Data
public class BadRequestAlertException  extends ResponseStatusException {


    private String entityName;

    private String message;

    public BadRequestAlertException(HttpStatus status) {
        super(status);
    }

    public BadRequestAlertException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public BadRequestAlertException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }


    public BadRequestAlertException(String message, String entityName, String reason) {
        super(HttpStatus.BAD_REQUEST);

        this.entityName = entityName;
        this.message =message;

    }
}
