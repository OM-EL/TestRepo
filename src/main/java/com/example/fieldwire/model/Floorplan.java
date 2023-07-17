package com.example.fieldwire.model;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Floorplan {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;
    private String original;
    private String thumb;
    private String large;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public Floorplan() {
    }
}
