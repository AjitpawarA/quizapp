package com.alpha.qusizapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alpha.qusizapp.dto.ResponceStructure;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<ResponceStructure<Object>> handleQuestionNotFoundException(QuestionNotFoundException ex) {
        ResponceStructure<Object> response = new ResponceStructure<>();
        response.setStatuscode(HttpStatus.NOT_FOUND.value());
        response.setMessage(ex.getMessage());
        response.setData(null);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(QuestionSaveException.class)
    public ResponseEntity<ResponceStructure<Object>> handleQuestionSaveException(QuestionSaveException ex) {
        ResponceStructure<Object> response = new ResponceStructure<>();
        response.setStatuscode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(ex.getMessage());
        response.setData(null);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponceStructure<Object>> handleGenericException(Exception ex) {
        ResponceStructure<Object> response = new ResponceStructure<>();
        response.setStatuscode(HttpStatus.BAD_REQUEST.value());
        response.setMessage("An unexpected error occurred: " + ex.getMessage());
        response.setData(null);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
