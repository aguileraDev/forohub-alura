package com.alura.forohub.security;

import com.alura.forohub.utility.exceptions.BearerTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    private final SecurityService securityService;
    private final JwtService jwtService;
    @Autowired
    public SecurityFilter(SecurityService securityService, JwtService jwtService){
        this.securityService = securityService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String uri = request.getRequestURI();
        final String method = request.getMethod();

        boolean isLoginOrSwaggerEndpoint = false;

        if(uri.contains("login")  && method.equalsIgnoreCase("POST")){
            isLoginOrSwaggerEndpoint = true;
        }

        if(uri.contains("swagger") || uri.contains("api-docs")){
            isLoginOrSwaggerEndpoint = true;
        }

        if(!isLoginOrSwaggerEndpoint){
            String authHeader;
            String token;

           try {
               authHeader = request.getHeader("Authorization");
               token = authHeader.replace("Bearer ","").trim();

           }catch (NullPointerException e){
               String message = "Bearer token nulo";
               logger.error(message);
               throw new BearerTokenException(message);
           }

            if(authHeader.isBlank() || token.equalsIgnoreCase("bearer")){
                String message = "Token invalido o vacio / uri: " + request.getRequestURI();
                logger.error(message);
                throw new BearerTokenException(message);
            }

            String username = jwtService.validateToken(token);

            securityService.createUserSession(username);


        }

        filterChain.doFilter(request, response);
    }
}
