package com.example.fieldwire.mapper;

import com.example.fieldwire.dto.ProjectDto;
import com.example.fieldwire.model.Project;


public interface ProjectMapper {
    ProjectDto toDto(Project project);
    Project toEntity(ProjectDto dto);
}
