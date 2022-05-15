package de.krayadev.domain.repositories;

import de.krayadev.domain.aggregates.userAggregate.entities.user.User;

import java.util.Collection;

public interface UserRepository {

    public User getUserById(String id);

    public User save(User user);

}
