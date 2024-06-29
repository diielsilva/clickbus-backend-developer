package com.dev.clickbus.handlers;

import com.dev.clickbus.dtos.ErrorResponse;
import com.dev.clickbus.exceptions.PlaceNotFoundException;
import com.mongodb.MongoWriteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(value = PlaceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePlaceNotFound(PlaceNotFoundException exception) {
        ErrorResponse response = new ErrorResponse(exception.getMessage());
        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(value = MongoWriteException.class)
    public ResponseEntity<ErrorResponse> handleMongoWrite(MongoWriteException exception) {
        ErrorResponse response = new ErrorResponse("Name constraint is already in use!");
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

}
