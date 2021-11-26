package com.rehab.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class contains static utility method for getting Map collected from fields errors.
 * Instantiation of this class is prohibited.
 */
public class ControllerUtil {

    /**
     * Private empty constructor that indicates prohibition for creating instance of current class.
     */
    private ControllerUtil() {
    }

    /**
     * Method collects field names and field error messages in Map from given parameter bindingResult.
     *
     * @param bindingResult current binding result that contains field errors.
     * @return Map of pairs where key is field name and value is field error messages.
     */
    public static Map<String, String> getErrorsMap(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(f -> f.getField() + "Error", FieldError::getDefaultMessage,
                        (m1, m2) -> String.join("<br>", m1, m2)));
    }
}
