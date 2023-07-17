package com.example.fieldwire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FloorplanDto {
    private Long id;
    private String name;
    private String original;
    private String thumb;
    private String large;
    private Long projectId;
}
