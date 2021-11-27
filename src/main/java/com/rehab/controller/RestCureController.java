package com.rehab.controller;

import com.rehab.dto.CureDto;
import com.rehab.service.CureService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for Cure.
 * It's used in some jsp pages, when we need to get or send data without page reload.
 * Any method returns execution result in JSON format in response body.
 */
@Secured("ROLE_DOCTOR")
@RestController
@RequestMapping("/rest/cures")
public class RestCureController {

    /**
     * CureService bean.
     */
    private final CureService cureService;

    /**
     * Interface that logs specific messages.
     */
    private final Logger logger;

    /**
     * @param cureService description of cureService is in field declaration.
     * @param logger      description of logger is in field declaration.
     */
    @Autowired
    public RestCureController(CureService cureService, Logger logger) {
        this.cureService = cureService;
        this.logger = logger;
    }

    /**
     * Method executes GET request to receive cureDto by given name.
     *
     * @param name that cure to get.
     * @return found cureDto.
     */
    @GetMapping("/{name}")
    public CureDto getByName(@PathVariable String name) {
        logger.info("Get cure by name {} through rest.", name);
        return cureService.getByName(name);
    }

    /**
     * Method executes POST request to create new cure.
     *
     * @param cureDto that will be saved.
     * @return saved cureDto.
     */
    @PostMapping()
    public CureDto create(CureDto cureDto) {
        logger.info("Create new cure through rest.");
        return cureService.save(cureDto);
    }
}
