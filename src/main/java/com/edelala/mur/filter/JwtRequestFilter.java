package com.edelala.mur.filter;

import com.edelala.mur.config.UserDetailsServiceImp;
import com.edelala.mur.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.SignatureException;

import java.io.IOException;

//@Component
//@RequiredArgsConstructor
//public class JwtRequestFilter extends OncePerRequestFilter {
//
//    private final UserDetailsService userDetailsService;
//    private final JwtUtil jwtUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//
//        final String authorizationHeader = request.getHeader("Authorization");
//
//        String username = null;
//        String jwt = null;
//
//        // Check if Authorization header exists and starts with "Bearer "
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            jwt = authorizationHeader.substring(7); // Extract the JWT token
//            try {
//                username = jwtUtil.extractUsername(jwt); // Extract username from token
//            } catch (Exception e) {
//                // Log and handle JWT parsing/validation errors (e.g., expired, invalid signature)
//                System.err.println("JWT token parsing failed: " + e.getMessage());
//                // Optionally, clear context or return error
//            }
//        }
//
//        // If username is extracted and no authentication is currently set in SecurityContext
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//
//            // Validate the token against UserDetails
//            if (jwtUtil.validateToken(jwt, userDetails)) {
//                // If token is valid, create an authentication token and set it in SecurityContext
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities());
//                usernamePasswordAuthenticationToken
//                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            }
//        }
//        chain.doFilter(request, response); // Continue the filter chain
//    }
//}
// jun 6


import com.edelala.mur.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Component
//@RequiredArgsConstructor
//public class JwtRequestFilter extends OncePerRequestFilter {
//
//    private final UserDetailsService userDetailsService;
//    private final JwtUtil jwtUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//
//        final String authorizationHeader = request.getHeader("Authorization");
//
//        String username = null;
//        String jwt = null;
//
//        System.out.println("JwtRequestFilter: Processing request to " + request.getRequestURI());
//
//        // Check if Authorization header exists and starts with "Bearer "
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            jwt = authorizationHeader.substring(7); // Extract the JWT token
//            System.out.println("JwtRequestFilter: Found Authorization header. JWT: " + jwt.substring(0, Math.min(jwt.length(), 30)) + "..."); // Log first 30 chars
//            try {
//                username = jwtUtil.extractUsername(jwt); // Extract username from token
//                System.out.println("JwtRequestFilter: Extracted username from JWT: " + username);
//            } catch (Exception e) {
//                // Log and handle JWT parsing/validation errors (e.g., expired, invalid signature)
//                System.err.println("JwtRequestFilter: JWT token parsing failed: " + e.getMessage());
//                // Optionally, clear context or return error
//            }
//        } else {
//            System.out.println("JwtRequestFilter: No valid Authorization header found for " + request.getRequestURI());
//        }
//
//        // If username is extracted and no authentication is currently set in SecurityContext
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            System.out.println("JwtRequestFilter: Username is present and SecurityContextHolder is null. Attempting authentication.");
//            UserDetails userDetails = null;
//            try {
//                userDetails = this.userDetailsService.loadUserByUsername(username);
//                System.out.println("JwtRequestFilter: Loaded UserDetails for: " + userDetails.getUsername() + ", Roles: " + userDetails.getAuthorities());
//            } catch (Exception e) {
//                System.err.println("JwtRequestFilter: Failed to load user details for " + username + ": " + e.getMessage());
//                // If user not found, token is invalid, don't proceed with validation
//                chain.doFilter(request, response);
//                return; // Stop processing this request in the filter if userDetails couldn't be loaded
//            }
//
//            // Validate the token against UserDetails
//            if (jwtUtil.validateToken(jwt, userDetails)) {
//                System.out.println("JwtRequestFilter: JWT token is VALID for user: " + username);
//                // If token is valid, create an authentication token and set it in SecurityContext
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities());
//                usernamePasswordAuthenticationToken
//                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                System.out.println("JwtRequestFilter: SecurityContextHolder updated successfully.");
//            } else {
//                System.err.println("JwtRequestFilter: JWT token is INVALID or expired for user: " + username);
//            }
//        } else if (username != null && SecurityContextHolder.getContext().getAuthentication() != null) {
//            System.out.println("JwtRequestFilter: Username is present, but SecurityContextHolder already has authentication. Skipping re-authentication.");
//        }
//
//        chain.doFilter(request, response); // Continue the filter chain
//    }
//}
//jun 13 11:11pm

//@Component
//@RequiredArgsConstructor
//public class JwtRequestFilter extends OncePerRequestFilter {
//
//    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
//
//    private final UserDetailsServiceImp userDetailsServiceImp;
//    private final JwtUtil jwtUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//
//        final String authorizationHeader = request.getHeader("Authorization");
//
//        String username = null;
//        String jwt = null;
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            jwt = authorizationHeader.substring(7);
//            try {
//                username = jwtUtil.extractUsername(jwt);
//                logger.debug("JwtRequestFilter: Extracted username from JWT: {}", username);
//            } catch (ExpiredJwtException e) {
//                logger.warn("JwtRequestFilter: JWT token has expired for user {}", e.getClaims().getSubject());
//            } catch (SignatureException e) {
//                logger.error("JwtRequestFilter: Invalid JWT signature: {}", e.getMessage());
//            } catch (Exception e) {
//                logger.error("JwtRequestFilter: Error parsing JWT: {}", e.getMessage(), e);
//            }
//        } else {
//            logger.debug("JwtRequestFilter: No Authorization header or does not start with Bearer for request to {}", request.getRequestURI());
//        }
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            logger.debug("JwtRequestFilter: Username is present ({}) and SecurityContextHolder is null. Attempting authentication.", username);
//
//            UserDetails userDetails = this.userDetailsServiceImp.loadUserByUsername(username);
//            logger.debug("JwtRequestFilter: Loaded UserDetails for: {}, Roles: {}", userDetails.getUsername(), userDetails.getAuthorities());
//
//            if (jwtUtil.validateToken(jwt, userDetails)) {
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                logger.debug("JwtRequestFilter: JWT token is VALID for user: {}", userDetails.getUsername());
//                logger.debug("JwtRequestFilter: SecurityContextHolder updated successfully.");
//            } else {
//                logger.warn("JwtRequestFilter: JWT token is INVALID for user: {}", username);
//            }
//        } else if (username != null) {
//            logger.debug("JwtRequestFilter: User {} already authenticated or no username to process.", username);
//        }
//
//        chain.doFilter(request, response);
//        logger.debug("JwtRequestFilter: Finished processing request to {}", request.getRequestURI());
//    }
//}

//@Component
//@RequiredArgsConstructor
//public class JwtRequestFilter extends OncePerRequestFilter {
//
//    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
//
//    private final UserDetailsServiceImp userDetailsServiceImp;
//    private final JwtUtil jwtUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//
//        final String authorizationHeader = request.getHeader("Authorization");
//
//        // --- NEW DEBUG LINE ---
//        logger.debug("JwtRequestFilter: === RAW HEADER CHECK === Request URL: {}, Authorization: '{}'", request.getRequestURI(), authorizationHeader);
//        // --- END NEW DEBUG LINE ---
//
//        String username = null;
//        String jwt = null;
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            jwt = authorizationHeader.substring(7);
//            try {
//                username = jwtUtil.extractUsername(jwt);
//                logger.debug("JwtRequestFilter: Extracted username from JWT: {}", username);
//            } catch (ExpiredJwtException e) {
//                logger.warn("JwtRequestFilter: JWT token has expired for user {}. Message: {}", e.getClaims().getSubject(), e.getMessage());
//            } catch (SignatureException e) {
//                logger.error("JwtRequestFilter: Invalid JWT signature: {}", e.getMessage());
//            } catch (Exception e) {
//                logger.error("JwtRequestFilter: Error parsing JWT (general exception): {}", e.getMessage(), e);
//            }
//        } else {
//            logger.debug("JwtRequestFilter: No Authorization header or does not start with Bearer for request to {}", request.getRequestURI());
//        }
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            logger.debug("JwtRequestFilter: Username is present ({}) and SecurityContextHolder is null. Attempting authentication.", username);
//
//            UserDetails userDetails = this.userDetailsServiceImp.loadUserByUsername(username);
//            logger.debug("JwtRequestFilter: Loaded UserDetails for: {}, Roles: {}", userDetails.getUsername(), userDetails.getAuthorities());
//
//            if (jwtUtil.validateToken(jwt, userDetails)) {
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                logger.debug("JwtRequestFilter: JWT token is VALID for user: {}", userDetails.getUsername());
//                logger.debug("JwtRequestFilter: SecurityContextHolder updated successfully.");
//            } else {
//                logger.warn("JwtRequestFilter: JWT token is INVALID for user: {}", username);
//            }
//        } else if (username != null) {
//            logger.debug("JwtRequestFilter: User {} already authenticated or no username to process.", username);
//        }
//
//        chain.doFilter(request, response);
//        logger.debug("JwtRequestFilter: Finished processing request to {}", request.getRequestURI());
//    }
//}
//july 14
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    private final UserDetailsServiceImp jwtUserDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        logger.debug("JwtRequestFilter: === RAW HEADER CHECK === Request URL: {}, Authorization: '{}'", request.getRequestURI(), requestTokenHeader);

        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwtToken);
                logger.debug("JwtRequestFilter: Extracted username from JWT: {}", username);
            } catch (IllegalArgumentException e) {
                logger.error("JwtRequestFilter Error: Unable to get JWT Token", e);
            } catch (ExpiredJwtException e) {
                logger.error("JwtRequestFilter Error: JWT Token has expired", e);
            } catch (SignatureException e) {
                logger.error("JwtRequestFilter Error: Invalid JWT Signature", e);
            } catch (MalformedJwtException e) {
                logger.error("JwtRequestFilter Error: Malformed JWT Token", e);
            } catch (Exception e) {
                logger.error("JwtRequestFilter Error: An unexpected error occurred during JWT parsing", e);
            }
        } else {
            logger.warn("JwtRequestFilter Warning: JWT Token does not begin with Bearer String or is missing for URL: {}", request.getRequestURI());
        }

        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.debug("JwtRequestFilter: Username is present ({}) and SecurityContextHolder is null. Attempting authentication.", username);
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            // if token is valid configure Spring Security to manually set authentication
            if (jwtUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the Context, we specify that the current user is authenticated.
                // So it passes the Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                logger.debug("JwtRequestFilter: Loaded UserDetails for: {}, Roles: {}", userDetails.getUsername(), userDetails.getAuthorities());
                logger.debug("JwtRequestFilter: JWT token is VALID for user: {}", username);
                logger.debug("JwtRequestFilter: SecurityContextHolder updated successfully. Current Auth: {}", SecurityContextHolder.getContext().getAuthentication());
            } else {
                logger.warn("JwtRequestFilter Warning: JWT token is NOT VALID for user: {}", username);
            }
        } else if (username != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            logger.debug("JwtRequestFilter: Username {} is present but SecurityContextHolder already has authentication. Skipping.", username);
        } else {
            logger.debug("JwtRequestFilter: No username extracted or no token provided for URL: {}", request.getRequestURI());
        }

        chain.doFilter(request, response);
        logger.debug("JwtRequestFilter: Finished processing request to {}", request.getRequestURI());
    }
}
