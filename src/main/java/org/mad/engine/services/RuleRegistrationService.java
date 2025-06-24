package org.mad.engine.services;

import org.mad.engine.models.entities.RuleEntity;
import org.mad.engine.repositories.RuleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RuleRegistrationService {
    private final RuleRepository repo;

    @Value("${json.file.path}")
    private final String ruleJsonFormat;

    public RuleRegistrationService(RuleRepository repo, String ruleJsonFormat) {
        this.repo = repo;
        this.ruleJsonFormat = ruleJsonFormat;
    }

        public void registerRule (){
            RuleEntity entity = new RuleEntity();
            entity.setRuleJson(ruleJsonFormat);
            repo.save(entity);
        }
    }

