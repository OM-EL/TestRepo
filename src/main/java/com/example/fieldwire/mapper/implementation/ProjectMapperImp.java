package com.example.fieldwire.mapper.implementation;

import com.example.fieldwire.dto.ProjectDto;
import com.example.fieldwire.mapper.ProjectMapper;
import com.example.fieldwire.model.Project;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
public class ProjectMapperImp implements ProjectMapper {
    @Override
    public ProjectDto toDto(Project project) {
        if (project == null) {
            return null;
        }

        ProjectDto dto = new ProjectDto();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setFloorplans(project.getFloorplans().stream().map(element -> element.getId()).toList());

        return dto;
    }

    @Override
    public Project toEntity(ProjectDto dto) {
        if (dto == null) {
            return null;
        }

        Project project = new Project();
        project.setId(dto.getId());
        project.setName(dto.getName());
        project.setFloorplans(new ArrayList<>());  // Assuming you want a new list here.

        return project;
    }
}
