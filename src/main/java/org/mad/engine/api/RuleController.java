package org.mad.engine.api;

import org.mad.engine.services.RuleEngineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/rules")
public class RuleController {
private static final Logger logger = LoggerFactory.getLogger(RuleController.class);

    private final RuleEngineService service;

    public RuleController(RuleEngineService service) {
        this.service = service;
    }


    @PostMapping("/{id}/evaluate")
    public boolean evaluate(@PathVariable Long id, @RequestBody Map<String, Object> data) throws Exception {
        logger.debug("Init evaluate from controller");

        boolean evaluation = service.evaluate(id, data);

        logger.debug("Evaluated from controller");
        return evaluation;
    }
}

