package com.esmaeel_essessment.controller;

import com.esmaeel_essessment.dto.CriteriasResponse;
import com.esmaeel_essessment.exception.CarsNotFountException;
import com.esmaeel_essessment.model.Car;
import com.esmaeel_essessment.service.CarService;
import jakarta.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/search-form")
    public String criteriaOptions(Model model) {
        populateCriteriaOptions(model);
        return "search-form";
    }

    @GetMapping("/search")
    public String searchCars(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "length", required = false) Double length,
            @RequestParam(name = "weight", required = false) Double weight,
            @RequestParam(name = "velocity", required = false) Double velocity,
            @RequestParam(name = "color", required = false) String color,
            Model model) {

        try {
            Page<Car> carsPage = carService.searchCars(length, weight, velocity, color, PageRequest.of(page, 10));
            model.addAttribute("cars", carsPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", carsPage.getTotalPages());
            model.addAttribute("selectedLength", length);
            model.addAttribute("selectedWeight", weight);
            model.addAttribute("selectedVelocity", velocity);
            model.addAttribute("selectedColor", color);

        } catch (CarsNotFountException e) {
            model.addAttribute("errorMessage", "No cars found matching the criteria.");
        }

        populateCriteriaOptions(model);
        return "search-form";
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadCarsAsXml(
            @RequestParam(required = false) Double length,
            @RequestParam(required = false) Double weight,
            @RequestParam(required = false) Double velocity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String color
    ) throws JAXBException {

        Page<Car> cars = carService.searchCars(length, weight, velocity, color, PageRequest.of(page, size));
        String xmlContent = carService.carsToXml(cars.getContent());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cars.xml");
        byte[] xmlBytes = xmlContent.getBytes();
        return new ResponseEntity<>(xmlBytes, headers, HttpStatus.OK);
    }

    private void populateCriteriaOptions(Model model) {
        CriteriasResponse criteriasResponse = carService.criteriaOptions();
        model.addAttribute("lengths", criteriasResponse.getLengths());
        model.addAttribute("weights", criteriasResponse.getWeights());
        model.addAttribute("velocities", criteriasResponse.getVelocities());
        model.addAttribute("colors", criteriasResponse.getColors());
    }
}
