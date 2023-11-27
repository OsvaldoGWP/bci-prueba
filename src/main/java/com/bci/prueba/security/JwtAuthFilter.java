package com.bci.prueba.security;

import com.bci.prueba.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "Bearer ";
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (ObjectUtils.isEmpty(authHeader) || !authHeader.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        log.debug("JwtAuthFilter in action");
        var jwt = authHeader.substring(TOKEN_PREFIX.length());
        var userEmail = jwtService.extractUsername(jwt);
        if (hasText(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = userService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt)) {
                var securityContext = SecurityContextHolder.createEmptyContext();
                var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request, response);
    }

}
