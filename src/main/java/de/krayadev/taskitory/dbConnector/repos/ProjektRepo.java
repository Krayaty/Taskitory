package de.krayadev.taskitory.dbConnector.repos;

import de.krayadev.taskitory.dbConnector.model.Projekt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjektRepo extends JpaRepository<Projekt, Integer> {}
