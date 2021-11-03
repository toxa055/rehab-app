package com.rehab.service;

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

@Service
public class CureService {

    private final CureCrudRepository cureCrudRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CureService(CureCrudRepository cureCrudRepository, ModelMapper modelMapper) {
        this.cureCrudRepository = cureCrudRepository;
        this.modelMapper = modelMapper;
    }

    public CureDto getById(int id) {
        return toDto(cureCrudRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Cure with id " + id + " not found.")));
    }

    public CureDto getByName(String name) {
        return toDto(cureCrudRepository.getByNameIgnoreCase(name).orElseThrow(() ->
                new NoSuchElementException("Cure with name " + name + " not found.")));
    }

    public CureDto save(CureDto cureDto) {
        if (cureCrudRepository.getByNameIgnoreCase(cureDto.getName()).isEmpty()) {
            return toDto(cureCrudRepository.save(toEntity(cureDto)));
        } else {
            throw new ApplicationException("Cure with name " + cureDto.getName() + " already exists.");
        }
    }

    public Page<CureDto> getAll(Pageable pageable) {
        return cureCrudRepository.findAll(pageable).map(this::toDto);
    }

    private CureDto toDto(Cure cure) {
        return modelMapper.map(cure, CureDto.class);
    }

    private Cure toEntity(CureDto cureDto) {
        return modelMapper.map(cureDto, Cure.class);
    }
}
