package de.krayadev.plugins.DbConnector.repos;

import de.krayadev.domain.task.TaskRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import de.krayadev.domain.task.Task;

import java.util.UUID;

public interface SpringDataTaskRepository extends JpaRepository<Task, UUID>, TaskRepository {}
