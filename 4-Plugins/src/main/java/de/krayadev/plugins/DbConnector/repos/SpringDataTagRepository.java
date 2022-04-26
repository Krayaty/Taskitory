package de.krayadev.plugins.DbConnector.repos;

import de.krayadev.domain.tag.TagRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import de.krayadev.domain.tag.Tag;

import java.util.UUID;

public interface SpringDataTagRepository extends JpaRepository<Tag, UUID>, TagRepository {}
