package org.mad.engine.models;

import java.util.Map;

public interface Rule {

    boolean validate(Map<String, Object> data);
}
