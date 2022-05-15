package de.krayadev.plugins.DbConnector.repos;

import de.krayadev.domain.repositories.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import de.krayadev.domain.aggregates.userAggregate.entities.user.User;

public interface SpringDataUserRepository extends JpaRepository<User, String>, UserRepository {}
