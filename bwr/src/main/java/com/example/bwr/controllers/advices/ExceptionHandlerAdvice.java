package com.example.bwr.controllers.advices;

import com.example.bwr.exceptions.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler
  public ResponseEntity handleValidationException(ValidationException e) {
    return buildResponseEntityFrom(e);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatusCode status, WebRequest request) {
    String bodyOfResponse = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bodyOfResponse);
  }

  public static ResponseEntity buildResponseEntityFrom(ValidationException e) {
    String message = e.getMessage();
    HttpStatus httpStatus = e.getHttpStatus() != null ? e.getHttpStatus() : HttpStatus.BAD_REQUEST;
    return ResponseEntity.status(httpStatus).body(message);
  }
}
