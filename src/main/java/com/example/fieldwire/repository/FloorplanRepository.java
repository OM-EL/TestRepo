package com.example.fieldwire.repository;
import com.example.fieldwire.model.Floorplan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FloorplanRepository extends JpaRepository<Floorplan, Long> {
}
