package de.krayadev.plugins.DbConnector.repos;

import de.krayadev.domain.aggregates.projectAggregate.entities.kanbanBoard.KanbanBoard;
import de.krayadev.domain.repositories.KanbanBoardRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataKanbanBoardRepository extends JpaRepository<KanbanBoard, UUID>, KanbanBoardRepository {}
