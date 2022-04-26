package de.krayadev.plugins.DbConnector.repos;

import de.krayadev.domain.projectMembership.ProjectMembershipRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import de.krayadev.domain.projectMembership.ProjectMembership;
import de.krayadev.domain.projectMembership.ProjectMembershipId;

public interface SpringDataProjectMembershipRepository extends JpaRepository<ProjectMembership, ProjectMembershipId>, ProjectMembershipRepository {}
