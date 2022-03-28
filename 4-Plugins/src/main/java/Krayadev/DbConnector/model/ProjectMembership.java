package Krayadev.DbConnector.model;

import Krayadev.DbConnector.idClasses.ProjectMembershipId;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "project_membership",
        schema = "backend",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"admin", "project"})
        })
public class ProjectMembership {

    @EmbeddedId
    private ProjectMembershipId id;

    @MapsId("projectId")
    @ManyToOne(targetEntity = Project.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "project", referencedColumnName = "id", nullable = false, updatable = false)
    private Project project;

    @MapsId("userId")
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "start_of_membership", length = 6, nullable = false, updatable = false)
    private final Timestamp startOfMembership = Timestamp.valueOf(LocalDateTime.now());

    @Column(nullable = false)
    private boolean admin = false;

}
