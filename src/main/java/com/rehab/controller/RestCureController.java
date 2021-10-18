package com.rehab.controller;

import com.rehab.dto.CureDto;
import com.rehab.service.CureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestCureController {

    private final CureService cureService;

    @Autowired
    public RestCureController(CureService cureService) {
        this.cureService = cureService;
    }

    @RequestMapping("/rest/cures/{name}")
    public CureDto getByName(@PathVariable String name) {
        return cureService.getByName(name);
    }
}
