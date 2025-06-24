package org.mad.engine.models.chainOfResponsibility;

import java.util.Map;

public interface Handler {
    void handle(Map<String, Object> data);
    Handler setNext(Handler next);
}

