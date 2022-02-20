package de.krayadev.taskitory.dbConnector.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AufgabenStatus {
    UNASSIGNED("Unassigned"),
    TODO("Todo"),
    IN_PROGRESS("In Progress"),
    REVIEW("Review"),
    TESTING("Testing"),
    DONE("Done");

    private String bezeichnung;
}
