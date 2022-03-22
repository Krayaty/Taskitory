package Krayadev.DbConnector.repos;

import Krayadev.DbConnector.model.KanbanBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KanbanBoardRepo extends JpaRepository<KanbanBoard, Integer> {}
