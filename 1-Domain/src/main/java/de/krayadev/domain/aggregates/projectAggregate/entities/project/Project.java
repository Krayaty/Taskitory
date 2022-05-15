package de.krayadev.domain.aggregates.projectAggregate.entities.project;

import de.krayadev.domain.aggregates.projectAggregate.entities.kanbanBoard.KanbanBoard;
import de.krayadev.domain.aggregates.projectAggregate.entities.projectMembership.ProjectRole;
import de.krayadev.domain.aggregates.projectAggregate.valueObjects.Sprint;
import de.krayadev.domain.aggregates.projectAggregate.entities.task.Task;
import de.krayadev.domain.aggregates.projectAggregate.entities.message.Message;
import de.krayadev.domain.aggregates.projectAggregate.entities.message.MessageFactory;
import de.krayadev.domain.aggregates.projectAggregate.entities.message.MessageType;
import de.krayadev.domain.aggregates.projectAggregate.entities.projectMembership.ProjectMembership;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.krayadev.domain.aggregates.projectAggregate.valueObjects.ProjectSecurityKey.ProjectSecurityKey;
import de.krayadev.domain.aggregates.projectAggregate.entities.task.TaskStatus;
import de.krayadev.domain.aggregates.userAggregate.entities.user.User;
import de.krayadev.domain.valueObjects.Description;
import lombok.*;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"sentMessages", "tags", "kanbanBoards"})
@EqualsAndHashCode(of = {"name"})
@Getter
@Entity
@Table(name = "project", schema = "backend")
public class Project {

    @Id
    @Column(length = 100, nullable = false, updatable = false)
    private String name;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(length = 500))
    })
    private Description description;

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
        this.description = new Description();
    }

    public Project(@NonNull String name, String description) {
        this.name = name;
        this.description = new Description(description);
    }

    private ProjectMembership getMembershipOf(@NonNull User user){
        return this.memberships.stream().filter(m -> m.getUser().equals(user)).findFirst().orElse(null);
    }

    private void remove(@NonNull User teamMember){
        ProjectMembership membership = this.getMembershipOf(teamMember);
        this.memberships.remove(membership);
    }

    public boolean isTeamMember(@NonNull User user){
        return this.getMembershipOf(user) != null;
    }

    public boolean isAdmin(@NonNull User user){
        if(!this.isTeamMember(user))
            throw new IllegalArgumentException("User is not a member of this project");

        ProjectMembership membership = this.getMembershipOf(user);
        return membership.isAdmin();
    }

    private boolean possesses(@NonNull KanbanBoard kanbanBoard){
        return this.kanbanBoards.contains(kanbanBoard);
    }

    private boolean possesses(@NonNull Task task){
        return this.tasks.contains(task);
    }

    private boolean sent(@NonNull Message message){
        return this.sentMessages.contains(message);
    }

    private void send(@NonNull Message message){
        this.sentMessages.add(message);
    }

    public void changeDescription(String newDescription) {
        this.description = new Description(newDescription);
    }

    public void changeSecurityKey(){
        this.key = new ProjectSecurityKey();
    }

    public void create(@NonNull KanbanBoard kanbanBoard) {
        if(!this.possesses(kanbanBoard))
            throw new IllegalArgumentException("Kanban board should be possessed by this project");

        this.kanbanBoards.add(kanbanBoard);
    }

    public void migrate(@NonNull KanbanBoard oldKanbanBoard, @NonNull Sprint newSprint){
        if(!this.possesses(oldKanbanBoard))
            throw new IllegalArgumentException("Kanban board should be possessed by this project");

        KanbanBoard newKanbanBoard = oldKanbanBoard.migrate(newSprint);
        this.create(newKanbanBoard);
    }

    public List<KanbanBoard> getKanbanBoardsWithActiveSprint(){
        return this.kanbanBoards.stream().filter(kb -> kb.isUsable()).collect(Collectors.toList());
    }

    public List<KanbanBoard> getKanbanBoardsWithFinishedSprint(){
        return this.kanbanBoards.stream().filter(kb -> !kb.isUsable()).collect(Collectors.toList());
    }

    public void putTaskOnKanbanBoard(@NonNull Task task, @NonNull KanbanBoard kanbanBoard){
        if(!this.possesses(task))
            throw new IllegalArgumentException("Task should be possessed by this project");

        if(!this.possesses(kanbanBoard))
            throw new IllegalArgumentException("Kanban board should be possessed by this project");

        kanbanBoard.assign(task);
    }

    public void moveTaskOnKanbanBoard(@NonNull Task task, @NonNull KanbanBoard kanbanBoard, @NonNull TaskStatus newState){
        if(!this.possesses(task))
            throw new IllegalArgumentException("Task should be possessed by this project");

        if(!this.possesses(kanbanBoard))
            throw new IllegalArgumentException("Kanban board should be possessed by this project");

        kanbanBoard.move(task, newState);
    }

    public void moveTaskToBacklog(@NonNull Task task, @NonNull KanbanBoard kanbanBoard){
        if(!this.possesses(task))
            throw new IllegalArgumentException("Task should be possessed by this project");

        if(!this.possesses(kanbanBoard))
            throw new IllegalArgumentException("Kanban board should be possessed by this project");

        kanbanBoard.remove(task);
    }

    public void delete(@NonNull KanbanBoard kanbanBoard){
        if(!this.possesses(kanbanBoard))
            throw new IllegalArgumentException("The given kanban board isn't possessed by this project");

        this.kanbanBoards.remove(kanbanBoard);
    }

    public void create(@NonNull Task task){
        if(!this.possesses(task))
            throw new IllegalArgumentException("Task should be possessed by this project");

        this.tasks.add(task);
    }

    public List<Task> getTasksInBacklog(){
        return this.tasks.stream().filter(task -> task.isInBacklog()).toList();
    }

    public Map<TaskStatus, List<Task>> getTasksOnKanbanBoard(@NonNull KanbanBoard kanbanBoard){
        if(!this.possesses(kanbanBoard))
            throw new IllegalArgumentException("Kanban board should be possessed by this project");

        return kanbanBoard.getAssignedTasksPerStatus();
    }

    public void delete(@NonNull Task task){
        if(!this.possesses(task))
            throw new IllegalArgumentException("The given task isn't possessed by this project");

        if(task.isOnKanbanBoard() && !task.getAssignedKanbanBoard().isUsable())
            throw new IllegalArgumentException("The given task is on an uneditable kanban board. Therefore you can not delete this task");

        this.tasks.remove(task);
    }

    public void add(@NonNull User teamMember, String projectSecurityKey){
        if(this.isTeamMember(teamMember))
            throw new IllegalArgumentException("User is already a member of this project");

        List<Message> invitationsToUser = this.getInvitationsFor(teamMember);

        if(invitationsToUser.isEmpty())
            throw new IllegalArgumentException("No invitation found for user");

        if(!this.key.equals(projectSecurityKey))
            throw new IllegalArgumentException("Incorrect Security Key passed");

        JSONObject latestInvitationContent = invitationsToUser.get(0).getContentAsJson();
        String roleName = latestInvitationContent.get("role").toString();
        ProjectRole role = ProjectRole.fromString(roleName);

        ProjectMembership membership = new ProjectMembership(this, teamMember, role);
        this.memberships.add(membership);

        this.memberships.stream().map(ProjectMembership::getUser)
                .forEach(user -> this.send(MessageFactory.createNewProjectMemberMessage(this, user, membership)));
    }

    public Map<User, ProjectRole> getTeamMembersWithRoles(){
        return this.memberships.stream().collect(Collectors.toMap(ProjectMembership::getUser, ProjectMembership::getRole));
    }

    public List<User> getAdmins(){
        return this.memberships.stream().filter(membership -> membership.isAdmin()).map(ProjectMembership::getUser).toList();
    }

    public List<User> getMembers(){
        return this.memberships.stream().filter(membership -> !membership.isAdmin()).map(ProjectMembership::getUser).toList();
    }

    public void promote(@NonNull User teamMember){
        if(!this.isTeamMember(teamMember))
            throw new IllegalArgumentException("User is not a member of this project");

        ProjectMembership membership = this.getMembershipOf(teamMember);
        membership.promote();

        this.memberships.stream().map(ProjectMembership::getUser)
                .forEach(user -> this.send(MessageFactory.createProjectMemberRoleChangeMessage(user, membership, ProjectRole.ADMIN)));
    }

    public void kick(@NonNull User teamMember){
        if(!this.isTeamMember(teamMember))
            throw new IllegalArgumentException("User is not a member of this project");

        this.remove(teamMember);
        this.send(MessageFactory.createKickedFromProjectMessage(this, teamMember));

    }

    public void invite(@NonNull User user, @NonNull ProjectRole role){
        if(this.isTeamMember(user))
            throw new IllegalArgumentException("User is already a member of this project");

        Message invitation = MessageFactory.createInvitationToProjectForUser(this, user, role);
        this.send(invitation);
    }

    public List<Message> getInvitations(){
        return this.sentMessages.stream().filter(message -> message.hasType(MessageType.INVITATION))
                .sorted((m1, m2) -> m2.getDispatch().compareTo(m1.getDispatch())).toList();
    }

    public List<Message> getInvitationsFor(@NonNull User user){
        return this.sentMessages.stream().filter(message -> message.hasType(MessageType.INVITATION) && message.isFor(user))
                .sorted((m1, m2) -> m2.getDispatch().compareTo(m1.getDispatch())).toList();
    }

    public List<Message> getMessagesFor(@NonNull User user){
        return this.sentMessages.stream().filter(message -> message.isFor(user))
                .sorted((m1, m2) -> m2.getDispatch().compareTo(m1.getDispatch())).toList();
    }

    public void revoke(@NonNull Message invitation){
        if(!this.sent(invitation))
            throw new IllegalArgumentException("The given message was not sent by this project");

        if(!invitation.hasType(MessageType.INVITATION))
            throw new IllegalArgumentException("Only invitations can be revoked");

        this.sentMessages.remove(invitation);
    }

}
