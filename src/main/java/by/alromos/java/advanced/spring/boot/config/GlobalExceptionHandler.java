package by.alromos.java.advanced.spring.boot.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  ResponseEntity<Object> handleUnexpected(Exception ex, WebRequest request) {
    logger.error("Unexpected error", ex);
    var status = HttpStatus.INTERNAL_SERVER_ERROR;
    var body = ProblemDetail.forStatusAndDetail(status, "An unexpected error occurred");

    return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
  }

}
