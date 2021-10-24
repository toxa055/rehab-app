package com.rehab.controller;

import com.rehab.dto.CureDto;
import com.rehab.service.CureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/cures")
public class RestCureController {

    private final CureService cureService;

    @Autowired
    public RestCureController(CureService cureService) {
        this.cureService = cureService;
    }

    @GetMapping("/{name}")
    public CureDto getByName(@PathVariable String name) {
        return cureService.getByName(name);
    }

    @PostMapping()
    public CureDto create(CureDto cureDto) {
        return cureService.save(cureDto);
    }
}
