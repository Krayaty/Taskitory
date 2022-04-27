package de.krayadev.domain.aggregates.projectAggregate.entities.projectMembership;

import de.krayadev.domain.aggregates.projectAggregate.entities.project.Project;
import de.krayadev.domain.aggregates.userAggregate.entities.user.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"de/krayadev/domain/aggregates/projectAggregate/entities/project", "de/krayadev/domain/aggregates/projectAggregate/entities/user"})
@ToString(exclude = {"id"})
@Getter
@Setter
@Entity
@Table(name = "project_membership", schema = "backend")
public class ProjectMembership {

    @EmbeddedId
    private ProjectMembershipId id;

    @MapsId("projectName")
    @ManyToOne(targetEntity = Project.class)
    @JoinColumn(name = "project", referencedColumnName = "name", nullable = false, updatable = false)
    @NonNull
    private Project project;

    @MapsId("userId")
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false, updatable = false)
    @NonNull
    private User user;

    @Column(name = "start_of_membership", length = 6, nullable = false, updatable = false)
    private final Timestamp startOfMembership = Timestamp.valueOf(LocalDateTime.now());

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectRole role = ProjectRole.MEMBER;

    public ProjectMembership(@NonNull Project project, @NonNull User user, ProjectRole role) {
        this.project = project;
        this.user = user;
        this.role = role;
    }

    public void setRole(ProjectRole role) {
        this.role = role;
    }
}
