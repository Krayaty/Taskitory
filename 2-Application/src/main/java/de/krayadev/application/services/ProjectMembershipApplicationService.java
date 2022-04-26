package de.krayadev.application.services;

import de.krayadev.domain.repositories.ProjectMembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectMembershipApplicationService {

    @Autowired
    private ProjectMembershipRepository projectMembershipRepository;

}
