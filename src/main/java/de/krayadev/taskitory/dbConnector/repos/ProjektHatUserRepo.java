package de.krayadev.taskitory.dbConnector.repos;

import de.krayadev.taskitory.dbConnector.idClasses.ProjektMitgliedschaftsId;
import de.krayadev.taskitory.dbConnector.model.ProjektMitgliedschaft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjektHatUserRepo extends JpaRepository<ProjektMitgliedschaft, ProjektMitgliedschaftsId> {}
