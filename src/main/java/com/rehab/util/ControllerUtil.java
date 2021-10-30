package com.rehab.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

public class ControllerUtil {

    private ControllerUtil() {
    }

    public static Map<String, String> getErrorsMap(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(f -> f.getField() + "Error", FieldError::getDefaultMessage,
                        (m1, m2) -> String.join("<br>", m1, m2)));
    }
}
