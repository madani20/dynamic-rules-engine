package org.mad.engine.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<ErrorResponse> handleValidateException(ValidateException validateException,
                                                                 HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                validateException.getMessage(),
                "Invalid evaluation!",
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(RuleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRuleNotFoundException(RuleNotFoundException ruleNotFoundException,
                                                                               HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ruleNotFoundException.getMessage(),
                "Invalid rule!",
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(400));
    }

    /**
     * Gestion des exceptions génériques.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred.",
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
