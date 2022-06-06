package elagin.dmitrii.ExchangeRateVisualizationService.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid date")
public class InvalidDateException extends RuntimeException {
    public static final String REASON = "Invalid date";

    public InvalidDateException() {
    }

    public InvalidDateException(String message) {
        super(message);
    }
}
