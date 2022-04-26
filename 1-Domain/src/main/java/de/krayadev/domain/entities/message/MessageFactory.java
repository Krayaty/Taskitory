package de.krayadev.domain.entities.message;

import lombok.SneakyThrows;
import de.krayadev.domain.entities.project.Project;
import de.krayadev.domain.entities.projectMembership.ProjectMembership;
import de.krayadev.domain.entities.user.User;
import de.krayadev.domain.valueobjects.ProjectSecurityKey;
import de.krayadev.domain.entities.projectMembership.ProjectRole;

public class MessageFactory {

    @SneakyThrows
    public static Message createInvitationToProjectForUser(Project project, User user, ProjectSecurityKey projectSecretKey) {
        String content = projectSecretKey.hashedKey();
        return new Message(content, MessageType.INVITATION, project, user);
    }

    public static Message createProjectMembershipRoleChangeMessage(ProjectMembership membership, ProjectRole oldRole) {
        ProjectRole newRole = membership.getRole();
        String content = "Rolle von " + oldRole.name + " zu " + newRole.name + " geändert.";
        return new Message(content, MessageType.INFORMATION, membership.getProject(), membership.getUser());
    }

    public static Message createProjectDeletedMessage(Project project, User user) {
        String content = "Projekt gelöscht";
        return new Message(content, MessageType.INFORMATION, project, user);
    }

}
