package com.esmaeel_essessment.service;

import com.esmaeel_essessment.dto.CriteriasResponse;
import com.esmaeel_essessment.exception.CarsNotFountException;
import com.esmaeel_essessment.model.Car;

import com.esmaeel_essessment.repository.CarRepository;
import com.esmaeel_essessment.util.XmlUtil;

import jakarta.xml.bind.JAXBException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;



import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;


    public CriteriasResponse criteriaOptions(){
        return mapToCriteriasResponse(
                carRepository.findDistinctLengths(),
                carRepository.findDistinctWeights(),
                carRepository.findDistinctVelocities(),
                carRepository.findDistinctColors()
        );
    }

    public Page<Car> searchCars(Double length, Double weight, Double velocity, String color, Pageable pageable) throws CarsNotFountException {
        Specification<Car> spec = Specification.where(CarSpecifications.hasLength(length))
                .and(CarSpecifications.hasWeight(weight))
                .and(CarSpecifications.hasVelocity(velocity))
                .and(CarSpecifications.hasColor(color));
        Page<Car> resultPage = carRepository.findAll(spec, pageable);

        if (resultPage.isEmpty()) {
            throw new CarsNotFountException("No cars found matching the criteria.");
        }

        return resultPage;


    }
    public List<Car> downloadCars(Double length, Double weight, Double velocity, String color) throws CarsNotFountException {
        Specification<Car> spec = Specification.where(CarSpecifications.hasLength(length))
                .and(CarSpecifications.hasWeight(weight))
                .and(CarSpecifications.hasVelocity(velocity))
                .and(CarSpecifications.hasColor(color));
        return carRepository.findAll(spec);
    }

    public String carsToXml(List<Car> cars) throws JAXBException {
        return XmlUtil.convertToXml(cars);
    }


    private CriteriasResponse mapToCriteriasResponse(List<Double> lengths,List<Double> weight,List<Double> velocities,List<String> colors){
        return CriteriasResponse.builder()
                .lengths(lengths)
                .weights(weight)
                .velocities(velocities)
                .colors(colors)
                .build();
    }



}
