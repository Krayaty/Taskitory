package de.krayadev.taskitory.dbConnector.model;

import de.krayadev.taskitory.dbConnector.idClasses.ProjectMembershipId;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
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
    @ManyToOne(targetEntity = Project.class, cascade = CascadeType.ALL) // Todo cascade Types prüfen
    @JoinColumn(name = "project", referencedColumnName = "id", nullable = false, updatable = false)
    private Project projekt;

    @MapsId("userId")
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "start_of_membership", length = 6, nullable = false, updatable = false)
    private final Timestamp startOfMembership = Timestamp.valueOf(LocalDateTime.now());

    @Column(nullable = false)
    private boolean admin = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectMembership vergleichsobjekt = (ProjectMembership) o;
        return this.id == vergleichsobjekt.id;
    }

    public void promote(){
        this.admin = true;
    }

    public void demote(){
        this.admin = false;
    }
}
