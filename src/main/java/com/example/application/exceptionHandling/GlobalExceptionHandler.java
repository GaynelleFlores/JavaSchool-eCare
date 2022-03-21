package com.example.application.exceptionHandling;

import com.example.application.exceptions.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final Logger logger;

    @ExceptionHandler(value
            = {BusinessLogicException.class})
    public ResponseEntity<String> method(BusinessLogicException e) {
        logger.error(e.getMessage());
        return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value
            = {ConstraintViolationException.class})
    public ResponseEntity<String> method(ConstraintViolationException e) {
        logger.error(e.getMessage());
        return new ResponseEntity<String>("Database error. Check unique fields in your form.", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value
            = {RuntimeException.class})
    public ResponseEntity<String> method(RuntimeException e) {
        logger.error(e.getMessage());
        return new ResponseEntity<String>("Internal server error.", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
