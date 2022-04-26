package de.krayadev.application.services;

import de.krayadev.domain.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectApplicationService {

    @Autowired
    private ProjectRepository projectRepository;

}
