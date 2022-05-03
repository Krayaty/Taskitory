package de.krayadev.domain.repositories;

import de.krayadev.domain.aggregates.projectAggregate.entities.project.Project;

public interface ProjectRepository {

    Project findById(String name);

    Project save(Project project);

}
