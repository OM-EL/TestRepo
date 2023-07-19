package com.example.fieldwire.model;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Floorplan> floorplans;

}
