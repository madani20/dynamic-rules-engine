package org.mad.engine.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ValidationResponseDTO {

    private boolean valid;
    private List<String> failedRules = new ArrayList<>();

    public ValidationResponseDTO() {}

    public ValidationResponseDTO(boolean valid, List<String> failedRules) {
        this.valid = valid;
        this.failedRules = failedRules;
    }
}
