package de.krayadev.taskitory.dbConnector.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AufgabenAbhängigekeitsTyp {
    VORAUSSETZUNG("Voraussetzung"),
    FOLGEAUFGABE("Folgeaufgabe"),
    TEILAUFGABE("Teilaufgabe");

    private String bezeichnung;
}
