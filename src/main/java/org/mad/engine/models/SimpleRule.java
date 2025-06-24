package org.mad.engine.models;

import org.mad.engine.models.operators.OperatorStrategy;

import java.util.Map;

public class SimpleRule implements Rule{
    private final String field;
    private final OperatorStrategy operator;
    private final Object value;

    public SimpleRule(String field, OperatorStrategy operator, Object value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }


    @Override
    public boolean evaluate(Map<String, Object> data) {
        Object fieldValue = data.get(field);

        return operator.evaluate(fieldValue, value);
    }
}
