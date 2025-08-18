package com.edelala.mur.repo;

import com.edelala.mur.entity.Notification;
import com.edelala.mur.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Find all notifications for a specific recipient, ordered by timestamp descending
    List<Notification> findByRecipientOrderByTimestampDesc(User recipient);

    // Find unread notifications for a specific recipient, ordered by timestamp descending
    List<Notification> findByRecipientAndIsReadFalseOrderByTimestampDesc(User recipient);

    // Count unread notifications for a specific recipient
    long countByRecipientAndIsReadFalse(User recipient);
}
