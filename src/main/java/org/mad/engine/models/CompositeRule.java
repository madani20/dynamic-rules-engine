package org.mad.engine.models;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

public abstract class CompositeRule implements  Rule{
    protected List<Rule> children;


    public CompositeRule(List<Rule> children) {
        this.children = children;
    }

    @Override
    public boolean validate(Map<String, Object> data) {
        return false;
    }
}
