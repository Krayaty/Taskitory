package de.krayadev.taskitory.dbConnector.repos;

import de.krayadev.taskitory.dbConnector.idClasses.ProjectMembershipId;
import de.krayadev.taskitory.dbConnector.model.ProjectMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectMembershipRepo extends JpaRepository<ProjectMembership, ProjectMembershipId> {}
