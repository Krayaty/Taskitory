package krayadev.domain.message;

import lombok.SneakyThrows;
import krayadev.domain.project.Project;
import krayadev.domain.projectMembership.ProjectMembership;
import krayadev.domain.user.User;
import krayadev.domain.project.ProjectSecurityKey;
import krayadev.domain.projectMembership.ProjectRole;

public class MessageFactory {

    @SneakyThrows
    public static Message createInvitationToProjectForUser(Project project, User user, ProjectSecurityKey projectSecretKey) {
        String content = projectSecretKey.getHashedKey();
        return new Message(project, user, content, MessageType.INVITATION);
    }

    public static Message createProjectMembershipRoleChangeMessage(ProjectMembership membership, ProjectRole oldRole) {
        ProjectRole newRole = membership.getRole();
        String content = "Rolle von " + oldRole.name + " zu " + newRole.name + " geändert.";
        return new Message(membership.getProject(), membership.getUser(), content, MessageType.INFORMATION);
    }

    public static Message createProjectDeletedMessage(Project project, User user) {
        String content = "Projekt gelöscht";
        return new Message(project, user, content, MessageType.INFORMATION);
    }

}
