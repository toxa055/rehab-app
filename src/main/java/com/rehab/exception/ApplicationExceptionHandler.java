package com.rehab.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler({ApplicationException.class, IllegalArgumentException.class, NoSuchElementException.class})
    public String handleException(Exception ex, Model model) {
        model.addAttribute("exception", ex.getMessage());
        return "error";
    }
}
