package de.krayadev.plugins.DbConnector.repos;

import de.krayadev.domain.repositories.ProjectRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import de.krayadev.domain.entities.project.Project;

import java.util.UUID;

public interface SpringDataProjectRepository extends JpaRepository<Project, UUID>, ProjectRepository {}
