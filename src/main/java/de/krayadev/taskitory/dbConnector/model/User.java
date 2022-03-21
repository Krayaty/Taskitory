package de.krayadev.taskitory.dbConnector.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_entity", schema = "iam")
public class User {

    @Id
    private String id;

    @Column
    private String email;

    @Column
    private String email_constraint;

    @Column
    private boolean email_verified;

    @Column
    private boolean enabled;

    @Column
    private String federation_link;

    @Column
    private String first_name;

    @Column
    private String last_name;

    @Column
    private String realm_id;

    @Column
    private String username;

    @Column
    private long created_timestamp;

    @Column
    private String service_account_client_link;

    @Column
    private int not_before;

    @OneToMany(mappedBy="responsibleUser", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Task> assignedTasks;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ProjectMembership> memberships;

    @Override
    public String toString(){
        String result = "id: " + this.id + "\n" +
                "username: " + this.username + "\n" +
                "assignedTasks: [";
        for (Task task: this.assignedTasks) {
            result += "{taskId: " + task.getId() + "},\n";
        }
        result += "]\n" + "memberships: [";
        for (ProjectMembership membership: this.memberships) {
            result += "{projectId" + membership.getProject() + "},\n";
        }
        result += "]";

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User vergleichsobjekt = (User) o;
        return this.id == vergleichsobjekt.id;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Set<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public Set<ProjectMembership> getMemberships() {
        return memberships;
    }

}
