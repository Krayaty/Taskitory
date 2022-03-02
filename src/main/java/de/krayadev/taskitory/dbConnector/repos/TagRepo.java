package de.krayadev.taskitory.dbConnector.repos;

import de.krayadev.taskitory.dbConnector.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepo extends JpaRepository<Tag, Integer> {}
