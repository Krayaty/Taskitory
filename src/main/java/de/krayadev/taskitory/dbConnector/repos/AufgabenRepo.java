package de.krayadev.taskitory.dbConnector.repos;

import de.krayadev.taskitory.dbConnector.model.Aufgabe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AufgabenRepo extends JpaRepository<Aufgabe, Integer> {}
