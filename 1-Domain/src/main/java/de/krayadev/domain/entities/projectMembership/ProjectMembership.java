package de.krayadev.domain.entities.projectMembership;

import de.krayadev.domain.entities.project.Project;
import de.krayadev.domain.entities.user.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"de/krayadev/domain/entities/project", "de/krayadev/domain/entities/user"})
@ToString(exclude = {"id"})
@Getter
@Setter
@Entity
@Table(name = "project_membership", schema = "backend")
public class ProjectMembership {

    @EmbeddedId
    @NonNull
    private ProjectMembershipId id;

    @MapsId("projectName")
    @ManyToOne(targetEntity = Project.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "project", referencedColumnName = "name", nullable = false, updatable = false)
    @NonNull
    private Project project;

    @MapsId("userId")
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false, updatable = false)
    @NonNull
    private User user;

    @Column(name = "start_of_membership", length = 6, nullable = false, updatable = false)
    private final Timestamp startOfMembership = Timestamp.valueOf(LocalDateTime.now());

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectRole role = ProjectRole.MEMBER;

    public void setRole(ProjectRole role) {
        this.role = role;
    }
}
