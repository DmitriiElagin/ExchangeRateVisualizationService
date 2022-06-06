package elagin.dmitrii.ExchangeRateVisualizationService.controller;

import elagin.dmitrii.ExchangeRateVisualizationService.error.InvalidCurrencyCodeException;
import elagin.dmitrii.ExchangeRateVisualizationService.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorController {
    public final static String CONSTRAINT_VIOLATION_ERROR_MESSAGE = "Constraint violation of parameter value";
    public final static String CONSTRAINT_VIOLATION_ERROR = "Invalid parameter value";

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception exception, HttpServletRequest request) {

        var status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponse error = new ErrorResponse(status, request.getRequestURI(), exception.getMessage());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException exception,
                                                                            HttpServletRequest request) {
        var status = HttpStatus.BAD_REQUEST;

        ErrorResponse error = new ErrorResponse(status.value(), CONSTRAINT_VIOLATION_ERROR, request.getRequestURI(),
                CONSTRAINT_VIOLATION_ERROR_MESSAGE, getErrorMessages(exception));

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(InvalidCurrencyCodeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCurrencyCodeException(Exception e, HttpServletRequest request) {
        System.out.println(request.getRequestURI());
        var status = HttpStatus.BAD_REQUEST;

        ErrorResponse error = new ErrorResponse(status.value(),
                InvalidCurrencyCodeException.REASON, request.getRequestURI(), e.getMessage());

        return ResponseEntity.status(status).body(error);
    }

    public Set<String> getErrorMessages(ConstraintViolationException exception) {
        var violations = exception.getConstraintViolations();

        return violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());
    }
}
