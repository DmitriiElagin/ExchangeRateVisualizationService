package elagin.dmitrii.ExchangeRateVisualizationService.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final int status;
    private final String error;
    private final String path;
    private final String message;
    private final Timestamp timestamp;
    private final Set<String> errors;

    public ErrorResponse(int status, String error, String path, String message, Set<String> errors) {
        this.status = status;
        this.error = error;
        this.path = path;
        this.message = message;
        timestamp = new Timestamp(System.currentTimeMillis());
        this.errors = errors;
    }

    public ErrorResponse(int status, String error, String path, String message) {
        this(status, error, path, message, null);
    }

    public ErrorResponse(HttpStatus status, String path, String message) {
        this(status.value(), status.name(), path, message, null);
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Set<String> getErrors() {
        return errors;
    }

    public String getPath() {
        return path;
    }
}
