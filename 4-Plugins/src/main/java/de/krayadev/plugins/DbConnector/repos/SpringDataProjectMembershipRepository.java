package de.krayadev.plugins.DbConnector.repos;

import de.krayadev.domain.repositories.ProjectMembershipRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import de.krayadev.domain.entities.projectMembership.ProjectMembership;
import de.krayadev.domain.entities.projectMembership.ProjectMembershipId;

public interface SpringDataProjectMembershipRepository extends JpaRepository<ProjectMembership, ProjectMembershipId>, ProjectMembershipRepository {}
