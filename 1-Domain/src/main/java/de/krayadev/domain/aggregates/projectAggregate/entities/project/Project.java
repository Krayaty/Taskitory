package de.krayadev.domain.aggregates.projectAggregate.entities.project;

import de.krayadev.domain.aggregates.projectAggregate.entities.kanbanBoard.KanbanBoard;
import de.krayadev.domain.aggregates.projectAggregate.entities.projectMembership.ProjectRole;
import de.krayadev.domain.aggregates.taskAggregate.entities.task.Task;
import de.krayadev.domain.aggregates.projectAggregate.entities.message.Message;
import de.krayadev.domain.aggregates.projectAggregate.entities.message.MessageFactory;
import de.krayadev.domain.aggregates.projectAggregate.entities.message.MessageType;
import de.krayadev.domain.aggregates.projectAggregate.entities.projectMembership.ProjectMembership;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.krayadev.domain.aggregates.projectAggregate.valueObjects.ProjectSecurityKey;
import de.krayadev.domain.aggregates.userAggregate.entities.user.User;
import lombok.*;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"sentMessages", "tags", "kanbanBoards"})
@EqualsAndHashCode(of = {"name"})
@Getter
@Setter
@Entity
@Table(name = "project", schema = "backend")
public class Project {

    @Id
    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "key", length = 128, columnDefinition = "bpchar(128)", unique = true, updatable = false, nullable = false)) })
    private ProjectSecurityKey key = new ProjectSecurityKey();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<KanbanBoard> kanbanBoards;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Task> tasks;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<ProjectMembership> memberships;

    @OneToMany(mappedBy = "origin", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Message> sentMessages;

    public Project(@NonNull String name) {
        this.name = name;
        this.description = "";
    }

    public Project(@NonNull String name, String description) {
        this.name = name;
        this.description = "";
        if(description != null)
            this.description = description;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = "";
        if(description != null)
            this.description = description;
    }

    public void updateSecurityKey(){
        this.key = new ProjectSecurityKey();
    }

    public void create(@NonNull KanbanBoard kanbanBoard){
        this.kanbanBoards.add(kanbanBoard);
    }

    public void delete(@NonNull KanbanBoard kanbanBoard){
        this.kanbanBoards.remove(kanbanBoard);
    }

    public void create(@NonNull Task task){
        this.tasks.add(task);
    }

    public void delete(@NonNull Task task){
        this.tasks.remove(task);
    }

    public boolean isTeamMember(@NonNull User user){
        return this.getProjectMembershipOf(user) != null;
    }

    private ProjectMembership getProjectMembershipOf(@NonNull User user){
        return this.memberships.stream().filter(m -> m.getUser().equals(user)).findFirst().orElse(null);
    }

    public void add(@NonNull User teamMember, String projectSecurityKey){
        if(this.isTeamMember(teamMember))
            throw new IllegalArgumentException("User is already a member of this project");

        List<Message> invitationsToUser = this.sentMessages.stream()
                .filter(message -> message.hasType(MessageType.INVITATION) && message.isFor(teamMember))
                .sorted((m1, m2) -> m2.getDispatch().compareTo(m1.getDispatch())).collect(Collectors.toList());

        if(invitationsToUser.isEmpty())
            throw new IllegalArgumentException("No invitation found for user");

        if(!projectSecurityKey.equals(this.key.getValue()))
            throw new IllegalArgumentException("Incorrect Security Key passed");

        JSONObject latestInvitationContent = invitationsToUser.get(0).getContentAsJson();
        String roleName = latestInvitationContent.get("role").toString();
        ProjectRole role = ProjectRole.fromString(roleName);

        ProjectMembership membership = new ProjectMembership(this, teamMember, role);
        this.memberships.add(membership);

        this.memberships.stream().map(ProjectMembership::getUser)
                .forEach(user -> this.send(MessageFactory.createNewProjectMemberMessage(this, user, membership)));
    }

    public void remove(@NonNull User teamMember){
        if(!this.isTeamMember(teamMember))
            throw new IllegalArgumentException("User is not a member of this project");

        ProjectMembership membership = this.getProjectMembershipOf(teamMember);
        this.memberships.remove(membership);
        this.send(MessageFactory.createKickedFromProjectMessage(this, teamMember));

    }

    public void promote(@NonNull User admin, @NonNull User teamMember){
        if(!this.isAdmin(admin))
            throw new IllegalArgumentException("Unauthorized promotion: Given user is not an admin of this project");

        if(!this.isTeamMember(teamMember))
            throw new IllegalArgumentException("User is not a member of this project");

        ProjectMembership membership = this.getProjectMembershipOf(teamMember);
        membership.setRole(ProjectRole.ADMIN);
    }

    public boolean isAdmin(@NonNull User user){
        if(!this.isTeamMember(user))
            throw new IllegalArgumentException("User is not a member of this project");

        ProjectMembership membership = this.getProjectMembershipOf(user);
        return membership.getRole() == ProjectRole.ADMIN;
    }

    public void invite(@NonNull User user, @NonNull ProjectRole role){
        Message invitation = MessageFactory.createInvitationToProjectForUser(this, user, role);
        this.send(invitation);
    }

    public void revoke(@NonNull Message invitation){
        if(!invitation.hasType(MessageType.INVITATION))
            throw new IllegalArgumentException("Only Invitations can be revoked");

        this.sentMessages.remove(invitation);
    }

    public void send(@NonNull Message message){
        this.sentMessages.add(message);
    }

}
