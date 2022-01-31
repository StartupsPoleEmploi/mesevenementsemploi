package fr.pe.domaine.peactions.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<CustomErrorResponse> customHandleNotFound(ResponseStatusException ex, WebRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), ex.getStatus().value());
        return new ResponseEntity<>(errors, ex.getStatus());
    }
}
