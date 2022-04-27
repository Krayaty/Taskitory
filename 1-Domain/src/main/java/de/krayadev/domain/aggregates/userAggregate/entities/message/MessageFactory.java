package de.krayadev.domain.aggregates.userAggregate.entities.message;

import de.krayadev.domain.aggregates.userAggregate.entities.user.User;
import de.krayadev.domain.aggregates.userAggregate.valueObjects.MessageContent;
import lombok.SneakyThrows;
import de.krayadev.domain.aggregates.projectAggregate.entities.project.Project;
import de.krayadev.domain.aggregates.projectAggregate.entities.projectMembership.ProjectMembership;
import de.krayadev.domain.aggregates.projectAggregate.entities.projectMembership.ProjectRole;
import org.json.JSONObject;

import java.util.HashMap;

public class MessageFactory {

    @SneakyThrows
    public static Message createInvitationToProjectForUser(Project project, User user, ProjectRole role) {
        JSONObject contentAsJSON = new JSONObject();
        contentAsJSON.put("Titel", "Projekt-Einladung");
        contentAsJSON.put("Projekt-Schlüssel", project.getKey().getValue());
        contentAsJSON.put("Rolle", role.name);

        MessageContent content = new MessageContent(contentAsJSON);
        return new Message(content, MessageType.INVITATION, project, user);
    }

    public static Message createProjectMembershipRoleChangeMessage(ProjectMembership membership, ProjectRole newRole) {
        JSONObject contentAsJSON = new JSONObject();
        contentAsJSON.put("Titel", "Rolle geändert");
        contentAsJSON.put("Projekt-Name", membership.getProject().getName());
        contentAsJSON.put("Alte Rolle", membership.getRole());
        contentAsJSON.put("Neue Rolle", newRole);

        MessageContent content = new MessageContent(contentAsJSON);
        return new Message(content, MessageType.ROLE_CHANGE, membership.getProject(), membership.getUser());
    }

    public static Message createProjectDeletedMessage(Project project, User user) {
        JSONObject contentAsJSON = new JSONObject();
        contentAsJSON.put("Titel", "Projekt gelöscht");
        contentAsJSON.put("Projekt-Name", project.getName());

        MessageContent content = new MessageContent(contentAsJSON);
        return new Message(content, MessageType.PROJECT_DELETION, project, user);
    }

    public static Message createKickedFromProjectMessage(Project project, User user) {
        JSONObject contentAsJSON = new JSONObject();
        contentAsJSON.put("Titel", "Aus Projekt entfernt");
        contentAsJSON.put("Projekt-Name", project.getName());

        MessageContent content = new MessageContent(contentAsJSON);
        return new Message(content, MessageType.KICKED_FROM_PROJECT, project, user);
    }

}
