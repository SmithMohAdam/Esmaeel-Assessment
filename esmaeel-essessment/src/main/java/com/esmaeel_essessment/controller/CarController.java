package com.esmaeel_essessment.controller;


import com.esmaeel_essessment.dto.CriteriasResponse;
import com.esmaeel_essessment.model.Car;
import com.esmaeel_essessment.service.CarService;
import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
//@Controller
@RequestMapping("/api/cars")
@Slf4j
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public CriteriasResponse criteriaOptions(){
        return carService.criteriaOptions();
    }

    @PostMapping("/search")
    public List<Car> searchCars(
            @RequestParam(required = false) Double length,
            @RequestParam(required = false) Double weight,
            @RequestParam(required = false) Double velocity,
            @RequestParam(required = false) String color
            ) {


        return carService.searchCars(length, weight, velocity, color);
    }

    @GetMapping("/download")
    public ResponseEntity<String> downloadCarsAsXml(
            @RequestParam(required = false) Double length,
            @RequestParam(required = false) Double weight,
            @RequestParam(required = false) Double velocity,
            @RequestParam(required = false) String color) throws JAXBException {

        List<Car> cars = carService.searchCars(length, weight, velocity, color);
        String xmlContent = carService.carsToXml(cars);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cars.xml");

        return new ResponseEntity<>(xmlContent, headers, HttpStatus.OK);
    }
}
