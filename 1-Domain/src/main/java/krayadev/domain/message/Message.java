package krayadev.domain.message;

import krayadev.domain.project.Project;
import krayadev.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@Table(name = "message", schema = "backend")
public class Message {

    @Id
    private final UUID id = UUID.randomUUID();

    @Column(length = 500, nullable = false, updatable = false)
    private String content;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @Column(length = 6, nullable = false, updatable = false)
    private final Timestamp dispatch = Timestamp.valueOf(LocalDateTime.now());

    @Column(nullable = false)
    private boolean read = false;

    @ManyToOne(targetEntity = Project.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "origin", referencedColumnName = "name", nullable = false, updatable = false)
    private Project origin;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipient", referencedColumnName = "id", nullable = false, updatable = false)
    private User recipient;

    protected Message(@NonNull Project origin, @NonNull User recipient, @NonNull String content, @NonNull MessageType type) {
        this.origin = origin;
        this.recipient = recipient;
        this.content = content;
        this.type = type;
    }

    public void markAsRead() {
    	this.read = true;
    }

}
