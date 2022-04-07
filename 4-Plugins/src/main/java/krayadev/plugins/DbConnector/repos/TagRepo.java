package krayadev.plugins.DbConnector.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import krayadev.domain.tag.Tag;

import java.util.UUID;

public interface TagRepo extends JpaRepository<Tag, UUID> {}
