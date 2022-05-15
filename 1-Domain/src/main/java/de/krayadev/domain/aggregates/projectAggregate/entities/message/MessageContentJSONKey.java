package de.krayadev.domain.aggregates.projectAggregate.entities.message;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum MessageContentJSONKey {
    TITLE("Titel"),
    PROJECT_SEC_KEY("Projekt-Schlüssel"),
    ROLE("Rolle"),
    OLD_ROLE("Alte Rolle"),
    NEW_ROLE("Neue Rolle"),
    PROJECT_NAME("Projektbezeichnung"),
    USER_NAME("Benutzername");


    private String value;

}
