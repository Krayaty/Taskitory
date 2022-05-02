package de.krayadev.domain.repositories;

import de.krayadev.domain.aggregates.userAggregate.entities.user.User;

public interface UserRepository {

    public User getUserById(String id);

}
