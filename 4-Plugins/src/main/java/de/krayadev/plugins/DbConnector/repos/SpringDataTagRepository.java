package de.krayadev.plugins.DbConnector.repos;

import de.krayadev.domain.repositories.TagRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import de.krayadev.domain.aggregates.userAggregate.entities.tag.Tag;

import java.util.UUID;

public interface SpringDataTagRepository extends JpaRepository<Tag, UUID>, TagRepository {}
