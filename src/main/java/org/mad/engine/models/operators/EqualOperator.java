package org.mad.engine.models.operators;

public class EqualOperator implements OperatorStrategy {
    @Override
    public boolean evaluate(Object left, Object right) {
        return left.equals(right);
    }
}

