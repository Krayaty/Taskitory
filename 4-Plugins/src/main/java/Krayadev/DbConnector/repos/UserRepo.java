package Krayadev.DbConnector.repos;

import Krayadev.DbConnector.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {}
