package org.mad.engine.models;

import java.util.Map;

public interface Rule {

    boolean evaluate (Map<String, Object> data);
}
