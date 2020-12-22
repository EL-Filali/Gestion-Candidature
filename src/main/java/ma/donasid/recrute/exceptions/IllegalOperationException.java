package ma.donasid.recrute.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalOperationException extends Exception {
    private final String message;
    public IllegalOperationException(String s) {
        message=s;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
