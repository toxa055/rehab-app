package com.rehab.controller;

import com.rehab.dto.CureDto;
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

    @GetMapping(value = "/getBy", params = "id")
    public String getById(@RequestParam int id, Model model) {
        model.addAttribute("cure", cureService.getById(id));
        return "/cures/cure";
    }

    @GetMapping(value = "getBy", params = "name")
    public String getByName(@RequestParam String name, Model model) {
        model.addAttribute("cure", cureService.getByName(name));
        return "cures/cure";
    }

    @GetMapping("/new")
    public String create() {
        return "/cures/new";
    }

    @PostMapping("new")
    public String createCure(CureDto cureDto) {
        cureService.save(cureDto);
        return "redirect:";
    }

    @GetMapping
    public String cures(Model model) {
        model.addAttribute("cures", cureService.getAll());
        return "/cures/list";
    }
}
