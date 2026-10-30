package by.alromos.java.advanced.spring.boot.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

  private final ObjectMapper objectMapper = new ObjectMapper()
      .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException
  ) throws IOException {
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    var problem = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, accessDeniedException.getMessage());
    Optional.ofNullable(request.getRequestURI())
        .map(URI::create)
        .ifPresent(problem::setInstance);
    response.getWriter()
        .write(objectMapper.writeValueAsString(problem));
  }

}
