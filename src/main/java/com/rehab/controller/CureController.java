package com.rehab.controller;

import com.rehab.dto.CureDto;
import com.rehab.service.CureService;
import com.rehab.util.ControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/cures")
@Secured({"ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_NURSE"})
public class CureController {

    private static final String NEW_CURE_URL = "/cures/new";
    private static final String CURE_URL = "/cures/cure";
    private static final String CURE = "cure";
    private final CureService cureService;

    @Autowired
    public CureController(CureService cureService) {
        this.cureService = cureService;
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        model.addAttribute(CURE, cureService.getById(id));
        return CURE_URL;
    }

    @GetMapping("/name/{name}")
    public String getByName(@PathVariable String name, Model model) {
        model.addAttribute(CURE, cureService.getByName(name));
        return CURE_URL;
    }

    @GetMapping("/new")
    public String create() {
        return NEW_CURE_URL;
    }

    @PostMapping("new")
    public String createCure(@Valid CureDto cureDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAllAttributes(ControllerUtil.getErrorsMap(bindingResult));
            model.addAttribute("name", cureDto.getName());
            model.addAttribute("cureType", cureDto.getCureType());
            return NEW_CURE_URL;
        }
        cureService.save(cureDto);
        return "redirect:";
    }

    @GetMapping
    public String cures(@PageableDefault(15) Pageable pageable, Model model) {
        model.addAttribute("page", cureService.getAll(pageable));
        return "/cures/list";
    }
}
