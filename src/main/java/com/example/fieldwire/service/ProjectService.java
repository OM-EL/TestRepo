package com.example.fieldwire.service;

import com.example.fieldwire.dto.ProjectDto;
import com.example.fieldwire.mapper.ProjectMapper;
import com.example.fieldwire.model.Project;
import com.example.fieldwire.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;


@Service
public class ProjectService {
    public static final String PROJECT_WITH_ID = "Project with ID ";
    private final ProjectRepository projectRepository;

    private final FloorplanService floorplanService;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper, FloorplanService floorplanService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.floorplanService = floorplanService;
    }

    @Transactional(readOnly = true)
    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ProjectDto getProjectById(UUID id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(PROJECT_WITH_ID + id + " not found"));
        return projectMapper.toDto(project);
    }

    @Transactional
    public ProjectDto createProject(ProjectDto projectDto) {
        Project project = projectMapper.toEntity(projectDto);

        if( project.getId() != null ) {
            throw new RuntimeException(" you can not provide an ID when creating the Project" );
        }

        project = projectRepository.save(project);
        return projectMapper.toDto(project);
    }

    @Transactional
    public void deleteProject(UUID id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException(PROJECT_WITH_ID + id + " not found"));
        project.getFloorplans().forEach( plan -> floorplanService.deleteFloorplan(plan.getId()) );
        projectRepository.deleteById(id);
    }

    @Transactional
    public ProjectDto updateProject(UUID id, ProjectDto projectDto) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(PROJECT_WITH_ID + id + " not found"));
        existingProject.setName(projectDto.getName());
        projectRepository.save(existingProject);
        return projectMapper.toDto(existingProject);
    }
}
