package com.ecommerce.productservice.infrastructure.adapter.securityauth;


import com.ecommerce.productservice.domain.model.tokenprovider.gateway.TokenProviderRepository;
import com.ecommerce.productservice.domain.model.usersession.UserSession;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Slf4j
@Component
public class JwtTokenProvider implements TokenProviderRepository {

    public static final String EMAIL = "email";
    public static final String ROLE = "role";
    public static final String TYPE = "Bearer";

    private final SecretKey secret;
    private final long expiration;


    public JwtTokenProvider(@Value("${JWT_SECRET}") String jwtSecret,
                            @Value("${JWT_EXPIRATION}") long expiration) {

        this.secret = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;

    }


    @Override
    public UserSession validateToken(String token) {

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            log.info("Token validated successfully");

            return new UserSession(
                    Long.valueOf(claims.getSubject()),
                    claims.get(EMAIL, String.class),
                    claims.get(ROLE, String.class)
            );

        } catch (JwtException e) {
            log.error("Error in validateToken method, failed with message: {}", e.getMessage());
            throw new BadCredentialsException("Invalid token");
        }
    }


}

