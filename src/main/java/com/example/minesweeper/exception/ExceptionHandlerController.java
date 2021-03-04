package com.example.minesweeper.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<Object> processApiException(ApiException ex) {
    ApiError apiError = new ApiError(HttpStatus.resolve(ex.getCode()));
    apiError.setDebugMessage("error!");
    apiError.setMessage(ex.getMessage());
    apiError.setCode(ex.getCode());

    logger.error(ex.getMessage());

    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(RuntimeException.class)
  protected ResponseEntity<Object> runtimeException(RuntimeException ex, WebRequest request) {
    ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
    apiError.setMessage("Error de parseo de Json, contacte al administrador");
    logger.error(ex.getMessage());
    return buildResponseEntity(apiError);
  }


  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    String message = "Hubo un error, por favor contacte al administrador";
    logger.error(ex.getMessage());
    return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, message, ex));
  }

  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());

  }
}
