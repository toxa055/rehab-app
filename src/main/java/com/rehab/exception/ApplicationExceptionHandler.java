package com.rehab.exception;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ApplicationExceptionHandler {

    private final Logger logger;

    @Autowired
    public ApplicationExceptionHandler(Logger logger) {
        this.logger = logger;
    }

    @ExceptionHandler({ApplicationException.class, IllegalArgumentException.class, NoSuchElementException.class})
    public String handleException(Exception ex, Model model) {
        var message = ex.getMessage();
        logger.error(message);
        model.addAttribute("exception", message);
        return "error";
    }

    @ExceptionHandler({Exception.class})
    public String handleAnyException(Exception ex) {
        var cause = ex.getCause();
        logger.error(Arrays.toString(cause == null ? ex.getStackTrace() : cause.getStackTrace()));
        return "error";
    }
}
