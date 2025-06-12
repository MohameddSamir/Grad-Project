package com.favtour.travel.contact.contactMessage;

import com.favtour.travel.shared.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;

    public List<ContactMessage> getContactMessages() {
        return contactMessageRepository.findAll();
    }

    public ContactMessage getContactMessageById(int id) {
        return contactMessageRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Contact message not found"));
    }

    public ContactMessage createContactMessage(ContactMessage contactMessage) {
        return contactMessageRepository.save(contactMessage);
    }

}
