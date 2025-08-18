package com.edelala.mur.service;

import com.edelala.mur.dto.MessageDTO;
import com.edelala.mur.entity.Message;
import com.edelala.mur.entity.RentRequest;

import com.edelala.mur.entity.User;
import com.edelala.mur.exception.ResourceNotFoundException;
import com.edelala.mur.repo.MessageRepository;
import com.edelala.mur.repo.RentRequestRepository;
import com.edelala.mur.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException; // <--- THIS IS THE IMPORTANT IMPORT
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final RentRequestRepository rentRequestRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService; // NEW: Inject NotificationService

    @Autowired
    public MessageService(MessageRepository messageRepository, RentRequestRepository rentRequestRepository, UserRepository userRepository, NotificationService notificationService) {
        this.messageRepository = messageRepository;
        this.rentRequestRepository = rentRequestRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService; // NEW: Initialize
    }

    @Transactional
    public MessageDTO sendMessage(Long rentRequestId, String content, UserDetails senderDetails) {
        User sender = userRepository.findByEmail(senderDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Sender user not found with email: " + senderDetails.getUsername()));

        RentRequest rentRequest = rentRequestRepository.findById(rentRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Rent request not found with ID: " + rentRequestId));

        // Determine the recipient of the message
        User recipient = null;
        if (rentRequest.getRenter().getId().equals(sender.getId())) {
            // Sender is renter, recipient is owner
            recipient = rentRequest.getProperty().getOwner();
        } else if (rentRequest.getProperty().getOwner().getId().equals(sender.getId())) {
            // Sender is owner, recipient is renter
            recipient = rentRequest.getRenter();
        } else {
            throw new AccessDeniedException("You are not authorized to send messages for this rent request.");
        }

        Message message = Message.builder()
                .rentRequest(rentRequest)
                .sender(sender)
                .content(content)
                .build();

        Message savedMessage = messageRepository.save(message);

        // NEW: Create a notification for the recipient
        notificationService.createNotification(
                recipient.getId(),
                sender.getId(),
                "NEW_MESSAGE",
                "New message for your rent request on property '" + rentRequest.getProperty().getTitle() + "' from " + sender.getFirstName(),
                rentRequest.getId(),
                "RENT_REQUEST"
        );

        return MessageDTO.fromEntity(savedMessage);
    }

    public List<MessageDTO> getMessagesByRentRequestId(Long rentRequestId, UserDetails currentUserDetails) {
        User currentUser = userRepository.findByEmail(currentUserDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found with email: " + currentUserDetails.getUsername()));

        RentRequest rentRequest = rentRequestRepository.findById(rentRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Rent request not found with ID: " + rentRequestId));

        if (!rentRequest.getRenter().getId().equals(currentUser.getId()) &&
                !rentRequest.getProperty().getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not authorized to view messages for this rent request.");
        }

        List<Message> messages = messageRepository.findByRentRequestIdOrderByTimestampAsc(rentRequestId);
        return messages.stream()
                .map(MessageDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
