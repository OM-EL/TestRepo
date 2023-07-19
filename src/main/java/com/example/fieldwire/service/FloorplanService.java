package com.example.fieldwire.service;
import com.example.fieldwire.dto.FloorplanDto;
import com.example.fieldwire.mapper.FloorplanMapper;
import com.example.fieldwire.model.Floorplan;
import com.example.fieldwire.repository.FloorplanRepository;
import com.example.fieldwire.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FloorplanService {
    private final FloorplanRepository floorplanRepository;
    private final FloorplanMapper floorplanMapper;

    private final ProjectRepository projectRepository;
    private final S3Service s3Service;

    public FloorplanService(ProjectRepository projectRepository , FloorplanRepository floorplanRepository, FloorplanMapper floorplanMapper, S3Service s3Service) {
        this.projectRepository = projectRepository;
        this.floorplanRepository = floorplanRepository;
        this.floorplanMapper = floorplanMapper;
        this.s3Service = s3Service;
    }

    @Transactional(readOnly = true)
    public List<FloorplanDto> getAllFloorplans() {
        return floorplanRepository.findAll().stream()
                .map(floorplanMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public FloorplanDto getFloorplanById(Long id) {
        Floorplan floorplan = floorplanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Floorplan not found"));
        return floorplanMapper.toDto(floorplan);
    }

    @Transactional
    public FloorplanDto createFloorplan(FloorplanDto floorplanDto , MultipartFile file) {
        projectRepository.findById(floorplanDto.getProjectId()).orElseThrow(() -> new RuntimeException("Project not found"));
        String original = s3Service.uploadFile(file);
        String thumb = s3Service.getFileThumbUrl(file);
        String large = s3Service.getFileLargeUrl(file);
        Floorplan floorplan = floorplanMapper.toEntity(floorplanDto);
        floorplan.setOriginal(original);
        floorplan.setThumb(thumb);
        floorplan.setLarge(large);
        floorplan = floorplanRepository.save(floorplan);
        return floorplanMapper.toDto(floorplan);
    }

    @Transactional
    public void deleteFloorplan(Long id) {
        Floorplan floorplan = floorplanRepository.findById(id).orElseThrow(() -> new RuntimeException("Floorplan not found"));
        s3Service.deleteFile(floorplan.getOriginal());
        s3Service.deleteFile(floorplan.getThumb());
        s3Service.deleteFile(floorplan.getLarge());
        floorplanRepository.deleteById(id);
    }

    @Transactional
    public FloorplanDto updateFloorplan(Long id, FloorplanDto floorplanDto , MultipartFile file) {
        Floorplan existingFloorplan = floorplanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Floorplan not found"));
        String original = s3Service.uploadFile(file);
        String thumb = s3Service.getFileThumbUrl(file);
        String large = s3Service.getFileLargeUrl(file);
        existingFloorplan.setName(floorplanDto.getName());
        existingFloorplan.setOriginal(original);
        existingFloorplan.setThumb(thumb);
        existingFloorplan.setLarge(large);
        floorplanRepository.save(existingFloorplan);
        return floorplanMapper.toDto(existingFloorplan);
    }
}
