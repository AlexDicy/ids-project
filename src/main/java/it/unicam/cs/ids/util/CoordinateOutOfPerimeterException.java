package it.unicam.cs.ids.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CoordinateOutOfPerimeterException extends RuntimeException {
    public CoordinateOutOfPerimeterException() {
    }

    public CoordinateOutOfPerimeterException(String message) {
        super(message);
    }

    @ResponseBody
    public String getErrorMessage() {
        return getMessage();
    }
}
