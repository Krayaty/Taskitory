package Krayadev.DbConnector.repos;

import Krayadev.DbConnector.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TagRepo extends JpaRepository<Tag, UUID> {}
