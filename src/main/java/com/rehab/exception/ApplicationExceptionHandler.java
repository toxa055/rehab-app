package com.rehab.exception;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Specific class for handling exceptions during the application working.
 */
@ControllerAdvice
public class ApplicationExceptionHandler {

    /**
     * Interface that logs specific messages.
     */
    private final Logger logger;

    /**
     * Constructs current instance and initializes following fields.
     *
     * @param logger description of logger is in field declaration.
     */
    @Autowired
    public ApplicationExceptionHandler(Logger logger) {
        this.logger = logger;
    }

    /**
     * Handle specific exceptions that are listed in @ExceptionHandler
     *
     * @param ex    exception that has been thrown and handled.
     * @param model holder for model attributes.
     * @return name of jsp page that will be shown for user.
     */
    @ExceptionHandler({ApplicationException.class, IllegalArgumentException.class, NoSuchElementException.class})
    public String handleException(Exception ex, Model model) {
        var message = ex.getMessage();
        logger.error(message);
        model.addAttribute("exception", message);
        return "error";
    }

    /**
     * Handle specific exceptions that are listed in @ExceptionHandler
     *
     * @param ex exception that has been thrown and handled.
     * @return name of jsp page that will be shown for user.
     */
    @ExceptionHandler({Exception.class})
    public String handleAnyException(Exception ex) {
        var cause = ex.getCause();
        logger.error(Arrays.toString(cause == null ? ex.getStackTrace() : cause.getStackTrace()));
        return "error";
    }
}
