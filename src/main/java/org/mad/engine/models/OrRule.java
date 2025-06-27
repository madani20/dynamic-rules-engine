package org.mad.engine.models;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

public class OrRule extends CompositeRule{
    public OrRule(List<Rule> children) {
        super(children);
    }

    @Override
    public boolean validate(Map<String, Object> data) {
        for (Rule rule : children) {
            if (rule.validate(data))
                return true;
        }
        return false;
    }
}
