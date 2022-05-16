package de.krayadev.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class CreateProjectDTO {
    @NonNull
    private String name;
    private String description;
}
