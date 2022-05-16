package de.krayadev.application.dto;

import de.krayadev.domain.aggregates.projectAggregate.valueObjects.Sprint;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class CreateKanbanBoardDTO {

    private String name;

    private String description;

    @NonNull
    private Sprint sprint;

    private boolean showReviewColumn;

    private boolean showTestingColumn;

    @NonNull
    private String projectName;

    public CreateKanbanBoardDTO(String name, String description, @NonNull Sprint sprint, boolean showReviewColumn, boolean showTestingColumn, @NonNull String projectName) {
        this.name = name;
        this.description = description;
        this.sprint = sprint;
        this.showReviewColumn = showReviewColumn;
        this.showTestingColumn = showTestingColumn;
        this.projectName = projectName;
    }

}
