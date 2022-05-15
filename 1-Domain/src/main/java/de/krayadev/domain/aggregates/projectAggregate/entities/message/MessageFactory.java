package de.krayadev.domain.aggregates.projectAggregate.entities.message;

import de.krayadev.domain.aggregates.userAggregate.entities.user.User;
import de.krayadev.domain.aggregates.projectAggregate.valueObjects.MessageContent;
import de.krayadev.domain.aggregates.projectAggregate.entities.project.Project;
import de.krayadev.domain.aggregates.projectAggregate.entities.projectMembership.ProjectMembership;
import de.krayadev.domain.aggregates.projectAggregate.entities.projectMembership.ProjectRole;
import org.json.JSONObject;

public class MessageFactory {

    public static Message createInvitationToProjectForUser(Project origin, User recipient, ProjectRole role) {
        JSONObject contentAsJSON = new JSONObject();
        contentAsJSON.put(MessageContentJSONKey.TITLE.getValue(), "Projekt-Einladung");
        contentAsJSON.put(MessageContentJSONKey.PROJECT_NAME.getValue(), origin.getName());
        contentAsJSON.put(MessageContentJSONKey.PROJECT_SEC_KEY.getValue(), origin.getKey().getValue());
        contentAsJSON.put(MessageContentJSONKey.ROLE.getValue(), role.name);

        MessageContent content = new MessageContent(contentAsJSON);
        return new Message(content, MessageType.INVITATION, origin, recipient);
    }

    public static Message createProjectMemberRoleChangeMessage(User recipient, ProjectMembership membership, ProjectRole newRole) {
        JSONObject contentAsJSON = new JSONObject();
        contentAsJSON.put(MessageContentJSONKey.TITLE.getValue(), "Rolle geändert");
        contentAsJSON.put(MessageContentJSONKey.PROJECT_NAME.getValue(), membership.getProject().getName());
        contentAsJSON.put(MessageContentJSONKey.USER_NAME.getValue(), membership.getUser().getUsername());
        contentAsJSON.put(MessageContentJSONKey.OLD_ROLE.getValue(), membership.getRole());
        contentAsJSON.put(MessageContentJSONKey.NEW_ROLE.getValue(), newRole);

        MessageContent content = new MessageContent(contentAsJSON);
        return new Message(content, MessageType.ROLE_CHANGE, membership.getProject(), recipient);
    }

    public static Message createProjectDeletedMessage(Project origin, User recipient) {
        JSONObject contentAsJSON = new JSONObject();
        contentAsJSON.put(MessageContentJSONKey.TITLE.getValue(), "Projekt gelöscht");
        contentAsJSON.put(MessageContentJSONKey.PROJECT_NAME.getValue(), origin.getName());

        MessageContent content = new MessageContent(contentAsJSON);
        return new Message(content, MessageType.PROJECT_DELETION, origin, recipient);
    }

    public static Message createKickedFromProjectMessage(Project origin, User recipient) {
        JSONObject contentAsJSON = new JSONObject();
        contentAsJSON.put(MessageContentJSONKey.TITLE.getValue(), "Aus Projekt entfernt");
        contentAsJSON.put(MessageContentJSONKey.PROJECT_NAME.getValue(), origin.getName());

        MessageContent content = new MessageContent(contentAsJSON);
        return new Message(content, MessageType.KICKED_FROM_PROJECT, origin, recipient);
    }

    public static Message createNewProjectMemberMessage(Project origin, User recipient, ProjectMembership membership) {
        JSONObject contentAsJSON = new JSONObject();
        contentAsJSON.put(MessageContentJSONKey.TITLE.getValue(), "Neues Projekt-Mitglied");
        contentAsJSON.put(MessageContentJSONKey.PROJECT_NAME.getValue(), origin.getName());
        contentAsJSON.put(MessageContentJSONKey.USER_NAME.getValue(), membership.getUser().getUsername());
        contentAsJSON.put(MessageContentJSONKey.ROLE.getValue(), membership.getRole().name);

        MessageContent content = new MessageContent(contentAsJSON);
        return new Message(content, MessageType.NEW_TEAM_MEMBER, origin, recipient);
    }

}
