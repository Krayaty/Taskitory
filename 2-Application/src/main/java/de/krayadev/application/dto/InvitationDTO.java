package de.krayadev.application.dto;

import de.krayadev.domain.aggregates.projectAggregate.entities.projectMembership.ProjectRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvitationDTO {
    private String projectName;
    private String newTeamMemberId;
    private ProjectRole role;
}
