package by.alromos.java.advanced.spring.boot.config;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Optional;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.webmvc.autoconfigure.error.AbstractErrorController;
import org.springframework.boot.webmvc.error.ErrorAttributes;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ProblemDetailErrorController extends AbstractErrorController {

  public ProblemDetailErrorController(ErrorAttributes errorAttributes) {
    super(errorAttributes);
  }

  @RequestMapping
  public ResponseEntity<ProblemDetail> handleError(HttpServletRequest request) {
    var status = getStatus(request);
    var options = ErrorAttributeOptions.defaults()
        .including(Include.MESSAGE);
    var attributes = getErrorAttributes(request, options);
    var detail = (String) attributes.getOrDefault("message", status.getReasonPhrase());
    var problem = ProblemDetail.forStatusAndDetail(status, detail);
    Optional.ofNullable(attributes.getOrDefault("path", request.getRequestURI()))
        .filter(String.class::isInstance)
        .map(String.class::cast)
        .map(URI::create)
        .ifPresent(problem::setInstance);

    return ResponseEntity.status(status)
        .body(problem);
  }

}
