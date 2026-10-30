package by.alromos.java.advanced.spring.boot.controller;

import by.alromos.java.advanced.spring.boot.config.JwtProperties;
import by.alromos.java.advanced.spring.boot.dto.AuthResponse;
import by.alromos.java.advanced.spring.boot.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider tokenProvider;
  private final JwtProperties jwtProperties;

  @PostMapping(
      path = "/token",
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
  )
  public AuthResponse token(
      @RequestParam String username,
      @RequestParam String password
  ) {
    Authentication auth = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password)
    );
    return new AuthResponse(
        tokenProvider.generateToken(auth),
        "bearer",
        jwtProperties.expiration().toSeconds()
    );
  }

}
