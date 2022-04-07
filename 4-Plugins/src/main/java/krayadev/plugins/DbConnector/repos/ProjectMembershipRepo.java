package krayadev.plugins.DbConnector.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import krayadev.domain.projectMembership.ProjectMembership;
import krayadev.domain.projectMembership.ProjectMembershipId;

public interface ProjectMembershipRepo extends JpaRepository<ProjectMembership, ProjectMembershipId> {}
