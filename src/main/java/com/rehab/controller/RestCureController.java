package com.rehab.controller;

import com.rehab.dto.CureDto;
import com.rehab.service.CureService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/cures")
public class RestCureController {

    private final CureService cureService;
    private final Logger logger;

    @Autowired
    public RestCureController(CureService cureService, Logger logger) {
        this.cureService = cureService;
        this.logger = logger;
    }

    @GetMapping("/{name}")
    public CureDto getByName(@PathVariable String name) {
        logger.info("Get cure by name {} through rest.", name);
        return cureService.getByName(name);
    }

    @PostMapping()
    public CureDto create(CureDto cureDto) {
        logger.info("Create new cure through rest.");
        return cureService.save(cureDto);
    }
}
