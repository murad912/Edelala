package com.edelala.mur.config;


import com.edelala.mur.filter.JwtRequestFilter;
import com.edelala.mur.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true) // Enable @PreAuthorize annotations
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final UserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder; // Inject PasswordEncoder directly
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder);
//        return authProvider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .csrf(csrf -> csrf.disable()) // For SPA, consider enabling with proper token handling
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/auth/**").permitAll() // Allow registration and other auth endpoints
//                        .requestMatchers(HttpMethod.GET, "/api/properties/**").permitAll() // Allow public viewing of properties
//                        .requestMatchers("/api/rent-requests/**").authenticated() // Requires authentication for rent requests
//                        .requestMatchers("/api/payments/**").authenticated() // Requires authentication for payments
//                        .requestMatchers("/api/users/**").authenticated() // Requires authentication for user info
//                        .requestMatchers("/api/properties/owner/**").hasRole("OWNER") // Owner-specific endpoints
//                        .requestMatchers("/api/rent-requests/owner/**").hasRole("OWNER") // Owner-specific rent request management
//                        .anyRequest().authenticated()
//                )
////                .httpBasic(httpBasic -> {}); // Use basic auth for simplicity in this example; consider JWT
//                .exceptionHandling(exceptions -> exceptions
//                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))) // Return 401 instead of redirect or pop-up
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)); // Ensure sessions are created if needed
//        http.authenticationProvider(authenticationProvider());
//
//        return http.build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Match your React app's port
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}





import com.edelala.mur.filter.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final UserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtRequestFilter jwtRequestFilter;
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder);
//        return authProvider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/auth/**").permitAll() // Allow registration, login, forgot/reset password
//                        .requestMatchers(HttpMethod.GET, "/api/properties/**").permitAll() // Allow public viewing of properties
//                        .requestMatchers("/api/rent-requests").hasRole("RENTER") // Allow authenticated RENTERS to submit requests
//                        .requestMatchers("/api/rent-requests/renter/**").hasRole("RENTER") // Allow RENTERS to view their own requests
//                        .requestMatchers("/api/rent-requests/owner/**").hasRole("OWNER") // Allow OWNERS to view/manage their requests
//                        .anyRequest().authenticated() // All other requests require authentication
//                )
//                .exceptionHandling(exceptions -> exceptions
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                );
//
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//
//        http.authenticationProvider(authenticationProvider());
//
//        return http.build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Match your React app's port
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//} jun 7 8:46 PM

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true) // Enable @PreAuthorize annotations
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final UserDetailsService userDetailsService; // Your custom UserDetailsService implementation
//    private final PasswordEncoder passwordEncoder;
//    private final JwtRequestFilter jwtRequestFilter; // Your JWT authentication filter
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService); // Set your UserDetailsService
//        authProvider.setPasswordEncoder(passwordEncoder);
//        return authProvider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs
//                .authorizeHttpRequests(auth -> auth
//                        // Publicly accessible paths (no authentication required)
//                        .requestMatchers("/api/auth/**").permitAll() // Allow registration, login, forgot/reset password
//                        .requestMatchers(HttpMethod.GET, "/api/properties/**").permitAll() // Allow public viewing of all properties (GET requests)
//                        .requestMatchers("/uploads/**").permitAll() // Allow access to uploaded files (images, etc.)
//
//                        // Role-based authorization rules
//                        // Order matters: more specific rules usually go before more general ones.
//                        // FIX: Changed .hasAuthority() to .hasRole() for consistency with UserDetailsService and @PreAuthorize.
//                        .requestMatchers("/api/admin/**").hasRole("ADMIN") // Only users with 'ADMIN' role
//                        .requestMatchers(HttpMethod.POST, "/api/rent-requests").hasRole("RENTER") // Only RENTERS can create rent requests
//                        .requestMatchers("/api/rent-requests/renter/**").hasRole("RENTER") // RENTERS can view their own requests
//                        .requestMatchers("/api/rent-requests/owner/**").hasRole("OWNER") // OWNERS can view/manage their property's requests
//
//                        // FIX: Ensure specific owner property management endpoints are correctly covered
//                        // This covers /api/properties/owner/list, /api/properties/owner/edit/:id, /api/properties/owner/:id etc.
//                        .requestMatchers("/api/properties/owner/**").hasRole("OWNER") // Changed to hasRole("OWNER")
//
//                        // Any other request must be authenticated
//                        .anyRequest().authenticated()
//                )
//                .exceptionHandling(exceptions -> exceptions
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                );
//
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//
//        http.authenticationProvider(authenticationProvider());
//
//        return http.build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//} jun 7 9:30

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true) // Enable @PreAuthorize annotations
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final UserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtRequestFilter jwtRequestFilter;
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder);
//        return authProvider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs
//                .exceptionHandling(exceptions -> exceptions
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) // Handle unauthorized access attempts
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless sessions for JWT
//                )
//                .authorizeHttpRequests(authorize -> authorize
//                        // --- Publicly Accessible Endpoints (no authentication required) ---
//                        .requestMatchers("/api/auth/register/**", "/api/auth/login", "/api/auth/forgot-password", "/api/auth/reset-password").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/properties/**").permitAll() // Allow public viewing of all properties (GET requests)
//                        .requestMatchers("/uploads/**").permitAll() // Allow access to uploaded files (images, etc.)
//
//                        // --- Role-Based Authorized Endpoints (authentication and specific role required) ---
//                        // Order matters: more specific rules usually go before more general ones.
//                        .requestMatchers("/api/admin/**").hasRole("ADMIN") // Only users with 'ADMIN' role
//
//                        // Renter-specific endpoints
//                        .requestMatchers(HttpMethod.POST, "/api/rent-requests").hasRole("RENTER") // Only RENTERS can create rent requests
//                        .requestMatchers("/api/rent-requests/renter/**").hasRole("RENTER") // RENTERS can view their own requests
//
//                        // Owner-specific endpoints (including listing properties)
//                        .requestMatchers("/api/properties/owner/**").hasRole("OWNER") // Owner properties management (list, create, update, delete, status changes)
//                        .requestMatchers("/api/rent-requests/owner/**").hasRole("OWNER") // OWNERS can view/manage their property's requests
//
//                        // Authenticated User Endpoints (any authenticated user)
//                        .requestMatchers("/api/users/me").authenticated() // Any authenticated user can access their own profile
//
//                        // --- All other requests require authentication ---
//                        .anyRequest().authenticated()
//                );
//
//        // Add the JWT filter before Spring Security's default UsernamePasswordAuthenticationFilter
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//
//        // Configure the authentication provider
//        http.authenticationProvider(authenticationProvider());
//
//        return http.build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        // Allow requests from your React frontend development server
//        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
//        // Allow all common HTTP methods
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        // Allow necessary headers, especially Authorization for JWT
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
//        // Allow credentials (like cookies or authorization headers)
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        // Apply this CORS configuration to all paths in your backend
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}
// comment for payment system

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true) // Enable @PreAuthorize annotations
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final UserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtRequestFilter jwtRequestFilter;
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder);
//        return authProvider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs
//                .exceptionHandling(exceptions -> exceptions
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) // Handle unauthorized access attempts
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless sessions for JWT
//                )
//                .authorizeHttpRequests(authorize -> authorize
//                        // --- Publicly Accessible Endpoints (no authentication required) ---
//                        .requestMatchers("/api/auth/register/**", "/api/auth/login", "/api/auth/forgot-password", "/api/auth/reset-password").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/properties/**").permitAll() // Allow public viewing of all properties (GET requests)
//                        .requestMatchers("/uploads/**").permitAll() // Allow access to uploaded files (images, etc.)
//
//                        // --- NEW: Permit access to payment endpoints for authenticated RENTERs/users ---
//                        // RENTERS can create a payment intent (POST request)
//                        .requestMatchers(HttpMethod.POST, "/api/payments/create-payment-intent").hasRole("RENTER")
//                        // Any authenticated user can confirm a payment (e.g., after Stripe redirect/webhook)
//                        .requestMatchers(HttpMethod.POST, "/api/payments/confirm-payment").authenticated()
//
//
//                        // --- Role-Based Authorized Endpoints (authentication and specific role required) ---
//                        // Order matters: more specific rules usually go before more general ones.
//                        .requestMatchers("/api/admin/**").hasRole("ADMIN") // Only users with 'ADMIN' role
//
//                        // Renter-specific endpoints
//                        .requestMatchers(HttpMethod.POST, "/api/rent-requests").hasRole("RENTER")
//                        .requestMatchers("/api/rent-requests/renter/**").hasRole("RENTER")
//
//                        // Owner-specific endpoints (including listing properties)
//                        .requestMatchers("/api/properties/owner/**").hasRole("OWNER")
//                        .requestMatchers("/api/rent-requests/owner/**").hasRole("OWNER")
//
//                        // Authenticated User Endpoints (any authenticated user)
//                        .requestMatchers("/api/users/me").authenticated() // Any authenticated user can access their own profile
//
//                        // --- All other requests require authentication ---
//                        .anyRequest().authenticated()
//                );
//
//        // Add the JWT filter before Spring Security's default UsernamePasswordAuthenticationFilter
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//
//        // Configure the authentication provider
//        http.authenticationProvider(authenticationProvider());
//
//        return http.build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        // Allow requests from your React frontend development server
//        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
//        // Allow all common HTTP methods
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        // Allow necessary headers, especially Authorization for JWT
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
//        // Allow credentials (like cookies or authorization headers)
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        // Apply this CORS configuration to all paths in your backend
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}
//jun 13 11:13pm
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true) // Enable @PreAuthorize annotations
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    // IMPORTANT: Ensure your UserDetailsServiceImp is correctly injected here.
//    // Spring will automatically find your @Service annotated UserDetailsServiceImp.
//    private final UserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtRequestFilter jwtRequestFilter;
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder);
//        return authProvider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .csrf(csrf -> csrf.disable())
//                .exceptionHandling(exceptions -> exceptions
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/api/auth/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/properties/**").permitAll()
//                        .requestMatchers("/uploads/**").permitAll()
//
//                        // Payment Endpoints
//                        .requestMatchers(HttpMethod.POST, "/api/payments/create-payment-intent").hasRole("RENTER")
//                        .requestMatchers(HttpMethod.POST, "/api/payments/confirm-payment").authenticated()
//
//                        // Role-Based Authorized Endpoints
//                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/api/rent-requests").hasRole("RENTER")
//                        .requestMatchers("/api/rent-requests/renter/**").hasRole("RENTER")
//                        .requestMatchers("/api/properties/owner/**").hasRole("OWNER")
//                        .requestMatchers("/api/rent-requests/owner/**").hasRole("OWNER")
//                        .requestMatchers("/api/users/me").authenticated()
//
//                        .anyRequest().authenticated()
//                );
//
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//
//        // This line is important to ensure your custom authenticationProvider is used
//        http.authenticationProvider(authenticationProvider());
//
//        return http.build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}
// 12:27 am

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final UserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtRequestFilter jwtRequestFilter;
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder);
//        return authProvider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .csrf(csrf -> csrf.disable())
//                .exceptionHandling(exceptions -> exceptions
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/api/auth/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/properties/**").permitAll()
//                        .requestMatchers("/uploads/**").permitAll()
//
//                        .requestMatchers(HttpMethod.POST, "/api/payments/create-payment-intent").hasRole("RENTER")
//                        .requestMatchers(HttpMethod.POST, "/api/payments/confirm-payment").authenticated()
//
//                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/api/rent-requests").hasRole("RENTER")
//                        .requestMatchers("/api/rent-requests/renter/**").hasRole("RENTER")
//                        .requestMatchers("/api/properties/owner/**").hasRole("OWNER")
//                        .requestMatchers("/api/rent-requests/owner/**").hasRole("OWNER")
//                        .requestMatchers("/api/users/me").authenticated()
//
//                        .anyRequest().authenticated()
//                );
//
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//        http.authenticationProvider(authenticationProvider());
//
//        return http.build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}
// jun 24 working code

import com.edelala.mur.filter.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity // Enables @PreAuthorize
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final JwtRequestFilter jwtRequestFilter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for stateless API
//                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS
//                .authorizeHttpRequests(authorize -> authorize
//                        // Public endpoints
//                        .requestMatchers("/api/auth/**").permitAll() // Authentication endpoints (login, register)
//                        .requestMatchers("/api/properties/**").permitAll() // Public property listings (GET only)
//                        .requestMatchers("/uploads/**").permitAll() // Allow access to uploaded images
//                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll() // Swagger UI
//                        // Authenticated endpoints
//                        .anyRequest().authenticated() // All other requests require authentication
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless sessions for JWT
//                )
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Allow your frontend origin
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
//        configuration.setAllowedHeaders(List.of("*")); // Allow all headers
//        configuration.setAllowCredentials(true); // Allow credentials (cookies, authorization headers)
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration); // Apply CORS to all paths
//        return source;
//    }
//}
//july 9 add to fix image issue and july 11 comment to fix ownerDashboard issue

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity // Enables @PreAuthorize
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final JwtRequestFilter jwtRequestFilter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .authorizeHttpRequests(authorize -> authorize
//                        // Specific AUTHENTICATED endpoints first (more specific rules before general ones)
//
//                        // Owner specific endpoints
//                        .requestMatchers("/api/properties/owner/**").hasRole("OWNER")
//                        .requestMatchers("/api/rent-requests/owner/**").hasRole("OWNER") // Explicitly for owner rent requests
//                        .requestMatchers("/api/users/owner/**").hasRole("OWNER")
//
//                        // Renter specific endpoints
//                        .requestMatchers("/api/rent-requests").hasRole("RENTER") // POST /api/rent-requests
//                        .requestMatchers("/api/rent-requests/renter/**").hasRole("RENTER")
//
//                        // Public endpoints (less specific, permitAll)
//                        .requestMatchers("/api/auth/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/properties").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/properties/{id}").permitAll()
//                        .requestMatchers("/uploads/**").permitAll()
//                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
//
//                        // All other requests require authentication
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
//        configuration.setAllowedHeaders(List.of("*"));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}
//july 12 comment for owner dashboard

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity // Enables @PreAuthorize
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final JwtRequestFilter jwtRequestFilter;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .authorizeHttpRequests(authorize -> authorize
//                        // Specific AUTHENTICATED endpoints first (more specific rules before general ones)
//
//                        // Owner specific endpoints
//                        // Allow all methods for /api/properties/owner/** (POST, PUT, DELETE, GET /me)
//                        .requestMatchers("/api/properties/owner/**").hasRole("OWNER")
//                        // Explicitly allow GET for owner's rent requests
//                        .requestMatchers(HttpMethod.GET, "/api/rent-requests/owner/my-properties").hasRole("OWNER")
//                        // Allow other methods for owner's rent requests (approve/reject)
//                        .requestMatchers("/api/rent-requests/owner/**").hasRole("OWNER")
//                        .requestMatchers("/api/users/owner/**").hasRole("OWNER")
//
//                        // Renter specific endpoints
//                        .requestMatchers("/api/rent-requests").hasRole("RENTER") // POST /api/rent-requests
//                        .requestMatchers("/api/rent-requests/renter/**").hasRole("RENTER") // GET /api/rent-requests/renter/my-requests etc.
//
//                        // Public endpoints (less specific, permitAll)
//                        .requestMatchers("/api/auth/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/properties").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/properties/{id}").permitAll()
//                        .requestMatchers("/uploads/**").permitAll()
//                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
//
//                        // All other requests require authentication
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
//        configuration.setAllowedHeaders(List.of("*"));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}
//comment july 13

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity // Enables @PreAuthorize
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final JwtRequestFilter jwtRequestFilter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .authorizeHttpRequests(authorize -> authorize
//                        // --- Specific AUTHENTICATED endpoints first (more specific rules before general ones) ---
//
//                        // Owner specific endpoints
//                        .requestMatchers("/api/properties/owner/**").hasRole("OWNER")
//                        .requestMatchers(HttpMethod.PUT, "/api/rent-requests/owner/{requestId}/approve").hasRole("OWNER")
//                        .requestMatchers(HttpMethod.PUT, "/api/rent-requests/owner/{requestId}/reject").hasRole("OWNER")
//                        .requestMatchers("/api/rent-requests/owner/**").hasRole("OWNER") // Catches GET /my-properties-requests and other owner rent-request paths
//                        .requestMatchers("/api/users/owner/**").hasRole("OWNER")
//
//                        // Renter specific endpoints
//                        .requestMatchers(HttpMethod.POST, "/api/rent-requests").hasRole("RENTER") // For creating a new rent request
//                        .requestMatchers(HttpMethod.GET, "/api/rent-requests/renter/my-requests").hasRole("RENTER") // Explicitly for renter's own requests
//                        .requestMatchers("/api/rent-requests/renter/**").hasRole("RENTER") // Catch-all for other renter rent-request paths (e.g., by ID if added later)
//                        // If a renter needs to view a specific rent request by ID (e.g., /api/rent-requests/{id}),
//                        // the @PreAuthorize in the controller method handles the fine-grained authorization.
//
//                        // Public endpoints (less specific, permitAll)
//                        .requestMatchers("/api/auth/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/properties").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/properties/{id}").permitAll()
//                        .requestMatchers("/uploads/**").permitAll()
//                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
//
//                        // All other requests require authentication
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
//        configuration.setAllowedHeaders(List.of("*"));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//} july 14

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity // Enables @PreAuthorize
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final JwtRequestFilter jwtRequestFilter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .authorizeHttpRequests(authorize -> authorize
//                        // --- MOST SPECIFIC AUTHENTICATED ENDPOINTS FIRST ---
//                        // Explicitly allow PUT for owner's rent request approval/rejection
//                        .requestMatchers(HttpMethod.PUT, "/api/rent-requests/owner/{requestId}/approve").hasRole("OWNER")
//                        .requestMatchers(HttpMethod.PUT, "/api/rent-requests/owner/{requestId}/reject").hasRole("OWNER")
//
//                        // Owner specific endpoints
//                        .requestMatchers("/api/properties/owner/**").hasRole("OWNER")
//                        .requestMatchers("/api/rent-requests/owner/**").hasRole("OWNER") // Catches GET /my-properties-requests and other owner rent-request paths
//                        .requestMatchers("/api/users/owner/**").hasRole("OWNER")
//
//                        // Renter specific endpoints
//                        .requestMatchers(HttpMethod.POST, "/api/rent-requests").hasRole("RENTER") // For creating a new rent request
//                        .requestMatchers(HttpMethod.GET, "/api/rent-requests/renter/my-requests").hasRole("RENTER") // Explicitly for renter's own requests
//                        .requestMatchers("/api/rent-requests/renter/**").hasRole("RENTER") // Catch-all for other renter rent-request paths
//
//                        // Public endpoints (less specific, permitAll)
//                        .requestMatchers("/api/auth/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/properties").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/properties/{id}").permitAll()
//                        .requestMatchers("/uploads/**").permitAll()
//                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
//
//                        // --- GENERAL AUTHENTICATED ENDPOINT (LAST) ---
//                        // All other requests require authentication
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
//        configuration.setAllowedHeaders(List.of("*"));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}
//

//added for test only
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Enables @PreAuthorize
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorize -> authorize
                        // --- MOST SPECIFIC AUTHENTICATED ENDPOINTS FIRST ---
                        // Allow OPTIONS pre-flight requests for all paths (Crucial for CORS)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Re-enable hasRole for approve endpoint (now using query param)
                        .requestMatchers(HttpMethod.PUT, "/api/rent-requests/owner/approve").hasRole("OWNER")

                        // Explicitly allow PUT for owner's rent request rejection (still uses path variable)
                        .requestMatchers(HttpMethod.PUT, "/api/rent-requests/owner/{requestId}/reject").hasRole("OWNER")

                        // Owner specific endpoints
                        .requestMatchers("/api/properties/owner/**").hasRole("OWNER")
                        .requestMatchers("/api/rent-requests/owner/**").hasRole("OWNER") // Catches GET /my-properties-requests and other owner rent-request paths
                        .requestMatchers("/api/users/owner/**").hasRole("OWNER")

                        // Renter specific endpoints
                        .requestMatchers(HttpMethod.POST, "/api/rent-requests").hasRole("RENTER") // For creating a new rent request
                        .requestMatchers(HttpMethod.GET, "/api/rent-requests/renter/my-requests").hasRole("RENTER") // Explicitly for renter's own requests
                        .requestMatchers("/api/rent-requests/renter/**").hasRole("RENTER") // Catch-all for other renter rent-request paths

                        // General authenticated user endpoints (e.g., /api/users/me)
                        .requestMatchers("/api/users/me").authenticated() // Any authenticated user can get their own details

                        // Public endpoints (less specific, permitAll)
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/properties").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/properties/{id}").permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/error").permitAll() // Keep permitting /error for now

                        // --- GENERAL AUTHENTICATED ENDPOINT (LAST) ---
                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
