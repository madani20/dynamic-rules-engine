package org.mad.engine.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mad.engine.models.AndRule;
import org.mad.engine.models.Rule;
import org.mad.engine.models.SimpleRule;
import org.mad.engine.models.chainOfResponsibility.Handler;
import org.mad.engine.models.chainOfResponsibility.LoggingHandler;
import org.mad.engine.models.entities.RuleEntity;
import org.mad.engine.models.operators.GreaterThanOperator;
import org.mad.engine.repositories.RuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RuleEngineService {
    private final RuleRepository repo;
    private final ObjectMapper mapper = new ObjectMapper();

    public RuleEngineService(RuleRepository repo) {
        this.repo = repo;
    }

    public boolean evaluate(Long ruleId, Map<String, Object> data) throws Exception {
        /** Récupération de la régle */
        RuleEntity entity = repo.findById(ruleId).orElseThrow();
        String json = entity.getRuleJson();

        // Simuler parsing du JSON -> Rule
        Rule rule = buildRuleFromJson(json);  // À implémenter : parser JSON en arborescence de Rule

        // Chain of Responsibility
        Handler handler = new LoggingHandler();
        handler.handle(data);

        return rule.evaluate(data);
    }

    private Rule buildRuleFromJson(String json) {
        // Pour le MVP : retourne une règle codée en dur
        return new AndRule(List.of(
                new SimpleRule("age", new GreaterThanOperator(), 21),
                new SimpleRule("income", new GreaterThanOperator(), 1500)
        ));
    }
}

