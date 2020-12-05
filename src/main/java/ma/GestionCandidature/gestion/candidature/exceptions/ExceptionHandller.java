package ma.GestionCandidature.gestion.candidature.exceptions;

import ma.GestionCandidature.gestion.candidature.responses.CandidatJPAExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RestController
public class ExceptionHandller {
    @ExceptionHandler
    public final ResponseEntity<Object> handlerUserEmailException(CandidatException exception, WebRequest response){
        CandidatJPAExceptionResponse userEmailExceptionResponse = new CandidatJPAExceptionResponse(exception.getMessage());

        return new ResponseEntity<>(userEmailExceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
