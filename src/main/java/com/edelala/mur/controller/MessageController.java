package com.edelala.mur.controller;

import com.edelala.mur.dto.MessageDTO;
import com.edelala.mur.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('RENTER', 'OWNER')") // Only authenticated renters or owners can send messages
    public ResponseEntity<MessageDTO> sendMessage(@RequestParam Long rentRequestId, @RequestBody String content, @AuthenticationPrincipal UserDetails currentUser) {
        MessageDTO sentMessage = messageService.sendMessage(rentRequestId, content, currentUser);
        return new ResponseEntity<>(sentMessage, HttpStatus.CREATED);
    }

    @GetMapping("/rent-request/{rentRequestId}")
    @PreAuthorize("hasAnyRole('RENTER', 'OWNER')") // Only authenticated renters or owners can view messages
    public ResponseEntity<List<MessageDTO>> getMessagesForRentRequest(@PathVariable Long rentRequestId, @AuthenticationPrincipal UserDetails currentUser) {
        List<MessageDTO> messages = messageService.getMessagesByRentRequestId(rentRequestId, currentUser);
        return ResponseEntity.ok(messages);
    }
}
