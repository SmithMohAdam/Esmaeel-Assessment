package com.esmaeel_essessment.service;

import com.esmaeel_essessment.model.Car;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CarSpecifications {
    public static Specification<Car> hasLength(Double length) {
        return (Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (length != null) {
                return builder.equal(root.get("length"), length);
            }
            return builder.conjunction();
        };
    }

    public static Specification<Car> hasWeight(Double weight) {
        return (Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (weight != null) {
                return builder.equal(root.get("weight"), weight);
            }
            return builder.conjunction();
        };
    }

    public static Specification<Car> hasVelocity(Double velocity) {
        return (Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (velocity != null) {
                return builder.equal(root.get("velocity"), velocity);
            }
            return builder.conjunction();
        };
    }

    public static Specification<Car> hasColor(String color) {
        return (Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (color != null && !color.isEmpty()) {
                return builder.equal(root.get("color"), color);
            }
            return builder.conjunction();
        };
    }

}
