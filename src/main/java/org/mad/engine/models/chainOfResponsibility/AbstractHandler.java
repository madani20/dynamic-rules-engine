package org.mad.engine.models.chainOfResponsibility;

import java.util.Map;

public abstract class AbstractHandler implements Handler {
    private Handler next;

    @Override
    public Handler setNext(Handler next) {
        this.next = next;
        return next;
    }

    @Override
    public void handle(Map<String, Object> data) {
        process(data);
        if (next != null) {
            next.handle(data);
        }
    }

    protected abstract void process(Map<String, Object> data);
}

