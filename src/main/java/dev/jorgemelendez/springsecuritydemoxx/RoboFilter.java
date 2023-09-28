package dev.jorgemelendez.springsecuritydemoxx;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class RoboFilter extends OncePerRequestFilter {

    private static final String HEADER = "x-robot-password";

    private final AuthenticationManager authenticationManager;

    public RoboFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        if (!Collections.list(request.getHeaderNames()).contains(HEADER)) {
            filterChain.doFilter(request, response);
            return;
        }

        String password = request.getHeader(HEADER);
        RobotAuthentication authRequest = RobotAuthentication.unauthenticated(password);
        try {
            Authentication authenticated = authenticationManager.authenticate(authRequest);
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authenticated);
            SecurityContextHolder.setContext(context);
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().println("You are not Ms. Robot");
        }
    }
}
