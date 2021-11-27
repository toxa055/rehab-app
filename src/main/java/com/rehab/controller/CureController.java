package com.rehab.controller;

import com.rehab.dto.CureDto;
import com.rehab.service.CureService;
import com.rehab.util.ControllerUtil;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.lang.Nullable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller that executes http requests for Cure.
 * Any method returns name of jsp page to show it to user or redirects to another.
 * Almost any method adds to model execution result of cureService (as attribute).
 */
@Controller
@RequestMapping("/cures")
@Secured({"ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_NURSE"})
public class CureController {

    /**
     * Name of jsp page that contains form for creating new cure.
     */
    private static final String NEW_CURE_URL = "/cures/new";

    /**
     * Name of jsp page that shows only one cure.
     */
    private static final String CURE_URL = "/cures/cure";

    /**
     * Name of model attribute for Cure.
     */
    private static final String CURE = "cure";

    /**
     * CureService bean.
     */
    private final CureService cureService;

    /**
     * Interface that logs specific messages.
     */
    private final Logger logger;

    /**
     * Constructs new instance and initializes following fields.
     *
     * @param cureService description of cureService is in field declaration.
     * @param logger      description of logger is in field declaration.
     */
    @Autowired
    public CureController(CureService cureService, Logger logger) {
        this.cureService = cureService;
        this.logger = logger;
    }

    /**
     * Method executes GET request to receive cure by given id.
     *
     * @param id    cure id.
     * @param model holder for model attributes.
     * @return name of jsp page.
     */
    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        logger.info("Get cure by id {}.", id);
        model.addAttribute(CURE, cureService.getById(id));
        return CURE_URL;
    }

    /**
     * Method executes GET request to receive cure by given name.
     *
     * @param name  cure name.
     * @param model holder for model attributes.
     * @return name of jsp page.
     */
    @GetMapping("/name/{name}")
    public String getByName(@PathVariable String name, Model model) {
        logger.info("Get cure by name {}.", name);
        model.addAttribute(CURE, cureService.getByName(name));
        return CURE_URL;
    }

    /**
     * Method executes GET request to open jsp page where new cure will be created.
     *
     * @return name of jsp page.
     */
    @GetMapping("/new")
    public String create() {
        return NEW_CURE_URL;
    }

    /**
     * Method executes POST request to create new cure. Given cureDto is validated before saving.
     *
     * @param cureDto       that will be saved.
     * @param bindingResult holder for field errors.
     * @param model         holder for model attributes.
     * @return redirect to list of cures.
     */
    @PostMapping("new")
    public String createCure(@Valid CureDto cureDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.warn("Binding result for cure has errors: {}", bindingResult.getAllErrors());
            model.addAllAttributes(ControllerUtil.getErrorsMap(bindingResult));
            model.addAttribute("name", cureDto.getName());
            model.addAttribute("cureType", cureDto.getCureType());
            return NEW_CURE_URL;
        }
        logger.info("Create new cure.");
        cureService.save(cureDto);
        return "redirect:";
    }

    /**
     * Method executes GET request to receive list of cures with no filter.
     *
     * @return redirect to list of cures.
     */
    @GetMapping
    public String cures() {
        return "redirect:/cures/filter?nameLike=";
    }

    /**
     * Method executes GET request to receive list of cures filtered by given parameters.
     *
     * @param nameLike not a particular cure name, but char sequence which cure names have to contain.
     * @param pageable interface that provides pagination.
     * @param model    holder for model attributes.
     * @return name of jsp page.
     */
    @GetMapping("/filter")
    public String filter(@RequestParam @Nullable String nameLike, @PageableDefault(15) Pageable pageable, Model model) {
        logger.info("Filter cures by name like... {}.", nameLike);
        model.addAttribute("page", cureService.filter(nameLike, pageable));
        return "/cures/list";
    }
}
