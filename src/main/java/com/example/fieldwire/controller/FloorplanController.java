package com.example.fieldwire.controller;
import com.example.fieldwire.dto.FloorplanDto;
import com.example.fieldwire.service.FloorplanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/v1/floorplans")
public class FloorplanController {

    private final FloorplanService floorplanService;

    public FloorplanController(FloorplanService floorplanService) {
        this.floorplanService = floorplanService;
    }

    @GetMapping
    public ResponseEntity<List<FloorplanDto>> getAllFloorplans() {
        return ResponseEntity.ok(floorplanService.getAllFloorplans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FloorplanDto> getFloorplanById(@PathVariable UUID id) {
        return ResponseEntity.ok(floorplanService.getFloorplanById(id));
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<FloorplanDto> createFloorplan(@RequestPart("floorplan") FloorplanDto floorplanDto,
                                                        @RequestPart("file") MultipartFile file) {
        return new ResponseEntity<>(floorplanService.createFloorplan(floorplanDto, file), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFloorplan(@PathVariable UUID id) {
        floorplanService.deleteFloorplan(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FloorplanDto> updateFloorplan(@PathVariable UUID id,
                                                        @RequestPart("floorplan") FloorplanDto floorplanDto,
                                                        @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(floorplanService.updateFloorplan(id, floorplanDto, file));
    }
}
