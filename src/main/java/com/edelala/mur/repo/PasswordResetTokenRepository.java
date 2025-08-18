package com.edelala.mur.repo;

import com.edelala.mur.entity.PasswordResetToken;
import com.edelala.mur.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    // Find a token by its string value
    Optional<PasswordResetToken> findByToken(String token);

    // Find a token by the associated user
    Optional<PasswordResetToken> findByUser(User user);

    // Delete tokens older than a certain date (for cleanup)
    // You might implement a scheduled task for this later
    void deleteByExpiryDateBefore(LocalDateTime now);
}
