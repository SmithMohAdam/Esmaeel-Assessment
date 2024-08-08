package com.esmaeel_essessment.repository;

import com.esmaeel_essessment.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car,Long> , JpaSpecificationExecutor<Car> {


    @Query("SELECT DISTINCT c.length FROM Car c WHERE c.length IS NOT NULL")
    List<Double> findDistinctLengths();

    @Query("SELECT DISTINCT c.weight FROM Car c WHERE c.weight IS NOT NULL")
    List<Double> findDistinctWeights();

    @Query("SELECT DISTINCT c.velocity FROM Car c WHERE c.velocity IS NOT NULL")
    List<Double> findDistinctVelocities();

    @Query("SELECT DISTINCT c.color FROM Car c WHERE c.color IS NOT NULL")
    List<String> findDistinctColors();
}
