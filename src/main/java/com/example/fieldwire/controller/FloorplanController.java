package com.example.fieldwire.controller;
import com.example.fieldwire.dto.FloorplanDto;
import com.example.fieldwire.service.FloorplanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/floorplans")
public class FloorplanController {

    private final FloorplanService floorplanService;

    public FloorplanController(FloorplanService floorplanService) {
        this.floorplanService = floorplanService;
    }

    @GetMapping
    public ResponseEntity<List<FloorplanDto>> getAllFloorplans() {
        return new ResponseEntity<>(floorplanService.getAllFloorplans(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FloorplanDto> getFloorplanById(@PathVariable Long id) {
        return new ResponseEntity<>(floorplanService.getFloorplanById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FloorplanDto> createFloorplan(@RequestBody FloorplanDto floorplanDto) {
        return new ResponseEntity<>(floorplanService.createFloorplan(floorplanDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFloorplan(@PathVariable Long id) {
        floorplanService.deleteFloorplan(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FloorplanDto> updateFloorplan(@PathVariable Long id, @RequestBody FloorplanDto floorplanDto) {
        return new ResponseEntity<>(floorplanService.updateFloorplan(id, floorplanDto), HttpStatus.OK);
    }
}
