package ma.donasid.recrute.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CandidatException extends Exception {



    public CandidatException(String message) {
        super(message);
    }

}
