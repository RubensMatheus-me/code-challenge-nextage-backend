package com.nextage.codeChallenge.config;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.QueryTimeoutException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    private Map<String, Object> handleAll(RuntimeException e, HttpServletRequest request) {
        return createErrorMessage(HttpStatus.BAD_REQUEST, e, request);
    }

    @ExceptionHandler({EntityNotFoundException.class, NoSuchElementException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private Map<String, Object> handleNotFound(RuntimeException e, HttpServletRequest request) {
        return createErrorMessage(HttpStatus.NOT_FOUND, e, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CONFLICT)
    private Map<String, Object> handleDataIntegrityViolation(RuntimeException e, HttpServletRequest request) {
        return createErrorMessage(HttpStatus.CONFLICT, e, request);
    }

    @ExceptionHandler(QueryTimeoutException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
    private Map<String, Object> handleQueryTimeoutException(RuntimeException e, HttpServletRequest request) {
        return createErrorMessage(HttpStatus.REQUEST_TIMEOUT, e, request);
    }

    @ExceptionHandler(PersistenceException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    private Map<String, Object> handlePersistenceException(RuntimeException e, HttpServletRequest request) {
        return createErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, e, request);
    }

    private Map<String, Object> createErrorMessage(HttpStatus status, Exception e, HttpServletRequest request) {
        var message = new HashMap<String, Object>();

        message.put("Timestamp", String.valueOf(new Date().getTime()));
        message.put("Error", status.getReasonPhrase());
        message.put("Status", status.value());
        message.put("Message", e.getMessage());
        message.put("Path", String.valueOf(request.getRequestURI()));

        return message;
    }
}
