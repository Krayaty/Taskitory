package de.krayadev.application.services;

import de.krayadev.application.UnauthorizedException;
import de.krayadev.application.dto.ChangeProjectMembershipDTO;
import de.krayadev.application.dto.CreateKanbanBoardDTO;
import de.krayadev.application.dto.CreateProjectDTO;
import de.krayadev.application.dto.InvitationDTO;
import de.krayadev.domain.aggregates.projectAggregate.entities.kanbanBoard.CreateKanbanBoard;
import de.krayadev.domain.aggregates.projectAggregate.entities.kanbanBoard.KanbanBoard;
import de.krayadev.domain.aggregates.projectAggregate.entities.project.Project;
import de.krayadev.domain.aggregates.projectAggregate.entities.projectMembership.ProjectRole;
import de.krayadev.domain.aggregates.userAggregate.entities.user.User;
import de.krayadev.domain.repositories.ProjectRepository;
import de.krayadev.domain.repositories.UserRepository;
import de.krayadev.domain.valueObjects.Description;
import de.krayadev.domain.valueObjects.Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static de.krayadev.domain.services.RessourceAccessService.hasAccess;

@Service
public class ProjectApplicationService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public Project createProject(String actorUserId, CreateProjectDTO createProjectDTO) {
        User actor = userRepository.getUserById(actorUserId);
        Name name = new Name(createProjectDTO.getName());
        Description description = new Description(createProjectDTO.getDescription());
        Project project = new Project(name, description, actor);

        return projectRepository.save(project);
    }

    public Project updateProjectDescription(String actorUserId, String projectName, String newProjectDescription) {
        User actor = userRepository.getUserById(actorUserId);
        Project project = projectRepository.findByName(projectName);
        if(!hasAccess(actor, project) || !project.isAdmin(actor))
            throw new UnauthorizedException("Action not authorized");

        Description description = new Description(newProjectDescription);
        project.changeDescription(description);
        return projectRepository.save(project);
    }

    public Project updateProjectSecurityKey(String actorUserId, String projectName) {
        User actor = userRepository.getUserById(actorUserId);
        Project project = projectRepository.findByName(projectName);
        if(!hasAccess(actor, project) || !project.isAdmin(actor))
            throw new UnauthorizedException("Action not authorized");

        project.changeSecurityKey();
        return projectRepository.save(project);
    }

    public boolean deleteProject(String actorUserId, String projectName) {
        User actor = userRepository.getUserById(actorUserId);
        Project project = projectRepository.findByName(projectName);
        if(!hasAccess(actor, project) || !project.isAdmin(actor))
            throw new UnauthorizedException("Action not authorized");

        projectRepository.delete(project);
        return true;
    }

    public Project inviteUser(String actorUserId, InvitationDTO dto) {
        User actor = userRepository.getUserById(actorUserId);
        Project project = projectRepository.findByName(dto.getProjectName());
        if(!hasAccess(actor, project) || !project.isAdmin(actor))
            throw new UnauthorizedException("Action not authorized");

        User newTeamMember = userRepository.getUserById(dto.getNewTeamMemberId());
        ProjectRole role = dto.getRole();

        project.invite(newTeamMember, role);

        return projectRepository.save(project);
    }

    public Project promoteUser(String actorUserId, ChangeProjectMembershipDTO dto) {
        User actor = userRepository.getUserById(actorUserId);
        Project project = projectRepository.findByName(dto.getProjectName());
        if (!hasAccess(actor, project) || !project.isAdmin(actor))
            throw new UnauthorizedException("Action not authorized");

        User user = userRepository.getUserById(dto.getTeamMemberId());

        project.promote(user);

        return projectRepository.save(project);
    }

    public Project kickUser(String actorUserId, ChangeProjectMembershipDTO dto) {
        User actor = userRepository.getUserById(actorUserId);
        Project project = projectRepository.findByName(dto.getProjectName());
        if (hasAccess(actor, project) && !project.isAdmin(actor))
            throw new UnauthorizedException("Action not authorized");

        User user = userRepository.getUserById(dto.getTeamMemberId());

        project.kick(user);

        return projectRepository.save(project);
    }

    public KanbanBoard createKanbanBoard(String actorUserId, CreateKanbanBoardDTO dto) {
        User actor = userRepository.getUserById(actorUserId);
        Project project = projectRepository.findByName(dto.getProjectName());
        if (hasAccess(actor, project) && !project.isAdmin(actor))
            throw new UnauthorizedException("Action not authorized");

        KanbanBoard kanbanBoard = CreateKanbanBoard.named(dto.getName())
                .forProjectAndSprint(project, dto.getSprint())
                .withDescription(dto.getDescription())
                .withShowReviewColumn(dto.isShowReviewColumn())
                .withShowTestingColumn(dto.isShowTestingColumn())
                .build();

        project.create(kanbanBoard);
        projectRepository.save(project);

        return kanbanBoard;
    }

}
