package com.rehab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller that executes http requests for starting and login pages.
 * Any method returns name of jsp page to show it to user.
 */
@Controller
public class MainController {

    /**
     * Method executes GET request to open starting jsp page.
     *
     * @return name of jsp page.
     */
    @GetMapping("/")
    public String home() {
        return "home";
    }

    /**
     * Method executes GET request to open login jsp page.
     *
     * @return name of jsp page.
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
