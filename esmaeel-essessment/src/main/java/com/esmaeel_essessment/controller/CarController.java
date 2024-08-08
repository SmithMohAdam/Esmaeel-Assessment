package com.esmaeel_essessment.controller;



import com.esmaeel_essessment.dto.CriteriasResponse;
import com.esmaeel_essessment.model.Car;
import com.esmaeel_essessment.service.CarService;
import jakarta.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Controller
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/search-form")
    public String criteriaOptions(Model model){
        CriteriasResponse criteriasResponse = carService.criteriaOptions();
        model.addAttribute("lengths", criteriasResponse.getLengths());
        model.addAttribute("weights", criteriasResponse.getWeights());
        model.addAttribute("velocities",criteriasResponse.getVelocities() );
        model.addAttribute("colors", criteriasResponse.getColors());
        return "search-form";
    }

    @PostMapping("/search")
    public String searchCars(
            @RequestParam(required = false) Double length,
            @RequestParam(required = false) Double weight,
            @RequestParam(required = false) Double velocity,
            @RequestParam(required = false) String color,Model model
            ) {
        List<Car> cars = carService.searchCars(length, weight, velocity, color);
        model.addAttribute("cars", cars);
        model.addAttribute("selectedLength", length);
        model.addAttribute("selectedWeight", weight);
        model.addAttribute("selectedVelocity", velocity);
        model.addAttribute("selectedColor", color);
        return "search-form";
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadCarsAsXml(
            @RequestParam(required = false) Double length,
            @RequestParam(required = false) Double weight,
            @RequestParam(required = false) Double velocity,
            @RequestParam(required = false) String color) throws JAXBException {

        List<Car> cars = carService.searchCars(length, weight, velocity, color);
        String xmlContent = carService.carsToXml(cars);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cars.xml");
        byte[] xmlBytes = xmlContent.getBytes();
        return new ResponseEntity<>(xmlBytes, headers, HttpStatus.OK);
    }
}
