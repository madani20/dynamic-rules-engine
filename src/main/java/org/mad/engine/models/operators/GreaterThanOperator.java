package org.mad.engine.models.operators;

public class GreaterThanOperator implements OperatorStrategy {
    @Override
    public boolean evaluate(Object left, Object right) {
        return ((Number) left).doubleValue() > ((Number) right).doubleValue();
    }
}

