package de.krayadev.domain.aggregates.userAggregate.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.krayadev.domain.aggregates.projectAggregate.entities.message.MessageContentJSONKey;
import de.krayadev.domain.aggregates.projectAggregate.entities.message.MessageType;
import de.krayadev.domain.aggregates.projectAggregate.entities.project.Project;
import de.krayadev.domain.aggregates.projectAggregate.entities.task.Task;
import de.krayadev.domain.aggregates.projectAggregate.entities.message.Message;
import de.krayadev.domain.aggregates.projectAggregate.entities.projectMembership.ProjectMembership;
import de.krayadev.domain.aggregates.userAggregate.entities.tag.Tag;
import de.krayadev.domain.aggregates.userAggregate.valueObjects.IrrelevantUserData;
import lombok.*;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static de.krayadev.domain.services.RessourceAccessService.hasAccess;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","username"})
@EqualsAndHashCode(of = {"username"})
@Getter
@Entity
@Table(name = "user_entity", schema = "iam")
public class User {

    @Id
    @Column(length = 36, nullable = false, updatable = false)
    @NonNull
    private String id;

    @Column(length = 255, nullable = false)
    @NonNull
    private String username;

    @Embedded
    @Getter(AccessLevel.NONE)
    private IrrelevantUserData irrelevantUserData;

    @OneToMany(mappedBy="responsibleUser", cascade = CascadeType.MERGE)
    @JsonIgnore
    private Set<Task> assignedTasks = new HashSet<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.MERGE)
    @JsonIgnore
    private Set<Task> createdTasks = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<ProjectMembership> memberships = new HashSet<>();

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.MERGE)
    @JsonIgnore
    private Set<Message> receivedMessages = new HashSet<>();

    @OneToMany(mappedBy="creator", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Tag> createdTags = new HashSet<>();

    public void takeResponsibilityFor(@NonNull Task task){
        if(!hasAccess(this, task))
            throw new IllegalStateException("User can not access task because he is not a member of the project");

        if(this.assignedTasks.contains(task))
            throw new IllegalStateException("User is already responsible for given task");

        this.assignedTasks.add(task);
    }

    public void giveUpResponsibilityFor(@NonNull Task task){
        if(!hasAccess(this, task))
            throw new IllegalStateException("User can not access task because he is not a member of the project");

        if(!this.assignedTasks.contains(task))
            throw new IllegalStateException("User is not responsible for this task");

        this.assignedTasks.remove(task);
    }

    public void acceptInvitation(@NonNull Message invitation){
        if(!invitation.hasType(MessageType.INVITATION))
            throw new IllegalArgumentException("Message is not an invitation");

        Project origin = invitation.getOrigin();
        JSONObject content = invitation.getContentAsJson();
        String projectSecurityKey = String.valueOf(content.get(MessageContentJSONKey.PROJECT_SEC_KEY.getValue()));
        origin.add(this, projectSecurityKey);
    }

    public void leave(@NonNull Project project){
        if(!hasAccess(this, project))
            throw new IllegalStateException("User is not a member of the given project");

        List<ProjectMembership> projectMemberships = this.memberships.stream().filter(membership -> membership.getProject().equals(project)).toList();
        this.memberships.removeAll(projectMemberships);
    }

    public void create(@NonNull Tag tag){
        if(!this.createdTags.contains(tag))
            throw new IllegalStateException("User has to be assigned as creator for given tag");

        this.createdTags.add(tag);
    }

    public void assignTagToTask(@NonNull Tag tag, @NonNull Task task){
        if(!hasAccess(this, task))
            throw new IllegalStateException("User can not access task because he is not a member of the project");

        tag.assignTo(task);
    }

    public void unassignTagFromTask(@NonNull Tag tag, @NonNull Task task){
        if(!hasAccess(this, task))
            throw new IllegalStateException("User can not access task because he is not a member of the project");

        tag.unassignFrom(task);
    }

    public void delete(@NonNull Tag tag){
        if(!this.createdTags.contains(tag))
            throw new IllegalStateException("Tags can only be deleted by their creator");

        this.createdTags.remove(tag);
    }

    public List<Message> retrieveAllMessages(){
        List<Message> allMessages = new ArrayList<>();

        for(Message message : this.receivedMessages){
            message.markAsRead();
            allMessages.add(message);
        }

        return allMessages;
    }

    public List<Message> retrieveUnreadMessages(){
        List<Message> unreadMessages =  this.receivedMessages.stream().filter(message -> !message.isRead()).toList();

        for(Message message : unreadMessages)
            message.markAsRead();

        return unreadMessages;
    }

    public List<Message> retrieveMessagesOfType(@NonNull MessageType type){
        List<Message> messagesOfType = this.receivedMessages.stream().filter(message -> message.hasType(type)).toList();

        for(Message message : messagesOfType)
            message.markAsRead();

        return messagesOfType;
    }

    public void deleteMessage(@NonNull Message message){
        if(!this.receivedMessages.contains(message))
            throw new IllegalStateException("User is not the recipient of this message");

        this.receivedMessages.remove(message);
    }

}
