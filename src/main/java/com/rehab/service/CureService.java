package com.rehab.service;

import com.rehab.dto.CureDto;
import com.rehab.model.Cure;
import com.rehab.repository.CureCrudRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return toDto(cureCrudRepository.findById(id).get());
    }

    public CureDto getByName(String name) {
        return toDto(cureCrudRepository.getByNameIgnoreCase(name));
    }

    public CureDto save(CureDto cureDto) {
        return toDto(cureCrudRepository.save(toEntity(cureDto)));
    }

    public List<CureDto> getAll() {
        return cureCrudRepository
                .findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private CureDto toDto(Cure cure) {
        return modelMapper.map(cure, CureDto.class);
    }

    private Cure toEntity(CureDto cureDto) {
        return modelMapper.map(cureDto, Cure.class);
    }
}
