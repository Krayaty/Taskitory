package de.krayadev.application.services;

import de.krayadev.application.UnauthorizedException;
import de.krayadev.application.dto.MoveTaskDTO;
import de.krayadev.domain.aggregates.projectAggregate.entities.kanbanBoard.KanbanBoard;
import de.krayadev.domain.aggregates.projectAggregate.entities.project.Project;
import de.krayadev.domain.aggregates.projectAggregate.entities.task.Task;
import de.krayadev.domain.aggregates.userAggregate.entities.user.User;
import de.krayadev.domain.repositories.ProjectRepository;
import de.krayadev.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static de.krayadev.domain.services.RessourceAccessService.hasAccess;

@Service
public class KanbanBoardApplicationService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public KanbanBoard moveTask(String actorUserId, MoveTaskDTO dto){
        User actor = userRepository.getUserById(actorUserId);
        Project project = projectRepository.findByName(dto.getProjectName());
        Task task = project.getTaskById(dto.getTaskId());
        KanbanBoard kanbanBoard = project.getKanbanBoardById(dto.getKanbanBoardId());
        if (!hasAccess(actor, project) || !hasAccess(actor, task) || !hasAccess(actor, kanbanBoard))
            throw new UnauthorizedException("Action not authorized");

        project.moveTaskOnKanbanBoard(task, kanbanBoard, dto.getNewStatus());
        if(dto.isMoveToBacklog())
            project.moveTaskToBacklog(task, kanbanBoard);

        projectRepository.save(project);

        return kanbanBoard;
    }

}
