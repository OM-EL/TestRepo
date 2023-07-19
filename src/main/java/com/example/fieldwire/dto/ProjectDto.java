package com.example.fieldwire.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    private Long id;
    private String name;
    private List<Long> floorplans;
}
