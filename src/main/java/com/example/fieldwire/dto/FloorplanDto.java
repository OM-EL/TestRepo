package com.example.fieldwire.dto;

import lombok.Data;

@Data
public class FloorplanDto {
    private Long id;
    private String name;
    private String original;
    private String thumb;
    private String large;
    private Long projectId;
}
