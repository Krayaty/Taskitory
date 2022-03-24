package Krayadev.DbConnector.repos;

import Krayadev.DbConnector.model.KanbanBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Todo Repo Annotation muss hier weg
@Repository
public interface KanbanBoardRepo extends JpaRepository<KanbanBoard, Integer> {}
