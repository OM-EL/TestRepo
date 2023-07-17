package com.example.fieldwire.controller;
import com.example.fieldwire.dto.FloorplanDto;
import com.example.fieldwire.service.FloorplanService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/floorplans")
public class FloorplanController {
    private final FloorplanService floorplanService;

    public FloorplanController(FloorplanService floorplanService) {
        this.floorplanService = floorplanService;
    }

    @GetMapping
    public List<FloorplanDto> getAllFloorplans() {
        return floorplanService.getAllFloorplans();
    }

    @GetMapping("/{id}")
    public FloorplanDto getFloorplanById(@PathVariable Long id) {
        return floorplanService.getFloorplanById(id);
    }

    @PostMapping
    public FloorplanDto createFloorplan(@RequestBody FloorplanDto floorplanDto) {
        return floorplanService.createFloorplan(floorplanDto);
    }

    @DeleteMapping("/{id}")
    public void deleteFloorplan(@PathVariable Long id) {
        floorplanService.deleteFloorplan(id);
    }

    @PatchMapping("/{id}")
    public FloorplanDto updateFloorplan(@PathVariable Long id, @RequestBody FloorplanDto floorplanDto) {
        return floorplanService.updateFloorplan(id, floorplanDto);
    }
}
