package com.ecommerce.productservice.infrastructure.adapter.securityauth;


import com.ecommerce.productservice.domain.bussinesexception.ExceptionMessages;
import com.ecommerce.productservice.domain.model.tokenprovider.gateway.TokenProviderRepository;
import com.ecommerce.productservice.domain.model.usersession.UserSession;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final TokenProviderRepository tokenProvider;

    private static final String USER_LOGIN = "/api/v1/users/login";
    private static final String USER_REGISTER = "/api/v1/users/register";
    private static final String SWAGGER_UI = "/swagger-ui.html";

    private static final String TYPE_TOKEN = "Bearer ";
    private static final String TYPE_ROLE = "ROLE_";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Allow login, and register route
        if (path.equals(USER_LOGIN) || path.equals(USER_REGISTER)
                || path.equals(SWAGGER_UI)
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith(TYPE_TOKEN)) {
            String token = authHeader.substring(7);

            try {

                UserSession userSession = tokenProvider.validateToken(token);

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userSession,
                        null,
                        List.of(new SimpleGrantedAuthority(TYPE_ROLE + userSession.getRole()))
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Authorized role '{}', token validated successfully", userSession.getRole());

            } catch (Exception e) {
                log.error("Error validating JWT token: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(ExceptionMessages.INVALID_TOKEN);
                return;
            }
        }

        // The flow continues as long as the authorization token and role have been validated
        filterChain.doFilter(request, response);
    }
}
