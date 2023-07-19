package com.example.fieldwire.service;
import com.example.fieldwire.dto.FloorplanDto;
import com.example.fieldwire.mapper.FloorplanMapper;
import com.example.fieldwire.model.Floorplan;
import com.example.fieldwire.repository.FloorplanRepository;
import com.example.fieldwire.repository.ProjectRepository;
import com.example.fieldwire.service.interfaces.CloudStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class FloorplanService {
    public static final String FLOORPLAN_NOT_FOUND = "Floorplan not found";
    private final FloorplanRepository floorplanRepository;
    private final FloorplanMapper floorplanMapper;

    private final ProjectRepository projectRepository;
    private final CloudStorageService cloudStorageService;

    public FloorplanService(ProjectRepository projectRepository , FloorplanRepository floorplanRepository, FloorplanMapper floorplanMapper, S3Service s3Service) {
        this.projectRepository = projectRepository;
        this.floorplanRepository = floorplanRepository;
        this.floorplanMapper = floorplanMapper;
        this.cloudStorageService = s3Service;
    }

    @Transactional(readOnly = true)
    public List<FloorplanDto> getAllFloorplans() {
        return floorplanRepository.findAll().stream()
                .map(floorplanMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public FloorplanDto getFloorplanById(UUID id) {
        Floorplan floorplan = floorplanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(FLOORPLAN_NOT_FOUND));
        return floorplanMapper.toDto(floorplan);
    }

    @Transactional
    public FloorplanDto createFloorplan(FloorplanDto floorplanDto , MultipartFile file) {
        if(floorplanDto.getId() != null && !projectRepository.existsById(floorplanDto.getProjectId())) {
            throw new IllegalArgumentException("Invalid project id");
        }
        String original = cloudStorageService.uploadFile(file);
        String thumb = cloudStorageService.getFileThumbUrl(file);
        String large = cloudStorageService.getFileLargeUrl(file);
        Floorplan floorplan = floorplanMapper.toEntity(floorplanDto);
        floorplan.setOriginal(original);
        floorplan.setThumb(thumb);
        floorplan.setLarge(large);
        floorplan = floorplanRepository.save(floorplan);
        return floorplanMapper.toDto(floorplan);
    }

    @Transactional
    public void deleteFloorplan(UUID id) {
        Floorplan floorplan = floorplanRepository.findById(id).orElseThrow(() -> new RuntimeException(FLOORPLAN_NOT_FOUND));
        cloudStorageService.deleteFile(floorplan.getOriginal());
        cloudStorageService.deleteFile(floorplan.getThumb());
        cloudStorageService.deleteFile(floorplan.getLarge());
        floorplanRepository.deleteById(id);
    }

    @Transactional
    public FloorplanDto updateFloorplan(UUID id, FloorplanDto floorplanDto , MultipartFile file) {
        Floorplan existingFloorplan = floorplanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(FLOORPLAN_NOT_FOUND));
        String original = cloudStorageService.uploadFile(file);
        String thumb = cloudStorageService.getFileThumbUrl(file);
        String large = cloudStorageService.getFileLargeUrl(file);
        existingFloorplan.setName(floorplanDto.getName());
        existingFloorplan.setOriginal(original);
        existingFloorplan.setThumb(thumb);
        existingFloorplan.setLarge(large);
        floorplanRepository.save(existingFloorplan);
        return floorplanMapper.toDto(existingFloorplan);
    }
}
