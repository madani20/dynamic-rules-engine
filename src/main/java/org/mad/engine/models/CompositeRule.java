package org.mad.engine.models;

import java.util.List;
import java.util.Map;

public abstract class CompositeRule implements  Rule{
    protected List<Rule> children;


    public CompositeRule(List<Rule> children) {
        this.children = children;
    }

    @Override
    public boolean evaluate(Map<String, Object> data) {
        return false;
    }
}
