package de.krayadev.application.services;

import de.krayadev.domain.kanbanBoard.KanbanBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KanbanBoardApplicationService {

    @Autowired
    private KanbanBoardRepository kanbanBoardRepository;

}
