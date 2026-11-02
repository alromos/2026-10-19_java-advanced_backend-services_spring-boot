package by.alromos.java.advanced.spring.boot.config;

import by.alromos.java.advanced.spring.boot.exception.NotFoundException;
import java.util.ArrayList;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  ResponseEntity<ProblemDetail> handleNotFound(NotFoundException ex) {
    return createResponseEntity(
        HttpStatus.BAD_REQUEST,
        ex.getMessage()
    );
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request
  ) {
    var errors = new ArrayList<String>();

    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.add(fieldName + ": " + errorMessage);
    });

    var body = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid request content.");
    body.setProperty("errors", errors);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  ResponseEntity<ProblemDetail> handleUnexpected(Exception ex) {
    logger.error("Unexpected error", ex);

    return createResponseEntity(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "An unexpected error occurred"
    );
  }

  private ResponseEntity<ProblemDetail> createResponseEntity(HttpStatus status, String message) {
    return new ResponseEntity<>(
        ProblemDetail.forStatusAndDetail(status, message),
        status
    );
  }

}
