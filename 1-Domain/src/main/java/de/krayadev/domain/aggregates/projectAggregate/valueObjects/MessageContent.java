package de.krayadev.domain.aggregates.projectAggregate.valueObjects;

import lombok.*;
import org.json.JSONObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@Getter
public final class MessageContent {

    @NonNull
    private String content;

    public MessageContent(@NonNull JSONObject content) {
        this.content = content.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MessageContent that = (MessageContent) o;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return this.content;
    }
}
