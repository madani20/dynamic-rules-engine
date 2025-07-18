package org.mad.engine.dto;

import lombok.Getter;
import lombok.Setter;
import org.mad.engine.models.types.RuleType;

import java.util.List;

@Getter
@Setter
public class RuleDTO {
    private String id;
    private RuleType type;    // AND, OR, SIMPLE
    private String field;   // Pour SIMPLE
    private String operator;// Pour SIMPLE
    private Object value;   // Pour SIMPLE
    private List<RuleDTO> rules;    // Pour AND/OR

    public RuleDTO() {
    }
}

