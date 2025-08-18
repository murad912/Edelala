package com.edelala.mur.controller;

import com.edelala.mur.dto.NotificationDTO;
import com.edelala.mur.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()") // Only authenticated users can get their notifications
    public ResponseEntity<List<NotificationDTO>> getMyNotifications(@AuthenticationPrincipal UserDetails currentUser) {
        List<NotificationDTO> notifications = notificationService.getMyNotifications(currentUser);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/me/unread-count")
    @PreAuthorize("isAuthenticated()") // Only authenticated users can get their unread count
    public ResponseEntity<Long> getUnreadNotificationCount(@AuthenticationPrincipal UserDetails currentUser) {
        long count = notificationService.getUnreadNotificationCount(currentUser);
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{notificationId}/read")
    @PreAuthorize("isAuthenticated()") // Only authenticated users can mark their notifications as read
    public ResponseEntity<NotificationDTO> markNotificationAsRead(@PathVariable Long notificationId, @AuthenticationPrincipal UserDetails currentUser) {
        NotificationDTO updatedNotification = notificationService.markNotificationAsRead(notificationId, currentUser);
        return ResponseEntity.ok(updatedNotification);
    }

    @PutMapping("/mark-all-read")
    @PreAuthorize("isAuthenticated()") // Only authenticated users can mark all their notifications as read
    public ResponseEntity<Void> markAllNotificationsAsRead(@AuthenticationPrincipal UserDetails currentUser) {
        notificationService.markAllNotificationsAsRead(currentUser);
        return ResponseEntity.noContent().build();
    }
}
