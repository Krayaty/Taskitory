package de.krayadev.domain.services;

import de.krayadev.domain.aggregates.projectAggregate.entities.kanbanBoard.KanbanBoard;
import de.krayadev.domain.aggregates.projectAggregate.entities.project.Project;
import de.krayadev.domain.aggregates.projectAggregate.entities.projectMembership.ProjectMembership;
import de.krayadev.domain.aggregates.projectAggregate.entities.task.Task;
import de.krayadev.domain.aggregates.userAggregate.entities.user.User;
import lombok.NonNull;

public class RessourceAccessService {

    public static boolean hasAccess(@NonNull User user, @NonNull Project project) {
        ProjectMembership projectMembership = user.getMemberships().stream().filter(membership -> membership.in(project))
                .findFirst().orElse(null);

        return projectMembership != null;
    }

    public static boolean hasAccess(@NonNull User user, @NonNull Task task) {
        return hasAccess(user, task.getProject());
    }

    public static boolean hasAccess(@NonNull User user, @NonNull KanbanBoard kanbanBoard) {
        return hasAccess(user, kanbanBoard.getProject());
    }

}
