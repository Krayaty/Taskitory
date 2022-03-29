package message;

import kanbanboard.KanbanBoard;
import lombok.*;
import project.Project;
import user.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode(exclude = {"read"})
@Getter
public class Message {

    @NonNull
    private final Project origin;

    @NonNull
    private final User recipient;

    @NonNull
    private final String contents;

    @NonNull
    private final MessageType type;

    @NonNull
    private final Timestamp dispatch;

    @NonNull
    private boolean read;

    protected Message(@NonNull Project origin, @NonNull User recipient, @NonNull String contents, @NonNull MessageType type) {
        this.origin = origin;
        this.recipient = recipient;
        this.contents = contents;
        this.type = type;
        this.dispatch = Timestamp.valueOf(LocalDateTime.now());
        this.read = false;
    }

    public void markAsRead() {
    	this.read = true;
    }

}
