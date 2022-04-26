package de.krayadev.application.services;

import de.krayadev.domain.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageApplicationService {

    @Autowired
    private MessageRepository messageRepository;

}
