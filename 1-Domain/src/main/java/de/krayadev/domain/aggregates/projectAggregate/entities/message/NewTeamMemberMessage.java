package de.krayadev.domain.aggregates.projectAggregate.entities.message;

import de.krayadev.domain.aggregates.projectAggregate.entities.project.Project;
import de.krayadev.domain.aggregates.projectAggregate.valueObjects.MessageContent;
import de.krayadev.domain.aggregates.userAggregate.entities.user.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewTeamMemberMessage extends Message{
    protected NewTeamMemberMessage(@NonNull MessageContent content, @NonNull Project origin, @NonNull User recipient) {
        super(content, MessageType.NEW_TEAM_MEMBER, origin, recipient);
    }
}
