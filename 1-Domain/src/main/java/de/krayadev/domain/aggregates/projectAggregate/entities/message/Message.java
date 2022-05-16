package de.krayadev.domain.aggregates.projectAggregate.entities.message;

import de.krayadev.domain.aggregates.projectAggregate.entities.project.Project;
import de.krayadev.domain.aggregates.userAggregate.entities.user.User;
import de.krayadev.domain.aggregates.projectAggregate.valueObjects.MessageContent;
import lombok.*;
import org.json.JSONObject;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode(of = "id")
@Getter
@Entity
@Table(name = "message", schema = "backend")
public abstract class Message {

    @Id
    private final UUID id = UUID.randomUUID();

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "content", column = @Column(length = 500, nullable = false, updatable = false)) })
    @NonNull
    private MessageContent content;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private MessageType type;

    @Column(length = 6, nullable = false, updatable = false)
    private final Timestamp dispatch = Timestamp.valueOf(LocalDateTime.now());

    @Column(nullable = false)
    private boolean read = false;

    @ManyToOne(targetEntity = Project.class)
    @JoinColumn(name = "origin", referencedColumnName = "name", nullable = false, updatable = false)
    @NonNull
    private Project origin;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "recipient", referencedColumnName = "id", nullable = false, updatable = false)
    @NonNull
    private User recipient;

    public void markAsRead() {
    	this.read = true;
    }

    public JSONObject getContentAsJson() {
    	return new JSONObject(this.content.getContent());
    }
    public boolean hasType(MessageType type) {
    	return this.type == type;
    }

    public boolean isFor(User user) {
    	return this.recipient.equals(user);
    }

}
