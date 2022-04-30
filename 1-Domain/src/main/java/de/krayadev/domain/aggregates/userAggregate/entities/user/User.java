package de.krayadev.domain.aggregates.userAggregate.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.krayadev.domain.aggregates.projectAggregate.entities.message.MessageContentJSONKey;
import de.krayadev.domain.aggregates.projectAggregate.entities.message.MessageType;
import de.krayadev.domain.aggregates.projectAggregate.entities.project.Project;
import de.krayadev.domain.aggregates.taskAggregate.entities.task.Task;
import de.krayadev.domain.aggregates.projectAggregate.entities.message.Message;
import de.krayadev.domain.aggregates.projectAggregate.entities.projectMembership.ProjectMembership;
import de.krayadev.domain.aggregates.userAggregate.entities.tag.Tag;
import de.krayadev.domain.aggregates.userAggregate.valueObjects.IrrelevantUserData;
import lombok.*;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"username"})
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

    public boolean isProjectMember(@NonNull Project project){
        ProjectMembership projectMembership = this.memberships.stream().filter(membership -> membership.getProject().equals(project)).findFirst().orElse(null);
        return projectMembership != null;
    }

    private boolean hasAccessTo(@NonNull Task task){
        return this.isProjectMember(task.getProject());
    }

    private void removeMembership(@NonNull ProjectMembership membership){
        memberships.remove(membership);
    }

    public void takeResponsibilityFor(@NonNull Task task){
        if(!this.hasAccessTo(task))
            throw new IllegalStateException("User can not access task because he is not a member of the project");

        this.assignedTasks.add(task);
    }

    public void giveUpResponsibilityFor(@NonNull Task task){
        if(!this.hasAccessTo(task))
            throw new IllegalStateException("User can not access task because he is not a member of the project");

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
        memberships.stream().filter(membership -> membership.getProject().equals(project)).findFirst().ifPresentOrElse(
                this::removeMembership, () -> {
                    throw new IllegalStateException("User is not a member of the project");
                });
    }

    public void create(@NonNull Tag tag){
        if(!tag.isCreator(this))
            throw new IllegalStateException("User has to be assigned as creator for given tag");

        this.createdTags.add(tag);
    }

    public void assignTagToTask(@NonNull Tag tag, @NonNull Task task){
        if(!this.hasAccessTo(task))
            throw new IllegalStateException("User can not access task because he is not a member of the project");

        task.assign(tag);
    }

    public void unassignTagFromTask(@NonNull Tag tag, @NonNull Task task){
        if(!this.hasAccessTo(task))
            throw new IllegalStateException("User can not access task because he is not a member of the project");

        task.unassign(tag);
    }

    public void delete(@NonNull Tag tag){
        if(!tag.isCreator(this))
            throw new IllegalStateException("Tags can only be deleted by their creator");

        this.createdTags.remove(tag);
    }

    public void receiveMessage(@NonNull Message message){
        if(!message.isFor(this))
            throw new IllegalArgumentException("Message is not for this user");

        receivedMessages.add(message);
    }

    public List<Message> retrieveAllMessages(){
        return this.receivedMessages.stream().toList();
    }

    public List<Message> retrieveUnreadMessages(){
        return this.receivedMessages.stream().filter(message -> !message.isRead()).toList();
    }

    public List<Message> retrieveMessagesOfType(@NonNull MessageType type){
        return this.receivedMessages.stream().filter(message -> message.hasType(type)).toList();
    }

    public void deleteMessage(@NonNull Message message){
        receivedMessages.remove(message);
    }

    private Message readMessage(@NonNull Message message){
        if(!message.isFor(this))
            throw new IllegalArgumentException("Message is not for this user");

        message.markAsRead();
        return message;
    }

}
