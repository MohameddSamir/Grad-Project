package com.favtour.travel.contact.contactMessage;

import com.favtour.travel.core.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact-messages")
@RequiredArgsConstructor
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ContactMessage>>> getContactMessages() {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "All messages are ready", contactMessageService.getContactMessages()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ContactMessage>> getContactMessageById(@PathVariable int id) {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Contact message found", contactMessageService.getContactMessageById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ContactMessage>> addContactMessage(@RequestBody ContactMessage contactMessage) {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Message saved successfully",
                        contactMessageService.createContactMessage(contactMessage)));
    }
}
