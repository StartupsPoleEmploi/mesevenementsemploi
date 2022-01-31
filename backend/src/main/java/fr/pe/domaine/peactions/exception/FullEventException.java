package fr.pe.domaine.peactions.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class FullEventException extends ResponseStatusException {
    private static final long serialVersionUID = 1;

    private static final Logger logger = LoggerFactory.getLogger(FullEventException.class);

    public FullEventException(String message) {
        super(HttpStatus.FORBIDDEN, message);
        logger.debug(this.getMessage());
    }
}
