package Krayadev.DbConnector.repos;

import Krayadev.DbConnector.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectRepo extends JpaRepository<Project, UUID> {}
