package krayadev.plugins.DbConnector.repos;

import krayadev.domain.kanbanboard.KanbanBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface KanbanBoardRepo extends JpaRepository<KanbanBoard, UUID> {}
