package com.example.fieldwire.mapper;

import com.example.fieldwire.dto.FloorplanDto;
import com.example.fieldwire.model.Floorplan;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface FloorplanMapper {
    FloorplanDto toDto(Floorplan floorplan);
    Floorplan toEntity(FloorplanDto dto);
}
