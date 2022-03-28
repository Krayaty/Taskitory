package Krayadev.DbConnector.repos;

import Krayadev.DbConnector.idClasses.ProjectMembershipId;
import Krayadev.DbConnector.model.ProjectMembership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMembershipRepo extends JpaRepository<ProjectMembership, ProjectMembershipId> {}
