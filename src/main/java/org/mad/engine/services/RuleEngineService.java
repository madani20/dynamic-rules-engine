package org.mad.engine.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mad.engine.dto.RuleDTO;
import org.mad.engine.dto.ValidationResponseDTO;
import org.mad.engine.exceptions.ValidateException;
import org.mad.engine.exceptions.RuleNotFoundException;
import org.mad.engine.models.entities.RuleEntity;
import org.mad.engine.models.types.RuleType;
import org.mad.engine.repositories.RuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;


@Service
public class RuleEngineService {
    private static final Logger logger = LoggerFactory.getLogger(RuleEngineService.class);

    private final RuleRepository rulesRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    public RuleEngineService(RuleRepository repo) {
        this.rulesRepository = repo;
    }

    /**
     *      Main method for rule validation
     *
     * @param ruleId
     * @param data
     * @return
     */
    public ValidationResponseDTO validate(Long ruleId, Map<String, Object> data) {
        logger.debug("Init validate from service");

        validateInput(ruleId, data);

        // Récupération de la règle en BDD
        RuleDTO rootRuleDTO = loadRuleAsDTO(ruleId);

        // Évaluation des données avec la règle racine
        ValidationResponseDTO validation = validateRule(rootRuleDTO, data);

        logger.debug("Validation completed. Result: {}", validation.isValid());
        return validation;
    }



    //================================== [ Utilitaires ] ===========================================================================

    /**
     *  Validate rules recursively
     *
     * @param ruleDTO
     * @param dataFromUser
     * @return
     */
    private ValidationResponseDTO validateRule(RuleDTO ruleDTO, Map<String, Object> dataFromUser) {
        ValidationResponseDTO validationResponseDTO = new ValidationResponseDTO();
        validationResponseDTO.setValid(true);

        switch (ruleDTO.getType()) {
            case AND:
                for (RuleDTO subRule : ruleDTO.getRules()) {
                    ValidationResponseDTO subValidationDTO = validateRule(subRule, dataFromUser);
                    if (!subValidationDTO.isValid()) {
                        validationResponseDTO.getFailedRules().addAll(subValidationDTO.getFailedRules());
                        validationResponseDTO.setValid(false);
                    }
                }
                break;

            case OR:
                boolean anyValid = false;
                for (RuleDTO subRule : ruleDTO.getRules()) {
                    ValidationResponseDTO subValidationDTO = validateRule(subRule, dataFromUser);
                    if (subValidationDTO.isValid()) {
                        anyValid = true;
                    } else {
                        validationResponseDTO.getFailedRules().addAll(subValidationDTO.getFailedRules());
                    }
                }
                validationResponseDTO.setValid(anyValid);
                if (anyValid) {
                    validationResponseDTO.getFailedRules().clear(); // si OR validé, on efface les échecs
                }
                break;

            case SIMPLE:
                boolean valid = validateSimpleRule(ruleDTO, dataFromUser);
                validationResponseDTO.setValid(valid);
                if (!valid) {
                    validationResponseDTO.getFailedRules().add(ruleDTO.getId());
                }
                break;

            default:
                throw new ValidateException("Unsupported rule type: " + ruleDTO.getType());
        }
        return validationResponseDTO;
    }

     /**
     *  Evaluation of a leaf of the tree
     * 
     * @param rule
     * @param data
     * @return
     */
    private boolean validateSimpleRule(RuleDTO rule, Map<String, Object> data) {
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
            return switch (operator) {
                case ">=" -> valueProvided >= ruleValue;
                case "<=" -> valueProvided <= ruleValue;
                case ">" -> valueProvided > ruleValue;
                case "<" -> valueProvided < ruleValue;
                case "==" -> valueProvided == ruleValue;
                case "!=" -> valueProvided != ruleValue;
                default -> throw new ValidateException("Unsupported operator: " + operator);
            };
        } else {
            return switch (operator) {
                case "==" -> fieldValue.equals(expectedValue);
                case "!=" -> !fieldValue.equals(expectedValue);
                default -> throw new ValidateException("Unsupported operator for type: " + operator);
            };
        }
    }

    private RuleDTO loadRuleAsDTO(Long ruleId) {
        RuleEntity ruleSaved = rulesRepository.findById(ruleId)
                .orElseThrow(() -> new RuleNotFoundException("Rule not found: " + ruleId));
        try {
            RuleDTO ruleDTO = mapper.readValue(ruleSaved.getRule_json(), RuleDTO.class);
            if (ruleDTO == null) {
                throw new RuntimeException("Parsed RuleDTO is null for rule ID: " + ruleId);
            }
            return ruleDTO;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid rule JSON: " + ruleId, e);
        }
    }

    private void validateInput(Long ruleId, Map<String, Object> data) {
        if (ruleId == null || ruleId <= 0) {
            throw new ValidateException("Invalid rule ID.");
        }

        if (data == null || data.isEmpty()) {
            throw new ValidateException("Data map is null or empty.");
        }
        boolean hasNullValues = data.values().stream().anyMatch(Objects::isNull);
        if (hasNullValues) {
            throw new ValidateException("Data contains null values.");
        }
    }

}

