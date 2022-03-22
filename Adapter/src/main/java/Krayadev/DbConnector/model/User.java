package Krayadev.DbConnector.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

}