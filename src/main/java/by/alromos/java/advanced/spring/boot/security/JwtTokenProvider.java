package by.alromos.java.advanced.spring.boot.security;

import by.alromos.java.advanced.spring.boot.config.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  private final JwtEncoder encoder;
  private final JwtProperties jwtProperties;

  public String generateToken(Authentication authentication) {
    var now = Instant.now();
    var roles = authentication.getAuthorities()
        .stream()
        .filter(SimpleGrantedAuthority.class::isInstance)
        .map(GrantedAuthority::getAuthority)
        .toList();

    var claims = JwtClaimsSet.builder()
        .issuer("self")
        .issuedAt(now)
        .expiresAt(now.plus(jwtProperties.expiration()))
        .subject(authentication.getName())
        .claim("roles", roles)
        .build();

    var header = JwsHeader.with(MacAlgorithm.HS256).build();
    return encoder.encode(JwtEncoderParameters.from(header, claims))
        .getTokenValue();
  }

}
