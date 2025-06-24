package org.mad.engine.models.chainOfResponsibility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class LoggingHandler extends AbstractHandler {
    private static final Logger logger = LoggerFactory.getLogger(LoggingHandler.class);

    @Override
    protected void process(Map<String, Object> data) {
        logger.info("Processing data: {}", data);
    }

}

