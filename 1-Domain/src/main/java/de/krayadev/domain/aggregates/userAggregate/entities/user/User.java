package de.krayadev.domain.aggregates.userAggregate.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.krayadev.domain.aggregates.taskAggregate.entities.task.Task;
import de.krayadev.domain.aggregates.userAggregate.entities.message.Message;
import de.krayadev.domain.aggregates.projectAggregate.entities.projectMembership.ProjectMembership;
import de.krayadev.domain.aggregates.userAggregate.valueObjects.IrrelevantUserData;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ProjectMembership> memberships = new HashSet<>();

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Message> receivedMessages = new HashSet<>();

    public void assignTask(@NonNull Task task){
        assignedTasks.add(task);
    }

    public void unassignTask(@NonNull Task task){
        assignedTasks.remove(task);
    }

    public void addMembership(@NonNull ProjectMembership membership){
        memberships.add(membership);
    }

    public void removeMembership(@NonNull ProjectMembership membership){
        memberships.remove(membership);
    }

    public void receiveMessage(@NonNull Message message){
        receivedMessages.add(message);
    }

    public void deleteMessage(@NonNull Message message){
        receivedMessages.remove(message);
    }

}
