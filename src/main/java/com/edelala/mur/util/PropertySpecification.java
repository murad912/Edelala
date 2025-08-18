package com.edelala.mur.util;


import com.edelala.mur.entity.Property;
import com.edelala.mur.entity.PropertyType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal; // Import BigDecimal
import java.util.ArrayList;
import java.util.List;

public class PropertySpecification {

    // Specification for filtering by PropertyType
    public static Specification<Property> hasPropertyType(PropertyType propertyType) {
        return (root, query, criteriaBuilder) ->
                propertyType == null ? null : criteriaBuilder.equal(root.get("propertyType"), propertyType);
    }

    // Specification for filtering by City (case-insensitive, contains)
    public static Specification<Property> hasCity(String city) {
        return (root, query, criteriaBuilder) ->
                city == null || city.isEmpty() ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("city")), "%" + city.toLowerCase() + "%");
    }

    // Specification for filtering by Minimum Rent Price
    public static Specification<Property> hasMinRentPrice(BigDecimal minRentPrice) { // Changed to BigDecimal
        return (root, query, criteriaBuilder) ->
                minRentPrice == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("rentPrice"), minRentPrice);
    }

    // Specification for filtering by Maximum Rent Price
    public static Specification<Property> hasMaxRentPrice(BigDecimal maxRentPrice) { // Changed to BigDecimal
        return (root, query, criteriaBuilder) ->
                maxRentPrice == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("rentPrice"), maxRentPrice);
    }

    // Specification for filtering by Minimum Bedrooms
    public static Specification<Property> hasMinBedrooms(Integer minBedrooms) {
        return (root, query, criteriaBuilder) ->
                minBedrooms == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("bedrooms"), minBedrooms);
    }

    // Specification for filtering by Minimum Bathrooms
    public static Specification<Property> hasMinBathrooms(Double minBathrooms) {
        return (root, query, criteriaBuilder) ->
                minBathrooms == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("bathrooms"), minBathrooms);
    }

    // Specification for filtering by Keyword (in title or description, case-insensitive)
    public static Specification<Property> containsKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isEmpty()) {
                return null;
            }
            String lowerCaseKeyword = "%" + keyword.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), lowerCaseKeyword),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), lowerCaseKeyword)
            );
        };
    }

    // Specification for filtering by availability for rent
    public static Specification<Property> isAvailableForRent(Boolean availableForRent) {
        return (root, query, criteriaBuilder) ->
                availableForRent == null ? null : criteriaBuilder.equal(root.get("availableForRent"), availableForRent);
    }

    // Specification for filtering by availability for sale
    public static Specification<Property> isAvailableForSale(Boolean availableForSale) {
        return (root, query, criteriaBuilder) ->
                availableForSale == null ? null : criteriaBuilder.equal(root.get("availableForSale"), availableForSale);
    }

    // Combine multiple specifications using AND
    public static Specification<Property> withFilters(
            PropertyType propertyType,
            String city,
            BigDecimal minRentPrice, // Changed to BigDecimal
            BigDecimal maxRentPrice, // Changed to BigDecimal
            Integer minBedrooms,
            Double minBathrooms,
            String keyword,
            Boolean availableForRent,
            Boolean availableForSale
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (propertyType != null) {
                predicates.add(criteriaBuilder.equal(root.get("propertyType"), propertyType));
            }
            if (city != null && !city.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("city")), "%" + city.toLowerCase() + "%"));
            }
            if (minRentPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rentPrice"), minRentPrice));
            }
            if (maxRentPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("rentPrice"), maxRentPrice));
            }
            if (minBedrooms != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("bedrooms"), minBedrooms));
            }
            if (minBathrooms != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("bathrooms"), minBathrooms));
            }
            if (keyword != null && !keyword.isEmpty()) {
                String lowerCaseKeyword = "%" + keyword.toLowerCase() + "%";
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), lowerCaseKeyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), lowerCaseKeyword)
                ));
            }
            if (availableForRent != null && availableForSale != null) {
                if (availableForRent && availableForSale) {
                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("availableForRent"), true),
                            criteriaBuilder.equal(root.get("availableForSale"), true)
                    ));
                } else if (availableForRent) {
                    predicates.add(criteriaBuilder.equal(root.get("availableForRent"), true));
                } else if (availableForSale) {
                    predicates.add(criteriaBuilder.equal(root.get("availableForSale"), true));
                }
            } else if (availableForRent != null) {
                predicates.add(criteriaBuilder.equal(root.get("availableForRent"), availableForRent));
            } else if (availableForSale != null) {
                predicates.add(criteriaBuilder.equal(root.get("availableForSale"), availableForSale));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
//added luly 10
