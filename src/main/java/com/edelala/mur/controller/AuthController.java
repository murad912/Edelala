package com.edelala.mur.controller;


import com.edelala.mur.entity.Role;
import com.edelala.mur.entity.User;
import com.edelala.mur.service.EmailService;
import com.edelala.mur.service.UserService;
import com.edelala.mur.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value; // Import @Value

import java.util.Map;

//@RestController
//@RequestMapping("/api/auth")
//@RequiredArgsConstructor
//public class AuthController {
//
//    private final UserService userService;
//    private final AuthenticationManager authenticationManager; // <--- Inject AuthenticationManager
//
//
//    @PostMapping("/register/renter")
//    public ResponseEntity<User> registerRenter(@Valid @RequestBody Map<String, String> registrationRequest) {
//        User user = userService.registerUser(
//                registrationRequest.get("firstName"),
//                registrationRequest.get("lastName"),
//                registrationRequest.get("username"),
//                registrationRequest.get("email"),
//                registrationRequest.get("password"),
//                Role.RENTER
//        );
//        return new ResponseEntity<>(user, HttpStatus.CREATED);
//    }
//
//    @PostMapping("/register/owner")
//    public ResponseEntity<User> registerOwner(@Valid @RequestBody Map<String, String> registrationRequest) {
//        User user = userService.registerUser(
//                registrationRequest.get("firstName"),
//                registrationRequest.get("lastName"),
//                registrationRequest.get("username"),
//                registrationRequest.get("email"),
//                registrationRequest.get("password"),
//                Role.OWNER
//        );
//        return new ResponseEntity<>(user, HttpStatus.CREATED);
//    }
//    @PostMapping("/login") // <--- NEW LOGIN ENDPOINT
//    public ResponseEntity<String> authenticateUser(@RequestBody Map<String, String> loginRequest) {
//        // Create an authentication token with the provided credentials
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.get("email"), loginRequest.get("password"))
//        );
//
//        // Set the authenticated user in the security context
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // At this point, Spring Security has successfully authenticated the user
//        // and should have set the JSESSIONID cookie.
//        // You can return a simple success message or user details (e.g., username, role).
//        return ResponseEntity.ok("User logged in successfully!");
//    }
//
//
//
//
//    // Login will be handled by Spring Security's default login endpoint if configured
//    // You might add custom login response if needed
//
//    // Add endpoints for forgot password, reset password, etc.
//}





// DTO for login response (optional but good practice)
// This can be a separate class file, e.g., LoginResponse.java
class LoginResponse {
    private String jwt;
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    // Add other fields you want to send to frontend
    // Constructors, getters, setters (or use @Data from Lombok)
    public LoginResponse(String jwt, String email, String role, String firstName, String lastName) {
        this.jwt = jwt;
        this.email = email;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getJwt() { return jwt; }
    public void setJwt(String jwt) { this.jwt = jwt; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
}



// DTO for reset password request
class ResetPasswordRequest {
    private String token;
    private String newPassword;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}



@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil; // <--- Inject JwtUtil
    private final UserDetailsService userDetailsService; // <--- Inject UserDetailsService
    private final EmailService emailService; // <--- Inject EmailService

    // Base URL for the frontend application (needed to construct reset link)
    @Value("${frontend.base-url}") // Read from application.properties or application.yml
    private String frontendBaseUrl;

    @PostMapping("/register/renter")
    public ResponseEntity<User> registerRenter(@Valid @RequestBody Map<String, String> registrationRequest) {
        User user = userService.registerUser(
                registrationRequest.get("firstName"),
                registrationRequest.get("lastName"),
                registrationRequest.get("username"),
                registrationRequest.get("email"),
                registrationRequest.get("password"),
                com.edelala.mur.entity.Role.RENTER // Specify full path to Role enum
        );
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/register/owner")
    public ResponseEntity<User> registerOwner(@Valid @RequestBody Map<String, String> registrationRequest) {
        User user = userService.registerUser(
                registrationRequest.get("firstName"),
                registrationRequest.get("lastName"),
                registrationRequest.get("username"),
                registrationRequest.get("email"),
                registrationRequest.get("password"),
                com.edelala.mur.entity.Role.OWNER // Specify full path to Role enum
        );
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody Map<String, String> loginRequest) {
        // Authenticate the user with Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.get("email"), loginRequest.get("password"))
        );

        // Set the authentication in the SecurityContext (important for the current request context)
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Load UserDetails to generate JWT
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.get("email"));
        final String jwt = jwtUtil.generateToken(userDetails);

        // Fetch the full User entity to get firstName, lastName etc.
        User authenticatedUser = userService.findByEmail(loginRequest.get("email"))
                .orElseThrow(() -> new RuntimeException("User not found after authentication."));

        // Return the JWT along with user details
        return ResponseEntity.ok(new LoginResponse(
                jwt,
                authenticatedUser.getEmail(),
                authenticatedUser.getRole().name(),
                authenticatedUser.getFirstName(),
                authenticatedUser.getLastName()
        ));
    }

    //  --- Forgot Password Endpoints ---

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isEmpty()) {
            return new ResponseEntity<>("Email is required.", HttpStatus.BAD_REQUEST);
        }
        try {
            String token = userService.generatePasswordResetToken(email);
            // Construct the reset link for the frontend
            String resetLink = frontendBaseUrl + "/reset-password?token=" + token;
            emailService.sendPasswordResetEmail(email, resetLink);
            return ResponseEntity.ok("Password reset link sent to your email (check console for mock email).");
        } catch (RuntimeException e) {
            // For security, always return a generic success message to prevent email enumeration
            System.err.println("Forgot password error for " + email + ": " + e.getMessage());
            return ResponseEntity.ok("If an account with that email exists, a password reset link has been sent.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest resetRequest) {
        if (resetRequest.getToken() == null || resetRequest.getToken().isEmpty() ||
                resetRequest.getNewPassword() == null || resetRequest.getNewPassword().isEmpty()) {
            return new ResponseEntity<>("Token and new password are required.", HttpStatus.BAD_REQUEST);
        }
        try {
            userService.resetPassword(resetRequest.getToken(), resetRequest.getNewPassword());
            return ResponseEntity.ok("Password has been reset successfully!");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Specific error messages here
        }
    }
}
