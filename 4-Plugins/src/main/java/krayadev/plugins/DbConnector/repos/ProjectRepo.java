package krayadev.plugins.DbConnector.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import krayadev.domain.project.Project;

import java.util.UUID;

public interface ProjectRepo extends JpaRepository<Project, UUID> {}
