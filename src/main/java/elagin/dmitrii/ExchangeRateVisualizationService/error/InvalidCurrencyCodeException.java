package elagin.dmitrii.ExchangeRateVisualizationService.error;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid currency code")
public class InvalidCurrencyCodeException extends RuntimeException {
    public static final String REASON = "Invalid currency code";

    public InvalidCurrencyCodeException(String message) {
        super(message);
    }
}
