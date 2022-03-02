package de.krayadev.taskitory.dbConnector.repos;

import de.krayadev.taskitory.dbConnector.idClasses.AbhängigkeitsId;
import de.krayadev.taskitory.dbConnector.model.Abhängigkeit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbhängigeAufgabenRepo extends JpaRepository<Abhängigkeit, AbhängigkeitsId> {}
