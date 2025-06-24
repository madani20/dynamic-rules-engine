package org.mad.engine.models;

import java.util.List;
import java.util.Map;

public class AndRule extends CompositeRule{

    public AndRule(List<Rule> children) {
        super(children);
    }

    @Override
    public boolean evaluate(Map<String, Object> data) {
        for(Rule rule : children) {
            if (!rule.evaluate(data))
                return false;
        }
        return true;
    }
}
