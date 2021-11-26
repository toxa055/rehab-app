package com.rehab.service;

import com.rehab.config.BeansConfig;
import com.rehab.dto.CureDto;
import com.rehab.exception.ApplicationException;
import com.rehab.model.Cure;
import com.rehab.repository.CureCrudRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * Service class for Cure. It operates with Cure, CureDto
 * and contains methods that are considered as business logic.
 */
@Service
public class CureService {

    /**
     * CureCrudRepository bean.
     */
    private final CureCrudRepository cureCrudRepository;

    /**
     * ModelMapper bean.
     *
     * @see BeansConfig#modelMapper()
     */
    private final ModelMapper modelMapper;

    /**
     * Constructs new instance and initializes following fields.
     *
     * @param cureCrudRepository description of cureCrudRepository is in field declaration.
     * @param modelMapper        description of modelMapper is in field declaration.
     */
    @Autowired
    public CureService(CureCrudRepository cureCrudRepository, ModelMapper modelMapper) {
        this.cureCrudRepository = cureCrudRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Method returns only cureDto by given cure id.
     *
     * @param id cure id.
     * @return found cure mapped to cureDto.
     */
    public CureDto getById(int id) {
        return toDto(cureCrudRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Cure with id " + id + " not found.")));
    }

    /**
     * Method returns cureDto by given cure name.
     *
     * @param name cure name.
     * @return found cure mapped to cureDto.
     */
    public CureDto getByName(String name) {
        return toDto(cureCrudRepository.findByNameIgnoreCase(name).orElseThrow(() ->
                new NoSuchElementException("Cure with name " + name + " not found.")));
    }

    /**
     * Method maps given cureDto to cure and saves it.
     *
     * @param cureDto that will be saved.
     * @return saved cure mapped to cureDto.
     */
    public CureDto save(CureDto cureDto) {
        var name = cureDto.getName();
        if (cureCrudRepository.findByNameIgnoreCase(name).isPresent()) {
            throw new ApplicationException("Cure with name " + name + " already exists.");
        }
        return toDto(cureCrudRepository.save(toEntity(cureDto)));
    }

    /**
     * Method finds cures whose names contain value of nameLike and maps them to page of cureDto.
     *
     * @param nameLike not a particular cure name, but char sequence which cure names have to contain.
     * @param pageable interface that provides pagination.
     * @return page of all cures mapped to cureDto if nameLike is null or only found ones.
     */
    public Page<CureDto> filter(String nameLike, Pageable pageable) {
        return cureCrudRepository.filter(nameLike == null ? null : nameLike.strip().toLowerCase(), pageable)
                .map(this::toDto);
    }

    /**
     * Method maps (converts) given object of Cure class to object of CureDto class.
     *
     * @param cure object to map from Cure to CureDto.
     * @return mapped instance of CureDto class.
     */
    private CureDto toDto(Cure cure) {
        return modelMapper.map(cure, CureDto.class);
    }

    /**
     * Method maps (converts) given object of CureDto class to object of Cure class.
     *
     * @param cureDto object to map from CureDto to Cure.
     * @return mapped instance of Cure class.
     */
    private Cure toEntity(CureDto cureDto) {
        return modelMapper.map(cureDto, Cure.class);
    }
}
