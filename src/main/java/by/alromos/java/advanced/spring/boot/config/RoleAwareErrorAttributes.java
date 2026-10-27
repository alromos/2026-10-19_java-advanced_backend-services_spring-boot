package by.alromos.java.advanced.spring.boot.config;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.webmvc.error.DefaultErrorAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class RoleAwareErrorAttributes extends DefaultErrorAttributes {

  @Override
  public Map<String, Object> getErrorAttributes(
      WebRequest webRequest,
      ErrorAttributeOptions options
  ) {
    return super.getErrorAttributes(
        webRequest,
        userHasAdminRole()
            ? options.including(ErrorAttributeOptions.Include.STACK_TRACE)
            : options.excluding(ErrorAttributeOptions.Include.STACK_TRACE)
    );
  }

  private boolean userHasAdminRole() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null) {
      return false;
    }

    return auth.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .anyMatch("ROLE_ADMIN"::equals);
  }

}
