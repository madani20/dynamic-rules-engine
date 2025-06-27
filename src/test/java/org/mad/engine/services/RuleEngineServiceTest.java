package org.mad.engine.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mad.engine.dto.ValidationResponseDTO;
import org.mad.engine.exceptions.RuleNotFoundException;
import org.mad.engine.exceptions.ValidateException;
import org.mad.engine.models.entities.RuleEntity;
import org.mad.engine.repositories.RuleRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class RuleEngineServiceTest {

    @Mock
    private RuleRepository ruleRepository;

    @InjectMocks
    private RuleEngineService ruleEngineService;

    private RuleEntity buildRuleEntity(String json, Long id) {
        RuleEntity entity = new RuleEntity();
        entity.setId(id);
        entity.setRule_json(json);
        return entity;
    }

    @Test
    @DisplayName("Validation of a valid SIMPLE rule")
    void testValidate_SimpleRuleValid() throws JsonProcessingException {
        // Arrange
        String jsonRule = """
        {
            "id": "rule-age",
            "type": "SIMPLE",
            "field": "age",
            "operator": ">=",
            "value": 18
        }
        """;
        RuleEntity entity = buildRuleEntity(jsonRule, 1L);

        Map<String, Object> data = Map.of("age", 20);

        Mockito.when(ruleRepository.findById(1L)).thenReturn(Optional.of(entity));

        // Act
        ValidationResponseDTO result = ruleEngineService.validate(1L, data);

        // Assert
        Assertions.assertTrue(result.isValid());
        Assertions.assertTrue(result.getFailedRules().isEmpty());
    }

    @Test
    @DisplayName("Validation of an invalid SIMPLE rule")
    void testValidate_SimpleRuleInvalid() throws JsonProcessingException {
        String jsonRule = """
        {
            "id": "rule-age",
            "type": "SIMPLE",
            "field": "age",
            "operator": ">=",
            "value": 18
        }
        """;
        RuleEntity entity = buildRuleEntity(jsonRule, 2L);

        Map<String, Object> data = Map.of("age", 16);

        Mockito.when(ruleRepository.findById(2L)).thenReturn(Optional.of(entity));

        ValidationResponseDTO result = ruleEngineService.validate(2L, data);

        Assertions.assertFalse(result.isValid());
        Assertions.assertTrue(result.getFailedRules().contains("rule-age"));
    }

    @Test
    @DisplayName("Should throw ValidateException when map contains null values")
    void testValidateInput_NullValueInMap() {
        Map<String, Object> dataWithNull = new HashMap<>();
        dataWithNull.put("age", 25);
        dataWithNull.put("income", null);

        Assertions.assertThrows(ValidateException.class, () -> {
            ruleEngineService.validate(7L, dataWithNull);
        });
    }


    @Test
    @DisplayName("Should throw ValidateException when a bad type in the data")
    void testValidateInput_ShouldReturnValidateExceptionForBadTypeInData() {
        String jsonRule = """
        {
            "id": "rule-age",
            "type": "SIMPLE",
            "field": "age",
            "operator": ">=",
            "value": 18
        },
            {
              "id": "rule-income",
              "type": "SIMPLE",
              "field": "income",
              "operator": ">=",
              "value": 1500
            }
        }
        """;
        RuleEntity entity = buildRuleEntity(jsonRule, 8L);
        Mockito.when(ruleRepository.findById(8L)).thenReturn(Optional.of(entity));
        Map<String, Object> dataWithNull = Map.of(
                "age", "25",
                "income", 15000
        );

        Assertions.assertThrows(ValidateException.class, () -> {
            ruleEngineService.validate(8L, dataWithNull);
        });
    }


    @Test
    @DisplayName("Validating a composite AND rule")
    void testValidate_CompositeAndRule() throws JsonProcessingException {
        String jsonRule = """
        {
          "id": "root",
          "type": "AND",
          "rules": [
            {
              "id": "rule-age",
              "type": "SIMPLE",
              "field": "age",
              "operator": ">=",
              "value": 18
            },
            {
              "id": "rule-income",
              "type": "SIMPLE",
              "field": "income",
              "operator": ">=",
              "value": 1500
            }
          ]
        }
        """;
        RuleEntity entity = buildRuleEntity(jsonRule, 3L);

        Map<String, Object> data = Map.of(
                "age", 20,
                "income", 1400
        );

        Mockito.when(ruleRepository.findById(3L)).thenReturn(Optional.of(entity));

        ValidationResponseDTO result = ruleEngineService.validate(3L, data);

        Assertions.assertFalse(result.isValid());
        Assertions.assertTrue(result.getFailedRules().contains("rule-income"));
    }

    @Test
    @DisplayName("Validating a composite OR rule")
    void testValidate_CompositeOrRule() throws JsonProcessingException {
        String jsonRule = """
        {
          "id": "root",
          "type": "OR",
          "rules": [
            {
              "id": "rule-age",
              "type": "SIMPLE",
              "field": "age",
              "operator": ">=",
              "value": 18
            },
            {
              "id": "rule-income",
              "type": "SIMPLE",
              "field": "income",
              "operator": ">=",
              "value": 1500
            }
          ]
        }
        """;
        RuleEntity entity = buildRuleEntity(jsonRule, 4L);

        Map<String, Object> data = Map.of(
                "age", 16,
                "income", 1800
        );

        Mockito.when(ruleRepository.findById(4L)).thenReturn(Optional.of(entity));

        ValidationResponseDTO result = ruleEngineService.validate(4L, data);

        Assertions.assertTrue(result.isValid());
        Assertions.assertTrue(result.getFailedRules().isEmpty());
    }

    @Test
    @DisplayName("Validating a composite AND rule within OR rule")
    void testValidate_CompositeAndRuleWithinOrRule() throws JsonProcessingException {
        String jsonRule = """
                {
                   "id": "root",
                   "type": "AND",
                   "rules": [
                     {
                       "id": "rule-age",
                       "type": "SIMPLE",
                       "field": "age",
                       "operator": ">=",
                       "value": 18
                     },
                     {
                       "id": "rule-OR",
                       "type": "OR",
                       "rules": [
                         {
                           "id": "rule-income",
                           "type": "SIMPLE",
                           "field": "income",
                           "operator": "<=",
                           "value": 25000
                         },
                         {
                           "id": "rule-student",
                           "type": "SIMPLE",
                           "field": "student",
                           "operator": "==",
                           "value": true
                         }
                       ]
                     }
                   ]
                 }
        """;
        RuleEntity entity = buildRuleEntity(jsonRule, 5L);

        Map<String, Object> data = Map.of(
                "age", 20,
                "income", 30000,
                "student", true
        );

        Mockito.when(ruleRepository.findById(5L)).thenReturn(Optional.of(entity));

        ValidationResponseDTO result = ruleEngineService.validate(5L, data);

        Assertions.assertTrue(result.isValid());
        Assertions.assertTrue(result.getFailedRules().isEmpty());
    }

    @Test
    @DisplayName("Should return RuleNotFoundException if rule not found")
    void testValidate_ShouldReturnRuleNotFoundException() {
        Mockito.when(ruleRepository.findById(99L)).thenReturn(Optional.empty());

        Map<String, Object> data = Map.of("age", 25);

        Assertions.assertThrows(RuleNotFoundException.class, () -> {
            ruleEngineService.validate(99L, data);
        });
    }
}
//Unknown rule or incorrect data.