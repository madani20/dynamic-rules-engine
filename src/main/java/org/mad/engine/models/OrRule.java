package org.mad.engine.models;

import java.util.List;
import java.util.Map;

public class OrRule extends CompositeRule{
    public OrRule(List<Rule> children) {
        super(children);
    }

    @Override
    public boolean evaluate(Map<String, Object> data) {
        for (Rule rule : children) {
            if (rule.evaluate(data))
                return true;
        }
        return false;
    }
}
