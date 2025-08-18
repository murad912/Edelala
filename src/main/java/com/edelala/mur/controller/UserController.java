package com.edelala.mur.controller;

// backend/src/main/java/com/edelala/mur/controller/UserController.java (Create if it doesn't exist)

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.edelala.mur.entity.User;
import com.edelala.mur.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal; // Import Principal

//@RestController
//@RequestMapping("/api/users") // Base mapping for user-related endpoints
//@RequiredArgsConstructor
//public class UserController {
//
//    private final UserService userService;
//
//    // This endpoint returns the currently authenticated user's details
//    // It requires authentication as per your SecurityConfig
////    @GetMapping("/me")
////    public ResponseEntity<User> getCurrentUser(Principal principal) {
////        // 'principal' contains the authenticated user's name (which is email in your case)
////        User user = userService.findByEmail(principal.getName())
////                .orElseThrow(() -> new UsernameNotFoundException("Authenticated user not found."));
////        return ResponseEntity.ok(user);
////    }
//
//    @GetMapping("/me")
//    @PreAuthorize("isAuthenticated()") // Any authenticated user can access their own details
//    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
//        // userDetails.getUsername() should be the email in your setup
//        return userService.findByEmail(userDetails.getUsername())
//                .map(ResponseEntity::ok) // If user is found, return OK with user data
//                .orElse(ResponseEntity.notFound().build()); // If user is not found (shouldn't happen post-auth), return 404
//    }
//
//    // You can add other user-related endpoints here (e.g., update profile)
//} comment jul 17 to add edit profile

@RestController
@RequestMapping("/api/users") // Base mapping for user-related endpoints
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // This endpoint returns the currently authenticated user's details
    // It requires authentication as per your SecurityConfig
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()") // Any authenticated user can access their own details
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        // userDetails.getUsername() should be the email in your setup
        return userService.findByEmail(userDetails.getUsername())
                .map(ResponseEntity::ok) // If user is found, return OK with user data
                .orElse(ResponseEntity.notFound().build()); // If user is not found (shouldn't happen post-auth), return 404
    }

    /**
     * Endpoint for authenticated users to update their own profile details.
     * Only allows updating firstName, lastName, and email.
     *
     * @param userDetails The authenticated user's details from the security context.
     * @param updatedUser The User object containing the updated profile data from the request body.
     * @return ResponseEntity with the updated User object or an error status.
     */
    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()") // Any authenticated user can update their own profile
    public ResponseEntity<?> updateCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody User updatedUser) { // Assuming User model for request body
        try {
            // Find the current user from the database using their authenticated email
            User existingUser = userService.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Authenticated user not found in database."));

            // IMPORTANT: Only allow specific fields to be updated to prevent privilege escalation
            // Ensure ID, username, password, and role are NOT directly updated via this endpoint
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            // If email is being updated, you might need re-verification logic in a real app
            // For now, we'll allow it directly.
            existingUser.setEmail(updatedUser.getEmail());

            // Use the existing updateUserProfile method from UserService
            User savedUser = userService.updateUserProfile(existingUser);
            return ResponseEntity.ok(savedUser);
        } catch (RuntimeException e) {
            System.err.println("Error updating user profile: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while updating user profile: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
