package de.krayadev.plugins.DbConnector.repos;

import de.krayadev.domain.kanbanBoard.KanbanBoard;
import de.krayadev.domain.kanbanBoard.KanbanBoardRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataKanbanBoardRepository extends JpaRepository<KanbanBoard, UUID>, KanbanBoardRepository {}
