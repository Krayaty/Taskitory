package Krayadev.DbConnector.repos;

import Krayadev.DbConnector.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepo extends JpaRepository<Tag, Integer> {}
