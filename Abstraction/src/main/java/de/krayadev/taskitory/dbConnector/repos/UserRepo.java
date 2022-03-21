package de.krayadev.taskitory.dbConnector.repos;

import de.krayadev.taskitory.dbConnector.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String> {}
