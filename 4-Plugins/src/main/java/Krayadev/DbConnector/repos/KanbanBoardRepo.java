package Krayadev.DbConnector.repos;

import Krayadev.DbConnector.model.KanbanBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface KanbanBoardRepo extends JpaRepository<KanbanBoard, UUID> {}
