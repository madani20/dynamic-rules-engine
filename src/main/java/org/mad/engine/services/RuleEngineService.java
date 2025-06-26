package org.mad.engine.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mad.engine.dto.RuleDTO;
import org.mad.engine.exceptions.EvaluateException;
import org.mad.engine.exceptions.RuleNotFoundException;
import org.mad.engine.models.entities.RuleEntity;
import org.mad.engine.repositories.RuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class RuleEngineService {
    private static final Logger logger = LoggerFactory.getLogger(RuleEngineService.class);

    private final RuleRepository rulesRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    public RuleEngineService(RuleRepository repo) {
        this.rulesRepository = repo;
    }


    public boolean evaluate(Long ruleId, Map<String, Object> data) {
        logger.debug("Init evaluate from service");

        validateInput(ruleId, data);

        // Récupération de la règle en BDD
        RuleDTO rootRuleDTO = loadRuleAsDTO(ruleId);

        // Évaluation de la règle racine
        boolean evaluation = evaluateRule(rootRuleDTO, data);

        logger.debug("Data evaluated");
        return evaluation;
    }



    //================================== [ Utilitaires ] ===========================================================================

    /**
     *  Evaluate rules recursively
     *
     * @param ruleDTO
     * @param dataFromUser
     * @return
     */
    private boolean evaluateRule (RuleDTO ruleDTO, Map<String, Object> dataFromUser) {
        switch (ruleDTO.getType()) {

            case "AND" :
                return ruleDTO.getRules()
                        .stream()
                        .allMatch( rule -> evaluateRule(rule, dataFromUser));

            case "OR" :
                return ruleDTO.getRules()
                        .stream()
                        .anyMatch( rule -> evaluateRule(rule, dataFromUser));

            case "SIMPLE" :
                return evaluateSimpleRule(ruleDTO, dataFromUser);

            default:
                throw new IllegalArgumentException("Unsupported rule type: " + ruleDTO.getType());
        }
    }

    /**
     *  Evaluation of a leaf of the tree
     * 
     * @param rule
     * @param data
     * @return
     */
    private boolean evaluateSimpleRule(RuleDTO rule, Map<String, Object> data) {
        Object fieldValue = data.get(rule.getField());
        return applyOperator(fieldValue, rule.getOperator(), rule.getValue());
    }

    
    private boolean applyOperator(Object fieldValue, String operator, Object expectedValue) {
        if (fieldValue == null) {
            return false;
        }

        if (fieldValue instanceof Number && expectedValue instanceof Number) {
            double valueProvided = ((Number) fieldValue).doubleValue();
            double ruleValue = ((Number) expectedValue).doubleValue();
            switch (operator) {
                case ">=": return valueProvided >= ruleValue;
                case "<=": return valueProvided <= ruleValue;
                case ">": return valueProvided > ruleValue;
                case "<": return valueProvided < ruleValue;
                case "==": return valueProvided == ruleValue;
                case "!=": return valueProvided != ruleValue;

                default: throw new EvaluateException("Unsupported operator: " + operator);
            }
        } else {
            switch (operator) {
                case "==": return fieldValue.equals(expectedValue);
                case "!=": return !fieldValue.equals(expectedValue);

                default: throw new EvaluateException("Unsupported operator for type: " + operator);
            }
        }
    }

    private RuleDTO loadRuleAsDTO(Long ruleId) {
        RuleEntity ruleSaved = rulesRepository.findById(ruleId)
                .orElseThrow(() -> new RuleNotFoundException("Rule not found: " + ruleId));
        try {
            return mapper.readValue(ruleSaved.getRule_json(), RuleDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid rule JSON: " + ruleId, e);
        }
    }

    private void validateInput(Long ruleId, Map<String, Object> data) {
            if (ruleId <= 0 || data == null || data.isEmpty()) {
                throw new EvaluateException("Unknown rule or incorrect data.");
            }
        }

}

