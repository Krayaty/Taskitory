package de.krayadev.plugins.DbConnector.repos;

import de.krayadev.domain.user.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import de.krayadev.domain.user.User;

public interface SpringDataUserRepository extends JpaRepository<User, String>, UserRepository {}
