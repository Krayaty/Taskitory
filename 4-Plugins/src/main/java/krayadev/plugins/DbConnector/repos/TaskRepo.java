package krayadev.plugins.DbConnector.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import krayadev.domain.task.Task;

import java.util.UUID;

public interface TaskRepo extends JpaRepository<Task, UUID> {}
