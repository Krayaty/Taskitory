package de.krayadev.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChangeProjectMembershipDTO {
    private String projectName;
    private String teamMemberId;
}
