package de.krayadev.application.services;

import de.krayadev.domain.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskApplicationService {

    @Autowired
    private TaskRepository taskRepository;

}
