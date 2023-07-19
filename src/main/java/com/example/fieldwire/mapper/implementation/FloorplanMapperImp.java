package com.example.fieldwire.mapper.implementation;

import com.example.fieldwire.dto.FloorplanDto;
import com.example.fieldwire.mapper.FloorplanMapper;
import com.example.fieldwire.model.Floorplan;
import com.example.fieldwire.model.Project;
import org.springframework.stereotype.Component;

@Component
public class FloorplanMapperImp implements FloorplanMapper {
    @Override
    public FloorplanDto toDto(Floorplan floorplan) {
        if (floorplan == null) {
            return null;
        }

        FloorplanDto dto = new FloorplanDto();
        dto.setId(floorplan.getId());
        dto.setName(floorplan.getName());
        dto.setOriginal(floorplan.getOriginal());
        dto.setThumb(floorplan.getThumb());
        dto.setLarge(floorplan.getLarge());

        if (floorplan.getProject() != null) {
            dto.setProjectId(floorplan.getProject().getId());
        }

        return dto;
    }

    @Override
    public Floorplan toEntity(FloorplanDto dto) {
        if (dto == null) {
            return null;
        }

        Floorplan floorplan = new Floorplan();
        floorplan.setId(dto.getId());
        floorplan.setName(dto.getName());
        floorplan.setOriginal(dto.getOriginal());
        floorplan.setThumb(dto.getThumb());
        floorplan.setLarge(dto.getLarge());

        if (dto.getProjectId() != null) {
            Project project = new Project();
            project.setId(dto.getProjectId());
            floorplan.setProject(project);
        }

        return floorplan;
    }
}

