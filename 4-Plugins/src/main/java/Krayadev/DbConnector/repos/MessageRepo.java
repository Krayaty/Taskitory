package Krayadev.DbConnector.repos;

import Krayadev.DbConnector.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepo extends JpaRepository<Message, UUID> {
}
