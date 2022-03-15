package de.krayadev.taskitory.dbConnector.repos;

import de.krayadev.taskitory.dbConnector.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Integer> {}
