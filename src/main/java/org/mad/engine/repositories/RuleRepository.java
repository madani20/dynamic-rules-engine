package org.mad.engine.repositories;

import org.mad.engine.models.entities.RuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<RuleEntity, Long> {
}
