package com.example.fieldwire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FloorplanDto {
    private UUID id;
    private String name;
    private String original;
    private String thumb;
    private String large;
    private UUID projectId;
}
