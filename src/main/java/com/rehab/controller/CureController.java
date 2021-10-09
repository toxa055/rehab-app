package com.rehab.controller;

import com.rehab.service.CureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cures")
public class CureController {

    private final CureService cureService;

    @Autowired
    public CureController(CureService cureService) {
        this.cureService = cureService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("cures", cureService.getAll());
        return "/cures/list";
    }
}
