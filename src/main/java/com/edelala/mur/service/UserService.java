package com.edelala.mur.service;

import com.edelala.mur.repo.PasswordResetTokenRepository;
import org.springframework.transaction.annotation.Transactional;
import com.edelala.mur.entity.PasswordResetToken;
import com.edelala.mur.entity.Role;
import com.edelala.mur.entity.User;
import com.edelala.mur.repo.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordResetTokenRepository passwordResetTokenRepository; // Inject token repository

    private final @NonNull UserRepository userRepository;
    private final @NonNull PasswordEncoder passwordEncoder;


    public User registerUser(String firstName, String lastName,String username, String email, String password, Role role) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists"); // Or a custom exception
        }
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole(role);
        return userRepository.save(newUser);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateUserProfile(User user) {
        // Add logic to update user details (excluding password for now)
        return userRepository.save(user);
    }

    // Add methods for password reset, fetching user by ID, etc.
    @Transactional // Ensure atomicity of operations
    public String generatePasswordResetToken(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User with email " + userEmail + " not found."));

        // Invalidate any existing token for this user to ensure only one active token at a time
        passwordResetTokenRepository.findByUser(user).ifPresent(passwordResetTokenRepository::delete);

        // Generate and save a new token
        PasswordResetToken token = new PasswordResetToken(user);
        passwordResetTokenRepository.save(token);
        return token.getToken();
    }

    @Transactional // Ensure atomicity of operations
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired password reset token."));

        if (resetToken.isExpired()) {
            passwordResetTokenRepository.delete(resetToken); // Clean up expired token
            throw new RuntimeException("Password reset token has expired.");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword)); // Encode the new password
        userRepository.save(user); // Save the updated user

        passwordResetTokenRepository.delete(resetToken); // Invalidate the token after successful use
    }
}