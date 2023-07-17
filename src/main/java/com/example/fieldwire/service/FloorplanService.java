package com.example.fieldwire.service;
import com.example.fieldwire.dto.FloorplanDto;
import com.example.fieldwire.mapper.FloorplanMapper;
import com.example.fieldwire.model.Floorplan;
import com.example.fieldwire.repository.FloorplanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FloorplanService {
    private final FloorplanRepository floorplanRepository;
    private final FloorplanMapper floorplanMapper;

    public FloorplanService(FloorplanRepository floorplanRepository, FloorplanMapper floorplanMapper) {
        this.floorplanRepository = floorplanRepository;
        this.floorplanMapper = floorplanMapper;
    }

    @Transactional(readOnly = true)
    public List<FloorplanDto> getAllFloorplans() {
        return floorplanRepository.findAll().stream()
                .map(floorplanMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FloorplanDto getFloorplanById(Long id) {
        Floorplan floorplan = floorplanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Floorplan not found"));
        return floorplanMapper.toDto(floorplan);
    }

    @Transactional
    public FloorplanDto createFloorplan(FloorplanDto floorplanDto) {
        Floorplan floorplan = floorplanMapper.toEntity(floorplanDto);
        floorplan = floorplanRepository.save(floorplan);
        return floorplanMapper.toDto(floorplan);
    }

    @Transactional
    public void deleteFloorplan(Long id) {
        floorplanRepository.deleteById(id);
    }

    @Transactional
    public FloorplanDto updateFloorplan(Long id, FloorplanDto floorplanDto) {
        Floorplan existingFloorplan = floorplanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Floorplan not found"));
        existingFloorplan.setName(floorplanDto.getName());
        existingFloorplan.setOriginal(floorplanDto.getOriginal());
        existingFloorplan.setThumb(floorplanDto.getThumb());
        existingFloorplan.setLarge(floorplanDto.getLarge());
        floorplanRepository.save(existingFloorplan);
        return floorplanMapper.toDto(existingFloorplan);
    }
}
