package de.krayadev.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.krayadev.domain.message.Message;
import de.krayadev.domain.projectMembership.ProjectMembership;
import de.krayadev.domain.task.Task;
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
@Setter
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

    @Column(length = 255, nullable = false)
    @NonNull
    private String email;

    @Column(length = 255)
    private String email_constraint;

    @Column
    private final boolean email_verified = true;

    @Column
    private final boolean enabled = true;

    @Column(length = 255)
    private String federation_link;

    @Column(length = 255, nullable = false)
    @NonNull
    private String first_name;

    @Column(length = 255, nullable = false)
    @NonNull
    private String last_name;

    @Column
    private final String realm_id = "Taskitory-Realm";

    @Column
    private final long created_timestamp = System.currentTimeMillis();

    @Column(length = 255)
    private String service_account_client_link;

    @Column
    private final int not_before = 0;

    @OneToMany(mappedBy="responsibleUser", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Task> assignedTasks = new HashSet<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Task> createdTasks = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ProjectMembership> memberships = new HashSet<>();

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Message> receivedMessages = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public void assignTask(Task task){
        assignedTasks.add(task);
    }

    public void unassignTask(Task task){
        assignedTasks.remove(task);
    }

    public void addMembership(ProjectMembership membership){
        memberships.add(membership);
    }

    public void removeMembership(ProjectMembership membership){
        memberships.remove(membership);
    }

    public void receiveMessage(Message message){
        receivedMessages.add(message);
    }

    public void deleteMessage(Message message){
        receivedMessages.remove(message);
    }

}
