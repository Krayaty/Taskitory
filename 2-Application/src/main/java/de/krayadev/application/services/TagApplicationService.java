package de.krayadev.application.services;

import de.krayadev.domain.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagApplicationService {

    @Autowired
    private TagRepository tagRepository;

}
