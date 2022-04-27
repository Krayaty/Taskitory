package de.krayadev.plugins.DbConnector.repos;

import de.krayadev.domain.aggregates.userAggregate.entities.message.Message;
import de.krayadev.domain.repositories.MessageRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataMessageRepository extends JpaRepository<Message, UUID>, MessageRepository {
}
