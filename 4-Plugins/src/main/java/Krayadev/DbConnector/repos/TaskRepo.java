package Krayadev.DbConnector.repos;

import Krayadev.DbConnector.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepo extends JpaRepository<Task, UUID> {}
