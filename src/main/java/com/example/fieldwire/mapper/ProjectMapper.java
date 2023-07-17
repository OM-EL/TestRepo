package com.example.fieldwire.mapper;

import com.example.fieldwire.dto.ProjectDto;
import com.example.fieldwire.model.Project;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ProjectMapper {
    ProjectDto toDto(Project project);
    Project toEntity(ProjectDto dto);
}
