package com.software.noteapp.exceptions;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRedourceNotfoundException(ResourceNotFoundException ex){
    String message = ex.getMessage();
    return ResponseEntity.badRequest().body(new ErrorResponse(400,"Resource Not found",message,LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequest(InvalidRequestException ex) {
        ErrorResponse error = new ErrorResponse(
                400,
                "Invalid Request",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> hadleUnexpectedError(Exception ex){
        return ResponseEntity.status(500).body(new ErrorResponse(500,"Something went wrong",ex.getMessage(),LocalDateTime.now()));
    }

    @Override
    public @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
        String message = ex.getBindingResult().getFieldErrors().stream().map(e->e.getField()+": "+e.getDefaultMessage()).collect(Collectors.joining(", "));
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(400, "Validation Failed", message, LocalDateTime.now()));
    }

    @Override
    public @Nullable ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
        String message = ex.getMessage();
        ErrorResponse error = new ErrorResponse(400,"Argument type missmatch",message,LocalDateTime.now());
        return ResponseEntity.badRequest().body(error);
    }




}
