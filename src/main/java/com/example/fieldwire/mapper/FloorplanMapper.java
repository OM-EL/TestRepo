package com.example.fieldwire.mapper;

import com.example.fieldwire.dto.FloorplanDto;
import com.example.fieldwire.model.Floorplan;


public interface FloorplanMapper {
    FloorplanDto toDto(Floorplan floorplan);

    Floorplan toEntity(FloorplanDto dto);
}
