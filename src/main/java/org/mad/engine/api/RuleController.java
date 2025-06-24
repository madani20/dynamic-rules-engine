package org.mad.engine.api;

import org.mad.engine.services.RuleEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/rules")
public class RuleController {

    private final RuleEngineService service;

    public RuleController(RuleEngineService service) {
        this.service = service;
    }


    @PostMapping("/{id}/evaluate")
    public boolean evaluate(@PathVariable Long id, @RequestBody Map<String, Object> data) throws Exception {
        return service.evaluate(id, data);
    }
}

