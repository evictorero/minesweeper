package com.example.minesweeper.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;


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
    apiError.setMessage("Internal server error");
    logger.error(ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
    apiError.setMessage("Validation error");
    logger.error(ex.getMessage());

    apiError.setErrors(ex.getBindingResult().getAllErrors()
            .stream()
            .map((error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              return fieldName.concat(" ").concat(errorMessage);
            })
            .collect(Collectors.toList()));

    return buildResponseEntity(apiError);
  }


  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    String message = "There was an error, please contact administrators";
    logger.error(ex.getMessage());
    return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, message, ex));
  }

  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());

  }
}
