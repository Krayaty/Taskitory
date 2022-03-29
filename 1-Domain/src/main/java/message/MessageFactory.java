package message;

import lombok.SneakyThrows;
import project.Project;
import projectMembership.ProjectMembership;
import user.User;
import project.ProjectSecurityKey;
import projectMembership.Role;

public class MessageFactory {

    @SneakyThrows
    public static Message createInvitationToProjectForUser(Project project, User user, ProjectSecurityKey projectSecretKey) {
        String content = projectSecretKey.getHashedKey();
        return new Message(project, user, content, MessageType.INVITATION);
    }

    public static Message createProjectMembershipRoleChangeMessage(ProjectMembership membership, Role oldRole) {
        Role newRole = membership.getRole();
        String content = "Rolle von " + oldRole.name + " zu " + newRole.name + " geändert.";
        return new Message(membership.getProject(), membership.getUser(), content, MessageType.ROLE_CHANGE);
    }

    public static Message createProjectDeletedMessage(Project project, User user) {
        String content = "Projekt gelöscht";
        return new Message(project, user, content, MessageType.PROJECT_DELETED);
    }

}
