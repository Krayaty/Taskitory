package projectMembership;

import lombok.*;
import project.Project;
import user.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@EqualsAndHashCode(of = {"project", "user"})
@ToString
@Getter
public class ProjectMembership {

    @NonNull
    private final Project project;

    @NonNull
    private final User user;

    private final Timestamp startOfMembership = Timestamp.valueOf(LocalDateTime.now());

    @NonNull
    private Role role;

    public void setRole(Role role) {
        this.role = role;
    }
}
