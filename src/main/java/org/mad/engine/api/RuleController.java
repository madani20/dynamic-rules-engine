package org.mad.engine.api;

import org.mad.engine.dto.ValidationResponseDTO;
import org.mad.engine.services.RuleEngineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ValidationResponseDTO> evaluate(@PathVariable Long id, @RequestBody Map<String, Object> data) {
        logger.debug("Init evaluate from controller");

        ValidationResponseDTO validationResponseDTO = service.validate(id, data);

        logger.debug("Evaluated from controller");
        return new ResponseEntity<>(validationResponseDTO, HttpStatus.valueOf(200));
    }
}

