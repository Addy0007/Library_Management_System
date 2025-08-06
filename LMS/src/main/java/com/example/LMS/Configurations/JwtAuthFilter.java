package com.example.LMS.Configurations;

import com.example.LMS.Service.UsersService;
import com.example.LMS.entity.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsersService usersService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;


        if (isPublicEndpoint(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                username = jwtService.extractEmail(token);
                log.debug("Extracted username from token: {}", username);
            } catch (Exception e) {
                log.error("Failed to extract username from token: {}", e.getMessage());
                filterChain.doFilter(request, response);
                return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = usersService.loadUserByUsername(username);
                log.debug("Loaded user details for: {}, authorities: {}",
                        username, userDetails.getAuthorities());

                if (jwtService.validateToken(token, userDetails)) {
                    // Use the authorities from UserDetails (which includes the role)
                    // This ensures consistency with what's stored in the database
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()); // Use UserDetails authorities

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.debug("Authentication set for user: {} with authorities: {}",
                            username, userDetails.getAuthorities());
                } else {
                    log.warn("Token validation failed for user: {}", username);
                }
            } catch (Exception e) {
                log.error("Authentication failed for user {}: {}", username, e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(String uri) {
        return uri.startsWith("/api/auth/") ||
                uri.startsWith("/v3/api-docs") ||
                uri.startsWith("/swagger-ui");
    }
}