package com.example.fieldwire.service;

import com.example.fieldwire.dto.ProjectDto;
import com.example.fieldwire.mapper.ProjectMapper;
import com.example.fieldwire.model.Project;
import com.example.fieldwire.repository.FloorplanRepository;
import com.example.fieldwire.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    private final FloorplanRepository floorplanRepository;

    private final FloorplanService floorplanService;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper, FloorplanRepository floorplanRepository , FloorplanService floorplanService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.floorplanRepository = floorplanRepository;
        this.floorplanService = floorplanService;
    }

    @Transactional(readOnly = true)
    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ProjectDto getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return projectMapper.toDto(project);
    }

    @Transactional
    public ProjectDto createProject(ProjectDto projectDto) {
        Project project = projectMapper.toEntity(projectDto);
        if(projectRepository.findById(project.getId()).isPresent()) {
            throw new RuntimeException("id Already in use ");
        }
        project = projectRepository.save(project);
        return projectMapper.toDto(project);
    }

    @Transactional
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        project.getFloorplans().forEach( plan -> floorplanService.deleteFloorplan(plan.getId()) );
        projectRepository.deleteById(id);
    }

    @Transactional
    public ProjectDto updateProject(Long id, ProjectDto projectDto) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        existingProject.setName(projectDto.getName());
        projectRepository.save(existingProject);
        return projectMapper.toDto(existingProject);
    }
}
