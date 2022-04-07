package krayadev.plugins.DbConnector.repos;

import krayadev.domain.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepo extends JpaRepository<Message, UUID> {
}
