package com.edelala.mur.service;

import com.edelala.mur.dto.NotificationDTO;
import com.edelala.mur.entity.Notification;
import com.edelala.mur.entity.User;
import com.edelala.mur.exception.ResourceNotFoundException;
import com.edelala.mur.repo.NotificationRepository;
import com.edelala.mur.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public NotificationDTO createNotification(Long recipientId, Long senderId, String type, String message, Long relatedEntityId, String relatedEntityType) {
        User recipient = userRepository.findById(recipientId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipient user not found with ID: " + recipientId));

        User sender = null;
        if (senderId != null) {
            sender = userRepository.findById(senderId)
                    .orElseThrow(() -> new ResourceNotFoundException("Sender user not found with ID: " + senderId));
        }

        Notification notification = Notification.builder()
                .recipient(recipient)
                .sender(sender)
                .type(type)
                .message(message)
                .isRead(false) // New notifications are unread by default
                .relatedEntityId(relatedEntityId)
                .relatedEntityType(relatedEntityType)
                .build();

        Notification savedNotification = notificationRepository.save(notification);
        return NotificationDTO.fromEntity(savedNotification);
    }

    @Transactional(readOnly = true)
    public List<NotificationDTO> getMyNotifications(UserDetails currentUserDetails) {
        User currentUser = userRepository.findByEmail(currentUserDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found with email: " + currentUserDetails.getUsername()));

        List<Notification> notifications = notificationRepository.findByRecipientOrderByTimestampDesc(currentUser);
        return notifications.stream()
                .map(NotificationDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public long getUnreadNotificationCount(UserDetails currentUserDetails) {
        User currentUser = userRepository.findByEmail(currentUserDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found with email: " + currentUserDetails.getUsername()));

        return notificationRepository.countByRecipientAndIsReadFalse(currentUser);
    }

    @Transactional
    public NotificationDTO markNotificationAsRead(Long notificationId, UserDetails currentUserDetails) {
        User currentUser = userRepository.findByEmail(currentUserDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found with email: " + currentUserDetails.getUsername()));

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with ID: " + notificationId));

        // Ensure the current user is the recipient of this notification
        if (!notification.getRecipient().getId().equals(currentUser.getId())) {
            throw new org.springframework.security.access.AccessDeniedException("You are not authorized to mark this notification as read.");
        }

        notification.setRead(true);
        Notification updatedNotification = notificationRepository.save(notification);
        return NotificationDTO.fromEntity(updatedNotification);
    }

    @Transactional
    public void markAllNotificationsAsRead(UserDetails currentUserDetails) {
        User currentUser = userRepository.findByEmail(currentUserDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found with email: " + currentUserDetails.getUsername()));

        List<Notification> unreadNotifications = notificationRepository.findByRecipientAndIsReadFalseOrderByTimestampDesc(currentUser);
        unreadNotifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(unreadNotifications);
    }
}
