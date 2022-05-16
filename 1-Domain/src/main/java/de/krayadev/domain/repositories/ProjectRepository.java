package de.krayadev.domain.repositories;

import de.krayadev.domain.aggregates.projectAggregate.entities.project.Project;

public interface ProjectRepository {

    Project findByName(String name);

    Project save(Project project);

    void delete(Project project);

}
