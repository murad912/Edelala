package com.edelala.mur.service;

import com.edelala.mur.entity.Image;
import com.edelala.mur.entity.Property;
import com.edelala.mur.entity.PropertyType;
import com.edelala.mur.entity.User;
import com.edelala.mur.dto.PropertyDTO;

import com.edelala.mur.exception.ResourceNotFoundException;
import com.edelala.mur.repo.ImageRepository;
import com.edelala.mur.repo.PropertyRepository;
import com.edelala.mur.repo.UserRepository;
import com.edelala.mur.util.ImageUtils;
import com.edelala.mur.util.PropertySpecification;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



//@Service
//@RequiredArgsConstructor
//public class PropertyService {
//
//    private final PropertyRepository propertyRepository;
//    private final ImageRepository imageRepository;
//
//    private Property mapToEntity(PropertyDTO dto, Property property) {
//        property.setTitle(dto.getTitle());
//        property.setDescription(dto.getDescription());
//        property.setAddress(dto.getAddress());
//        property.setCity(dto.getCity());
//        property.setState(dto.getState());
//        property.setZipCode(dto.getZipCode());
//        property.setPropertyType(PropertyType.valueOf(dto.getPropertyType().name()));
//        property.setBedrooms(dto.getBedrooms());
//        property.setBathrooms(dto.getBathrooms());
//        property.setRentPrice(dto.getRentPrice());
//        property.setSalePrice(dto.getSalePrice());
//        property.setAvailable(dto.isAvailable());
//        property.setAvailableForRent(dto.isAvailableForRent());
//        property.setAvailableForSale(dto.isAvailableForSale());
//        return property;
//    }
//
//    @Transactional
//    public Property save(Property property) {
//        return propertyRepository.save(property);
//    }
//
//    public List<Property> findAllProperties(String location, Integer bedrooms) {
//        Specification<Property> spec = Specification.where(null);
//
//        if (location != null && !location.isEmpty()) {
//            spec = spec.and(PropertySpecifications.hasCityOrAddressContaining(location));
//        }
//
//        if (bedrooms != null) {
//            spec = spec.and(PropertySpecifications.hasBedrooms(bedrooms));
//        }
//
//        return propertyRepository.findAll(spec);
//    }
//
//    @Transactional(readOnly = true)
//    public Page<Property> getFilteredProperties(
//            String propertyType,
//            String city,
//            BigDecimal minRent,
//            BigDecimal maxRent,
//            Pageable pageable) {
//
//        Specification<Property> spec = Specification.where(null);
//        spec = spec.and(PropertySpecifications.isAvailableForRent());
//
//        if (propertyType != null && !propertyType.isEmpty()) {
//            try {
//                PropertyType type = PropertyType.valueOf(propertyType.toUpperCase());
//                spec = spec.and(PropertySpecifications.hasPropertyType(type));
//            } catch (IllegalArgumentException e) {
//                System.err.println("Invalid PropertyType string provided: " + propertyType);
//            }
//        }
//
//        if (city != null && !city.isEmpty()) {
//            spec = spec.and(PropertySpecifications.hasCityOrAddressContaining(city));
//        }
//
//        if (minRent != null) {
//            spec = spec.and(PropertySpecifications.hasMinRentPrice(minRent));
//        }
//
//        if (maxRent != null) {
//            spec = spec.and(PropertySpecifications.hasMaxRentPrice(maxRent));
//        }
//
//        return propertyRepository.findAll(spec, pageable);
//    }
//
//    @Transactional
//    public Property listNewProperty(PropertyDTO propertyDTO, List<MultipartFile> images, User owner) throws IOException {
//        Property property = new Property();
//        property.setOwner(owner);
//        mapToEntity(propertyDTO, property);
//
//        Property savedProperty = propertyRepository.save(property);
//
//        if (images != null && !images.isEmpty()) {
//            for (MultipartFile file : images) {
//                String originalFilename = file.getOriginalFilename();
//                String fileExtension = "";
//                if (originalFilename != null && originalFilename.contains(".")) {
//                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
//                }
//                String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
//
//                ImageUtils.saveImageToFileSystem(file, uniqueFileName);
//
//                Image image = new Image();
//                image.setProperty(savedProperty);
//                image.setImageUrl("/uploads/" + uniqueFileName);
//                imageRepository.save(image);
//            }
//        }
//        return savedProperty;
//    }
//
//    @Transactional(readOnly = true)
//    public Optional<Property> getPropertyById(Long id) {
//        Optional<Property> propertyOptional = propertyRepository.findById(id);
//        propertyOptional.ifPresent(p -> {
//            // FIX: Added debug logging for property owner ID
//            System.out.println("PropertyService Debug: Fetched Property ID: " + p.getId() + ", Title: " + p.getTitle() +
//                    ", Owner ID from DB: " + (p.getOwner() != null ? p.getOwner().getId() : "NULL"));
//        });
//        return propertyOptional;
//    }
//
//    @Transactional(readOnly = true)
//    public List<Property> getPropertiesByOwner(User owner) {
//        return propertyRepository.findByOwner(owner);
//    }
//
//    @Transactional
//    public Property updateProperty(Long propertyId, PropertyDTO propertyDTO, User owner, List<MultipartFile> newImages) throws IOException {
//        Property existingProperty = propertyRepository.findById(propertyId)
//                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + propertyId));
//
//        // Ownership check: crucial for security, ensuring only the owner can update their property
//        // FIX: Added debug logging for owner IDs in backend update method
//        System.out.println("PropertyService Debug: Attempting to update Property ID: " + propertyId +
//                ", Authenticated User ID: " + owner.getId() +
//                ", Existing Property Owner ID: " + existingProperty.getOwner().getId());
//
//        if (!existingProperty.getOwner().getId().equals(owner.getId())) {
//            throw new RuntimeException("You do not have permission to update this property.");
//        }
//
//        mapToEntity(propertyDTO, existingProperty);
//
//        if (newImages != null && !newImages.isEmpty()) {
//            for (MultipartFile file : newImages) {
//                String originalFilename = file.getOriginalFilename();
//                String fileExtension = "";
//                if (originalFilename != null && originalFilename.contains(".")) {
//                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
//                }
//                String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
//                ImageUtils.saveImageToFileSystem(file, uniqueFileName);
//
//                Image newImage = new Image();
//                newImage.setProperty(existingProperty);
//                newImage.setImageUrl("/uploads/" + uniqueFileName);
//                imageRepository.save(newImage);
//            }
//        }
//
//        return propertyRepository.save(existingProperty);
//    }
//
//    @Transactional
//    public void deleteProperty(Long propertyId, User owner) {
//        Property property = propertyRepository.findById(propertyId)
//                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + propertyId));
//
//        if (!property.getOwner().getId().equals(owner.getId())) {
//            throw new RuntimeException("You do not have permission to delete this property.");
//        }
//
//        if (property.getImages() != null) {
//            for (Image image : property.getImages()) {
//                try {
//                    String filename = image.getImageUrl().substring(image.getImageUrl().lastIndexOf("/") + 1);
//                    ImageUtils.deleteImageFromFileSystem(filename);
//                } catch (IOException e) {
//                    System.err.println("Could not delete image file: " + image.getImageUrl() + " - " + e.getMessage());
//                }
//            }
//            imageRepository.deleteAll(property.getImages());
//        }
//        propertyRepository.deleteById(propertyId);
//    }
//
//    @Transactional
//    public Property markAsRented(Long id, User owner) {
//        Property property = getPropertyById(id)
//                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + id));
//
//        if (!property.getOwner().getId().equals(owner.getId())) {
//            throw new RuntimeException("You do not have permission to modify this property.");
//        }
//
//        property.setAvailableForRent(false);
//        property.setAvailableForSale(false);
//        property.setAvailable(false);
//        return propertyRepository.save(property);
//    }
//
//    @Transactional
//    public Property markAsAvailableForRent(Long id, User owner) {
//        Property property = getPropertyById(id)
//                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + id));
//
//        if (!property.getOwner().getId().equals(owner.getId())) {
//            throw new RuntimeException("You do not have permission to modify this property.");
//        }
//
//        property.setAvailableForRent(true);
//        property.setAvailable(true);
//        return propertyRepository.save(property);
//    }
//
//    @Transactional
//    public Property markAsAvailableForSale(Long id, User owner) {
//        Property property = getPropertyById(id)
//                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + id));
//
//        if (!property.getOwner().getId().equals(owner.getId())) {
//            throw new RuntimeException("You do not have permission to modify this property.");
//        }
//
//        property.setAvailableForSale(true);
//        property.setAvailable(true);
//        return propertyRepository.save(property);
//    }
//
//    @Transactional
//    public Property markAsUnavailable(Long id, User owner) {
//        Property property = getPropertyById(id)
//                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + id));
//
//        if (!property.getOwner().getId().equals(owner.getId())) {
//            throw new RuntimeException("You do not have permission to modify this property.");
//        }
//
//        property.setAvailable(false);
//        property.setAvailableForRent(false);
//        property.setAvailableForSale(false);
//        return propertyRepository.save(property);
//    }
//
//    static class PropertySpecifications {
//        public static Specification<Property> isAvailable() {
//            return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("available"));
//        }
//
//        public static Specification<Property> isAvailableForRent() {
//            return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("availableForRent"));
//        }
//
//        public static Specification<Property> isAvailableForSale() {
//            return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("availableForSale"));
//        }
//
//        public static Specification<Property> hasCityOrAddressContaining(String location) {
//            return (root, query, criteriaBuilder) ->
//                    criteriaBuilder.or(
//                            criteriaBuilder.like(criteriaBuilder.lower(root.get("city")), "%" + location.toLowerCase() + "%"),
//                            criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%" + location.toLowerCase() + "%")
//                    );
//        }
//
//        public static Specification<Property> hasBedrooms(Integer bedrooms) {
//            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("bedrooms"), bedrooms);
//        }
//
//        public static Specification<Property> hasPropertyType(PropertyType propertyType) {
//            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("propertyType"), propertyType);
//        }
//
//        public static Specification<Property> hasMinRentPrice(BigDecimal minRent) {
//            return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("rentPrice"), minRent);
//        }
//
//        public static Specification<Property> hasMaxRentPrice(BigDecimal maxRent) {
//            return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("rentPrice"), maxRent);
//        }
//    }
//}
////I comment the code to improve owner dashboard jun 24

//@Service
//@RequiredArgsConstructor
//public class PropertyService {
//
//    private static final Logger logger = LoggerFactory.getLogger(PropertyService.class);
//
//    private final PropertyRepository propertyRepository;
//    private final ImageRepository imageRepository;
//    private final UserRepository userRepository;
//
//    @Value("${upload.dir}")
//    private String uploadDir;
//
//    private Property mapToEntity(PropertyDTO dto, Property property) {
//        property.setTitle(dto.getTitle());
//        property.setDescription(dto.getDescription());
//        property.setAddress(dto.getAddress());
//        property.setCity(dto.getCity());
//        property.setState(dto.getState());
//        property.setZipCode(dto.getZipCode());
//        property.setPropertyType(PropertyType.valueOf(dto.getPropertyType().name()));
//        property.setBedrooms(dto.getBedrooms());
//        property.setBathrooms(dto.getBathrooms());
//        property.setRentPrice(dto.getRentPrice());
//        property.setSalePrice(dto.getSalePrice());
//        property.setAvailable(dto.isAvailable());
//        property.setAvailableForRent(dto.isAvailableForRent());
//        property.setAvailableForSale(dto.isAvailableForSale());
//        return property;
//    }
//
//    @Transactional
//    public Property save(Property property) {
//        return propertyRepository.save(property);
//    }
//
//    public List<Property> findAllProperties(String location, Integer bedrooms) {
//        Specification<Property> spec = Specification.where(null);
//
//        if (location != null && !location.isEmpty()) {
//            spec = spec.and(PropertySpecifications.hasCityOrAddressContaining(location));
//        }
//
//        if (bedrooms != null) {
//            spec = spec.and(PropertySpecifications.hasBedrooms(bedrooms));
//        }
//
//        return propertyRepository.findAll(spec);
//    }
//
//    @Transactional(readOnly = true)
//    public Page<Property> getFilteredProperties(
//            String propertyType,
//            String city,
//            BigDecimal minRent,
//            BigDecimal maxRent,
//            Pageable pageable) {
//
//        PropertyType typeEnum = null;
//        if (propertyType != null && !propertyType.isEmpty()) {
//            try {
//                typeEnum = PropertyType.valueOf(propertyType.toUpperCase());
//            } catch (IllegalArgumentException e) {
//                logger.warn("Invalid PropertyType string provided for public filter: {}", propertyType);
//                return Page.empty(pageable);
//            }
//        }
//
//        return propertyRepository.findFilteredProperties(
//                typeEnum, city, minRent, maxRent, pageable);
//    }
//
//    @Transactional
//    public Property listNewProperty(PropertyDTO propertyDTO, List<MultipartFile> images, User owner) throws IOException {
//        Property property = new Property();
//        property.setOwner(owner);
//        mapToEntity(propertyDTO, property);
//
//        Property savedProperty = propertyRepository.save(property);
//
//        if (images != null && !images.isEmpty()) {
//            for (MultipartFile file : images) {
//                String originalFilename = file.getOriginalFilename();
//                String fileExtension = "";
//                if (originalFilename != null && originalFilename.contains(".")) {
//                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
//                }
//                String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
//
//                ImageUtils.saveImageToFileSystem(file, uniqueFileName);
//
//                Image image = new Image();
//                image.setProperty(savedProperty);
//                image.setImageUrl("/uploads/" + uniqueFileName);
//                imageRepository.save(image);
//            }
//        }
//        return savedProperty;
//    }
//
//    @Transactional(readOnly = true)
//    public Optional<Property> getPropertyById(Long id) {
//        Optional<Property> propertyOptional = propertyRepository.findById(id);
//        propertyOptional.ifPresent(p -> {
//            logger.debug("PropertyService Debug: Fetched Property ID: {}, Title: {}, Owner ID from DB: {}",
//                    p.getId(), p.getTitle(), (p.getOwner() != null ? p.getOwner().getId() : "NULL"));
//        });
//        return propertyOptional;
//    }
//
//    @Transactional(readOnly = true)
//    public Page<Property> getPropertiesByOwner(User owner, Boolean available, Boolean availableForRent, Boolean availableForSale, String propertyType, Pageable pageable) {
//        logger.debug("PropertyService: Received Pageable for owner properties: {}", pageable);
//        logger.debug("PropertyService: Pageable Sort for owner properties: {}", pageable.getSort());
//
//        Specification<Property> spec = (root, query, cb) -> cb.equal(root.get("owner"), owner);
//
//        if (available != null) {
//            spec = spec.and(PropertySpecifications.isAvailable(available));
//        }
//        if (availableForRent != null) {
//            spec = spec.and(PropertySpecifications.isAvailableForRent(availableForRent));
//        }
//        if (availableForSale != null) {
//            spec = spec.and(PropertySpecifications.isAvailableForSale(availableForSale));
//        }
//        if (propertyType != null && !propertyType.isEmpty()) {
//            try {
//                PropertyType type = PropertyType.valueOf(propertyType.toUpperCase());
//                spec = spec.and(PropertySpecifications.hasPropertyType(type));
//            } catch (IllegalArgumentException e) {
//                logger.warn("Invalid property type in getPropertiesByOwner: {}", propertyType);
//                return Page.empty(pageable);
//            }
//        }
//
//        logger.debug("PropertyService: Calling repository.findAll(spec, pageable) for owner properties.");
//        return propertyRepository.findAll(spec, pageable);
//    }
//
//    @Transactional
//    public Property updateProperty(Long propertyId, PropertyDTO propertyDTO, User owner, List<MultipartFile> newImages) throws IOException {
//        Property existingProperty = propertyRepository.findById(propertyId)
//                .orElseThrow(() -> new ResourceNotFoundException("Property not found with ID: " + propertyId));
//
//        // Ownership check: crucial for security, ensuring only the owner can update their property
//        logger.debug("PropertyService Debug: Attempting to update Property ID: {}", propertyId);
//        logger.debug("PropertyService Debug: Authenticated User ID (from JWT/SecurityContext): {}", owner.getId());
//        logger.debug("PropertyService Debug: Existing Property Owner ID (from Database): {}", existingProperty.getOwner().getId());
//
//
//        if (!existingProperty.getOwner().getId().equals(owner.getId())) {
//            logger.warn("PropertyService Warning: User {} attempted to update property {} but is not the owner (Property owned by {}).",
//                    owner.getId(), propertyId, existingProperty.getOwner().getId());
//            throw new RuntimeException("You do not have permission to update this property.");
//        }
//
//        mapToEntity(propertyDTO, existingProperty);
//
//        if (newImages != null && !newImages.isEmpty()) {
//            for (MultipartFile file : newImages) {
//                String originalFilename = file.getOriginalFilename();
//                String fileExtension = "";
//                if (originalFilename != null && originalFilename.contains(".")) {
//                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
//                }
//                String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
//                ImageUtils.saveImageToFileSystem(file, uniqueFileName);
//
//                Image newImage = new Image();
//                newImage.setProperty(existingProperty);
//                newImage.setImageUrl("/uploads/" + uniqueFileName);
//                imageRepository.save(newImage);
//            }
//        }
//
//        return propertyRepository.save(existingProperty);
//    }
//
//    @Transactional
//    public void deleteProperty(Long propertyId, User owner) {
//        Property property = propertyRepository.findById(propertyId)
//                .orElseThrow(() -> new ResourceNotFoundException("Property not found with ID: " + propertyId));
//
//        if (!property.getOwner().getId().equals(owner.getId())) {
//            throw new RuntimeException("You do not have permission to delete this property.");
//        }
//
//        if (property.getImages() != null) {
//            for (Image image : property.getImages()) {
//                try {
//                    String filename = image.getImageUrl().substring(image.getImageUrl().lastIndexOf("/") + 1);
//                    ImageUtils.deleteImageFromFileSystem(filename);
//                } catch (IOException e) {
//                    logger.error("Could not delete image file: {} - {}", image.getImageUrl(), e.getMessage(), e);
//                }
//            }
//            imageRepository.deleteAll(property.getImages());
//        }
//        propertyRepository.deleteById(propertyId);
//    }
//
//    @Transactional
//    public Property markAsRented(Long id, User owner) {
//        Property property = getPropertyById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Property not found with ID: " + id));
//
//        if (!property.getOwner().getId().equals(owner.getId())) {
//            throw new RuntimeException("You do not have permission to modify this property.");
//        }
//
//        property.setAvailableForRent(false);
//        property.setAvailableForSale(false);
//        property.setAvailable(false);
//        return propertyRepository.save(property);
//    }
//
//    @Transactional
//    public Property markAsAvailableForRent(Long id, User owner) {
//        Property property = getPropertyById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Property not found with ID: " + id));
//
//        if (!property.getOwner().getId().equals(owner.getId())) {
//            throw new RuntimeException("You do not have permission to modify this property.");
//        }
//
//        property.setAvailableForRent(true);
//        property.setAvailable(true);
//        return propertyRepository.save(property);
//    }
//
//    @Transactional
//    public Property markAsAvailableForSale(Long id, User owner) {
//        Property property = getPropertyById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Property not found with ID: " + id));
//
//        if (!property.getOwner().getId().equals(owner.getId())) {
//            throw new RuntimeException("You do not have permission to modify this property.");
//        }
//
//        property.setAvailableForSale(true);
//        property.setAvailable(true);
//        return propertyRepository.save(property);
//    }
//
//    @Transactional
//    public Property markAsUnavailable(Long id, User owner) {
//        Property property = getPropertyById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Property not found with ID: " + id));
//
//        if (!property.getOwner().getId().equals(owner.getId())) {
//            throw new RuntimeException("You do not have permission to modify this property.");
//        }
//
//        property.setAvailable(false);
//        property.setAvailableForRent(false);
//        property.setAvailableForSale(false);
//        return propertyRepository.save(property);
//    }
//
//    // Inner class for Specifications - updated to accept boolean values
//    static class PropertySpecifications {
//        public static Specification<Property> isAvailable(Boolean available) {
//            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("available"), available);
//        }
//
//        public static Specification<Property> isAvailableForRent(Boolean availableForRent) {
//            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("availableForRent"), availableForRent);
//        }
//
//        public static Specification<Property> isAvailableForSale(Boolean availableForSale) {
//            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("availableForSale"), availableForSale);
//        }
//
//        public static Specification<Property> hasCityOrAddressContaining(String location) {
//            return (root, query, criteriaBuilder) ->
//                    criteriaBuilder.or(
//                            criteriaBuilder.like(criteriaBuilder.lower(root.get("city")), "%" + location.toLowerCase() + "%"),
//                            criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%" + location.toLowerCase() + "%")
//                    );
//        }
//
//        public static Specification<Property> hasBedrooms(Integer bedrooms) {
//            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("bedrooms"), bedrooms);
//        }
//
//        public static Specification<Property> hasPropertyType(PropertyType propertyType) {
//            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("propertyType"), propertyType);
//        }
//
//        public static Specification<Property> hasMinRentPrice(BigDecimal minRent) {
//            return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("rentPrice"), minRent);
//        }
//
//        public static Specification<Property> hasMaxRentPrice(BigDecimal maxRent) {
//            return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("rentPrice"), maxRent);
//        }
//    }
//}

// ******************* comment to improve filter july 10 ***********************


import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

//@Service
//@RequiredArgsConstructor
//public class PropertyService {
//
//    private static final Logger logger = LoggerFactory.getLogger(PropertyService.class);
//    private final PropertyRepository propertyRepository;
//    private final UserRepository userRepository;
//    private final ImageRepository imageRepository;
//
//    private final String UPLOAD_DIR = "uploads/";
//
//    @Transactional
//    public PropertyDTO createProperty(PropertyDTO propertyDTO, List<MultipartFile> images, User owner) throws IOException {
//        logger.debug("PropertyService Debug: Creating property for owner: {}", owner.getEmail());
//
//        Property property = new Property();
//        BeanUtils.copyProperties(propertyDTO, property);
//        property.setOwner(owner);
//        property.setAvailable(true);
//        property.setAvailableForRent(propertyDTO.getAvailableForRent() != null ? propertyDTO.getAvailableForRent() : false);
//        property.setAvailableForSale(propertyDTO.getAvailableForSale() != null ? propertyDTO.getAvailableForSale() : false);
//
//        Property savedProperty = propertyRepository.save(property);
//        logger.debug("PropertyService Debug: Property saved with ID: {}", savedProperty.getId());
//
//        if (images != null && !images.isEmpty()) {
//            Path uploadPath = Paths.get(UPLOAD_DIR);
//            if (!Files.exists(uploadPath)) {
//                Files.createDirectories(uploadPath);
//            }
//
//            for (MultipartFile file : images) {
//                if (!file.isEmpty()) {
//                    String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
//                    Path filePath = uploadPath.resolve(fileName);
//                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//                    Image image = new Image();
//                    image.setImageUrl("/uploads/" + fileName);
//                    image.setProperty(savedProperty);
//                    imageRepository.save(image);
//                    logger.debug("PropertyService Debug: Saved image: {}", image.getImageUrl());
//                }
//            }
//        }
//        return convertToDTO(savedProperty);
//    }
//
//    @Transactional(readOnly = true)
//    public Page<PropertyDTO> getAllProperties(
//            int page,
//            int size,
//            String sortBy,
//            String sortDir,
//            String propertyType,
//            String city,
//            BigDecimal minRent,
//            BigDecimal maxRent,
//            Integer minBedrooms,
//            Double minBathrooms,
//            String keyword,
//            Boolean availableForRent,
//            Boolean availableForSale
//    ) {
//        Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
//        Pageable pageable = PageRequest.of(page, size, sort);
//
//        Specification<Property> spec = Specification.where(null);
//
//        if (propertyType != null && !propertyType.isEmpty()) {
//            try {
//                PropertyType typeEnum = PropertyType.valueOf(propertyType.toUpperCase());
//                spec = spec.and(PropertySpecification.hasPropertyType(typeEnum));
//            } catch (IllegalArgumentException e) {
//                logger.warn("Invalid property type provided: {}", propertyType);
//            }
//        }
//        if (city != null && !city.isEmpty()) {
//            spec = spec.and(PropertySpecification.hasCity(city));
//        }
//        if (minRent != null) {
//            spec = spec.and(PropertySpecification.hasMinRentPrice(minRent));
//        }
//        if (maxRent != null) {
//            spec = spec.and(PropertySpecification.hasMaxRentPrice(maxRent));
//        }
//        if (minBedrooms != null) {
//            spec = spec.and(PropertySpecification.hasMinBedrooms(minBedrooms));
//        }
//        if (minBathrooms != null) {
//            spec = spec.and(PropertySpecification.hasMinBathrooms(minBathrooms));
//        }
//        if (keyword != null && !keyword.isEmpty()) {
//            spec = spec.and(PropertySpecification.containsKeyword(keyword));
//        }
//        if (availableForRent != null || availableForSale != null) {
//            spec = spec.and(PropertySpecification.withFilters(
//                    null, null, null, null, null, null, null,
//                    availableForRent, availableForSale
//            ));
//        }
//
//        Page<Property> propertiesPage = propertyRepository.findAll(spec, pageable);
//        return propertiesPage.map(this::convertToDTO);
//    }
//
//
//    @Transactional(readOnly = true)
//    public PropertyDTO getPropertyById(Long id) {
//        logger.debug("PropertyService Debug: Fetching property with ID: {}", id);
//        Property property = propertyRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));
//        return convertToDTO(property);
//    }
//
//    @Transactional
//    public PropertyDTO updateProperty(Long id, PropertyDTO propertyDTO, List<MultipartFile> newImages, User authenticatedUser) throws IOException {
//        logger.debug("PropertyService Debug: Attempting to update Property ID: {}", id);
//
//        Property existingProperty = propertyRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));
//
//        if (!Objects.equals(existingProperty.getOwner().getId(), authenticatedUser.getId())) {
//            logger.warn("PropertyService Warning: User {} attempted to update property {} but is not the owner (Property owned by {}).",
//                    authenticatedUser.getId(), id, existingProperty.getOwner().getId());
//            throw new RuntimeException("You do not have permission to update this property.");
//        }
//
//        logger.debug("PropertyService Debug: Authenticated User ID (from JWT/SecurityContext): {}", authenticatedUser.getId());
//        logger.debug("PropertyService Debug: Existing Property Owner ID (from Database): {}", existingProperty.getOwner().getId());
//
//        // Use propertyDTO.getAvailable(), etc., as they are now Boolean wrapper types
//        BeanUtils.copyProperties(propertyDTO, existingProperty, "id", "owner", "images");
//        existingProperty.setAvailable(propertyDTO.getAvailable() != null ? propertyDTO.getAvailable() : false);
//        existingProperty.setAvailableForRent(propertyDTO.getAvailableForRent() != null ? propertyDTO.getAvailableForRent() : false);
//        existingProperty.setAvailableForSale(propertyDTO.getAvailableForSale() != null ? propertyDTO.getAvailableForSale() : false);
//
//
//        if (newImages != null && !newImages.isEmpty()) {
//            Path uploadPath = Paths.get(UPLOAD_DIR);
//            if (!Files.exists(uploadPath)) {
//                Files.createDirectories(uploadPath);
//            }
//
//            for (MultipartFile file : newImages) {
//                if (!file.isEmpty()) {
//                    String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
//                    Path filePath = uploadPath.resolve(fileName);
//                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//                    Image image = new Image();
//                    image.setImageUrl("/uploads/" + fileName);
//                    image.setProperty(existingProperty);
//                    imageRepository.save(image);
//                    logger.debug("PropertyService Debug: Saved new image: {}", image.getImageUrl());
//                }
//            }
//        }
//
//        Property updatedProperty = propertyRepository.save(existingProperty);
//        logger.debug("PropertyService Debug: Property ID {} updated successfully.", updatedProperty.getId());
//        return convertToDTO(updatedProperty);
//    }
//
//    @Transactional
//    public void deleteProperty(Long id, User authenticatedUser) {
//        logger.debug("PropertyService Debug: Attempting to delete Property ID: {}", id);
//        Property property = propertyRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));
//
//        if (!Objects.equals(property.getOwner().getId(), authenticatedUser.getId())) {
//            logger.warn("PropertyService Warning: User {} attempted to delete property {} but is not the owner (Property owned by {}).",
//                    authenticatedUser.getId(), id, property.getOwner().getId());
//            throw new RuntimeException("You do not have permission to delete this property.");
//        }
//
//        property.getImages().forEach(image -> {
//            Path imagePath = Paths.get(UPLOAD_DIR).resolve(Paths.get(image.getImageUrl()).getFileName().toString());
//            try {
//                Files.deleteIfExists(imagePath);
//                logger.debug("PropertyService Debug: Deleted image file: {}", imagePath);
//            } catch (IOException e) {
//                logger.warn("PropertyService Warning: Could not delete image file {}: {}", imagePath, e.getMessage());
//            }
//            imageRepository.delete(image);
//        });
//
//        propertyRepository.delete(property);
//        logger.debug("PropertyService Debug: Property ID {} deleted successfully.", id);
//    }
//
//    @Transactional
//    public PropertyDTO markPropertyAvailableForRent(Long propertyId, User authenticatedUser) {
//        logger.debug("PropertyService Debug: Marking property {} available for rent.", propertyId);
//        Property property = propertyRepository.findById(propertyId)
//                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + propertyId));
//
//        if (!Objects.equals(property.getOwner().getId(), authenticatedUser.getId())) {
//            logger.warn("PropertyService Warning: User {} attempted to mark property {} available for rent but is not the owner.",
//                    authenticatedUser.getId(), propertyId);
//            throw new RuntimeException("You do not have permission to perform this action.");
//        }
//
//        property.setAvailableForRent(true);
//        property.setAvailable(true);
//        Property updatedProperty = propertyRepository.save(property);
//        logger.debug("PropertyService Debug: Property {} marked available for rent.", propertyId);
//        return convertToDTO(updatedProperty);
//    }
//
//    // NEW METHOD: Mark property as rented/unavailable
//    @Transactional
//    public PropertyDTO markPropertyAsRented(Long propertyId, User authenticatedUser) {
//        logger.debug("PropertyService Debug: Marking property {} as rented/unavailable.", propertyId);
//        Property property = propertyRepository.findById(propertyId)
//                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + propertyId));
//
//        // Authorization check: Ensure the authenticated user is the owner of the property
//        if (!Objects.equals(property.getOwner().getId(), authenticatedUser.getId())) {
//            logger.warn("PropertyService Warning: User {} attempted to mark property {} as rented but is not the owner.",
//                    authenticatedUser.getId(), propertyId);
//            throw new RuntimeException("You do not have permission to perform this action.");
//        }
//
//        property.setAvailableForRent(false); // No longer available for rent
//        property.setAvailable(false); // Generally unavailable
//        Property updatedProperty = propertyRepository.save(property);
//        logger.debug("PropertyService Debug: Property {} marked as rented/unavailable.", propertyId);
//        return convertToDTO(updatedProperty);
//    }
//
//
//    @Transactional(readOnly = true)
//    public List<PropertyDTO> getPropertiesByOwner(User owner) {
//        logger.debug("PropertyService Debug: Fetching properties for owner: {}", owner.getEmail());
//        List<Property> properties = propertyRepository.findByOwnerId(owner.getId());
//        return properties.stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    private PropertyDTO convertToDTO(Property property) {
//        PropertyDTO propertyDTO = new PropertyDTO();
//        BeanUtils.copyProperties(property, propertyDTO);
//        if (property.getOwner() != null) {
//            propertyDTO.setOwnerId(property.getOwner().getId());
//            propertyDTO.setOwnerFirstName(property.getOwner().getFirstName());
//            propertyDTO.setOwnerLastName(property.getOwner().getLastName());
//        }
//        if (property.getImages() != null && !property.getImages().isEmpty()) {
//            propertyDTO.setImages(property.getImages().stream()
//                    .map(image -> new PropertyDTO.ImageDTO(image.getId(), image.getImageUrl()))
//                    .collect(Collectors.toList()));
//        }
//        return propertyDTO;
//    }
//} comment jul 14

//@Service
//@RequiredArgsConstructor
//public class PropertyService {
//
//    private static final Logger logger = LoggerFactory.getLogger(PropertyService.class);
//    private final PropertyRepository propertyRepository;
//    private final UserRepository userRepository;
//    private final ImageRepository imageRepository;
//
//    private final String UPLOAD_DIR = "uploads/";
//
//    @Transactional
//    public PropertyDTO createProperty(PropertyDTO propertyDTO, List<MultipartFile> images, User owner) throws IOException {
//        try {
//            logger.debug("PropertyService Debug: Creating property for owner: {}", owner.getEmail());
//
//            Property property = new Property();
//            BeanUtils.copyProperties(propertyDTO, property);
//            property.setOwner(owner);
//            property.setAvailable(true);
//            property.setAvailableForRent(propertyDTO.getAvailableForRent() != null ? propertyDTO.getAvailableForRent() : false);
//            property.setAvailableForSale(propertyDTO.getAvailableForSale() != null ? propertyDTO.getAvailableForSale() : false);
//
//            Property savedProperty = propertyRepository.save(property);
//            logger.debug("PropertyService Debug: Property saved with ID: {}", savedProperty.getId());
//
//            if (images != null && !images.isEmpty()) {
//                Path uploadPath = Paths.get(UPLOAD_DIR);
//                if (!Files.exists(uploadPath)) {
//                    Files.createDirectories(uploadPath);
//                }
//
//                for (MultipartFile file : images) {
//                    if (!file.isEmpty()) {
//                        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
//                        Path filePath = uploadPath.resolve(fileName);
//                        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//                        Image image = new Image();
//                        image.setImageUrl("/uploads/" + fileName);
//                        image.setProperty(savedProperty);
//                        imageRepository.save(image);
//                        logger.debug("PropertyService Debug: Saved image: {}", image.getImageUrl());
//                    }
//                }
//            }
//            return convertToDTO(savedProperty);
//        } catch (Exception e) {
//            logger.error("PropertyService Error in createProperty: " + e.getMessage(), e);
//            throw e;
//        }
//    }
//
//    @Transactional(readOnly = true)
//    public Page<PropertyDTO> getAllProperties(
//            int page,
//            int size,
//            String sortBy,
//            String sortDir,
//            String propertyType,
//            String city,
//            BigDecimal minRent,
//            BigDecimal maxRent,
//            Integer minBedrooms,
//            Double minBathrooms,
//            String keyword,
//            Boolean availableForRent,
//            Boolean availableForSale
//    ) {
//        try {
//            Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
//            Pageable pageable = PageRequest.of(page, size, sort);
//
//            Specification<Property> spec = Specification.where(null);
//
//            if (propertyType != null && !propertyType.isEmpty()) {
//                try {
//                    PropertyType typeEnum = PropertyType.valueOf(propertyType.toUpperCase());
//                    spec = spec.and(PropertySpecification.hasPropertyType(typeEnum));
//                } catch (IllegalArgumentException e) {
//                    logger.warn("Invalid property type provided: {}", propertyType);
//                }
//            }
//            if (city != null && !city.isEmpty()) {
//                spec = spec.and(PropertySpecification.hasCity(city));
//            }
//            if (minRent != null) {
//                spec = spec.and(PropertySpecification.hasMinRentPrice(minRent));
//            }
//            if (maxRent != null) {
//                spec = spec.and(PropertySpecification.hasMaxRentPrice(maxRent));
//            }
//            if (minBedrooms != null) {
//                spec = spec.and(PropertySpecification.hasMinBedrooms(minBedrooms));
//            }
//            if (minBathrooms != null) {
//                spec = spec.and(PropertySpecification.hasMinBathrooms(minBathrooms));
//            }
//            if (keyword != null && !keyword.isEmpty()) {
//                spec = spec.and(PropertySpecification.containsKeyword(keyword));
//            }
//            if (availableForRent != null || availableForSale != null) {
//                spec = spec.and(PropertySpecification.withFilters(
//                        null, null, null, null, null, null, null,
//                        availableForRent, availableForSale
//                ));
//            }
//
//            Page<Property> propertiesPage = propertyRepository.findAll(spec, pageable);
//            return propertiesPage.map(this::convertToDTO);
//        } catch (Exception e) {
//            logger.error("PropertyService Error in getAllProperties: " + e.getMessage(), e);
//            throw e;
//        }
//    }
//
//
//    @Transactional(readOnly = true)
//    public PropertyDTO getPropertyById(Long id) {
//        try {
//            logger.debug("PropertyService Debug: Fetching property with ID: {}", id);
//            Property property = propertyRepository.findById(id)
//                    .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));
//            return convertToDTO(property);
//        } catch (Exception e) {
//            logger.error("PropertyService Error in getPropertyById: " + e.getMessage(), e);
//            throw e;
//        }
//    }
//
//    @Transactional
//    public PropertyDTO updateProperty(Long id, PropertyDTO propertyDTO, List<MultipartFile> newImages, User authenticatedUser) throws IOException {
//        try {
//            logger.debug("PropertyService Debug: Attempting to update Property ID: {}", id);
//
//            Property existingProperty = propertyRepository.findById(id)
//                    .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));
//
//            if (!Objects.equals(existingProperty.getOwner().getId(), authenticatedUser.getId())) {
//                logger.warn("PropertyService Warning: User {} attempted to update property {} but is not the owner (Property owned by {}).",
//                        authenticatedUser.getId(), id, existingProperty.getOwner().getId());
//                throw new RuntimeException("You do not have permission to update this property.");
//            }
//
//            logger.debug("PropertyService Debug: Authenticated User ID (from JWT/SecurityContext): {}", authenticatedUser.getId());
//            logger.debug("PropertyService Debug: Existing Property Owner ID (from Database): {}", existingProperty.getOwner().getId());
//
//            // Use propertyDTO.getAvailable(), etc., as they are now Boolean wrapper types
//            BeanUtils.copyProperties(propertyDTO, existingProperty, "id", "owner", "images");
//            existingProperty.setAvailable(propertyDTO.getAvailable() != null ? propertyDTO.getAvailable() : false);
//            existingProperty.setAvailableForRent(propertyDTO.getAvailableForRent() != null ? propertyDTO.getAvailableForRent() : false);
//            existingProperty.setAvailableForSale(propertyDTO.getAvailableForSale() != null ? propertyDTO.getAvailableForSale() : false);
//
//
//            if (newImages != null && !newImages.isEmpty()) {
//                Path uploadPath = Paths.get(UPLOAD_DIR);
//                if (!Files.exists(uploadPath)) {
//                    Files.createDirectories(uploadPath);
//                }
//
//                for (MultipartFile file : newImages) {
//                    if (!file.isEmpty()) {
//                        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
//                        Path filePath = uploadPath.resolve(fileName);
//                        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//                        Image image = new Image();
//                        image.setImageUrl("/uploads/" + fileName);
//                        image.setProperty(existingProperty);
//                        imageRepository.save(image);
//                        logger.debug("PropertyService Debug: Saved new image: {}", image.getImageUrl());
//                    }
//                }
//            }
//
//            Property updatedProperty = propertyRepository.save(existingProperty);
//            logger.debug("PropertyService Debug: Property ID {} updated successfully.", updatedProperty.getId());
//            return convertToDTO(updatedProperty);
//        } catch (Exception e) {
//            logger.error("PropertyService Error in updateProperty: " + e.getMessage(), e);
//            throw e;
//        }
//    }
//
//    @Transactional
//    public void deleteProperty(Long id, User authenticatedUser) {
//        try {
//            logger.debug("PropertyService Debug: Attempting to delete Property ID: {}", id);
//            Property property = propertyRepository.findById(id)
//                    .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));
//
//            if (!Objects.equals(property.getOwner().getId(), authenticatedUser.getId())) {
//                logger.warn("PropertyService Warning: User {} attempted to delete property {} but is not the owner (Property owned by {}).",
//                        authenticatedUser.getId(), id, property.getOwner().getId());
//                throw new RuntimeException("You do not have permission to delete this property.");
//            }
//
//            property.getImages().forEach(image -> {
//                Path imagePath = Paths.get(UPLOAD_DIR).resolve(Paths.get(image.getImageUrl()).getFileName().toString());
//                try {
//                    Files.deleteIfExists(imagePath);
//                    logger.debug("PropertyService Debug: Deleted image file: {}", imagePath);
//                } catch (IOException e) {
//                    logger.warn("PropertyService Warning: Could not delete image file {}: {}", imagePath, e.getMessage());
//                }
//                imageRepository.delete(image);
//            });
//
//            propertyRepository.delete(property);
//            logger.debug("PropertyService Debug: Property ID {} deleted successfully.", id);
//        } catch (Exception e) {
//            logger.error("PropertyService Error in deleteProperty: " + e.getMessage(), e);
//            throw e;
//        }
//    }
//
//    @Transactional
//    public PropertyDTO markPropertyAvailableForRent(Long propertyId, User authenticatedUser) {
//        try {
//            logger.debug("PropertyService Debug: Marking property {} available for rent.", propertyId);
//            Property property = propertyRepository.findById(propertyId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + propertyId));
//
//            if (!Objects.equals(property.getOwner().getId(), authenticatedUser.getId())) {
//                logger.warn("PropertyService Warning: User {} attempted to mark property {} available for rent but is not the owner.",
//                        authenticatedUser.getId(), propertyId);
//                throw new RuntimeException("You do not have permission to perform this action.");
//            }
//
//            property.setAvailableForRent(true);
//            property.setAvailable(true);
//            Property updatedProperty = propertyRepository.save(property);
//            logger.debug("PropertyService Debug: Property {} marked available for rent.", propertyId);
//            return convertToDTO(updatedProperty);
//        } catch (Exception e) {
//            logger.error("PropertyService Error in markPropertyAvailableForRent: " + e.getMessage(), e);
//            throw e;
//        }
//    }
//
//    // NEW METHOD: Mark property as rented/unavailable
//    @Transactional
//    public PropertyDTO markPropertyAsRented(Long propertyId, User authenticatedUser) {
//        try { // Added try-catch block
//            logger.debug("PropertyService Debug: Marking property {} as rented/unavailable.", propertyId);
//            Property property = propertyRepository.findById(propertyId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + propertyId));
//
//            // Authorization check: Ensure the authenticated user is the owner of the property
//            if (!Objects.equals(property.getOwner().getId(), authenticatedUser.getId())) {
//                logger.warn("PropertyService Warning: User {} attempted to mark property {} as rented but is not the owner.",
//                        authenticatedUser.getId(), propertyId);
//                throw new RuntimeException("You do not have permission to perform this action.");
//            }
//
//            property.setAvailableForRent(false); // No longer available for rent
//            property.setAvailable(false); // Generally unavailable
//            Property updatedProperty = propertyRepository.save(property);
//            logger.debug("PropertyService Debug: Property {} marked as rented/unavailable.", propertyId);
//            return convertToDTO(updatedProperty);
//        } catch (Exception e) { // Catch any exception
//            logger.error("PropertyService Error in markPropertyAsRented: " + e.getMessage(), e); // Log with stack trace
//            throw e; // Re-throw the exception to propagate it
//        }
//    }
//
//
//    @Transactional(readOnly = true)
//    public List<PropertyDTO> getPropertiesByOwner(User owner) {
//        try {
//            logger.debug("PropertyService Debug: Fetching properties for owner: {}", owner.getEmail());
//            List<Property> properties = propertyRepository.findByOwnerId(owner.getId());
//            return properties.stream()
//                    .map(this::convertToDTO)
//                    .collect(Collectors.toList());
//        } catch (Exception e) {
//            logger.error("PropertyService Error in getPropertiesByOwner: " + e.getMessage(), e);
//            throw e;
//        }
//    }
//
//    private PropertyDTO convertToDTO(Property property) {
//        PropertyDTO propertyDTO = new PropertyDTO();
//        BeanUtils.copyProperties(property, propertyDTO);
//        if (property.getOwner() != null) {
//            propertyDTO.setOwnerId(property.getOwner().getId());
//            propertyDTO.setOwnerFirstName(property.getOwner().getFirstName());
//            propertyDTO.setOwnerLastName(property.getOwner().getLastName());
//        }
//        if (property.getImages() != null && !property.getImages().isEmpty()) {
//            propertyDTO.setImages(property.getImages().stream()
//                    .map(image -> new PropertyDTO.ImageDTO(image.getId(), image.getImageUrl()))
//                    .collect(Collectors.toList()));
//        }
//        return propertyDTO;
//    }
//}
//Jully 29

@Service
@RequiredArgsConstructor
public class PropertyService {

    private static final Logger logger = LoggerFactory.getLogger(PropertyService.class);
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    private final String UPLOAD_DIR = "uploads/";

    @Transactional
    public PropertyDTO createProperty(PropertyDTO propertyDTO, List<MultipartFile> images, User owner) throws IOException {
        try {
            logger.debug("PropertyService Debug: Creating property for owner: {}", owner.getEmail());

            Property property = new Property();
            BeanUtils.copyProperties(propertyDTO, property);
            property.setOwner(owner);
            property.setAvailable(true);
            property.setAvailableForRent(propertyDTO.getAvailableForRent() != null ? propertyDTO.getAvailableForRent() : false);
            property.setAvailableForSale(propertyDTO.getAvailableForSale() != null ? propertyDTO.getAvailableForSale() : false);

            Property savedProperty = propertyRepository.save(property);
            logger.debug("PropertyService Debug: Property saved with ID: {}", savedProperty.getId());

            if (images != null && !images.isEmpty()) {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                for (MultipartFile file : images) {
                    if (!file.isEmpty()) {
                        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                        Path filePath = uploadPath.resolve(fileName);
                        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                        Image image = new Image();
                        image.setImageUrl("/uploads/" + fileName);
                        image.setProperty(savedProperty);
                        imageRepository.save(image);
                        logger.debug("PropertyService Debug: Saved image: {}", image.getImageUrl());
                    }
                }
            }
            return convertToDTO(savedProperty);
        } catch (Exception e) {
            logger.error("PropertyService Error in createProperty: " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Page<PropertyDTO> getAllProperties(
            int page,
            int size,
            String sortBy,
            String sortDir,
            String propertyType,
            String city,
            BigDecimal minRent,
            BigDecimal maxRent,
            Integer minBedrooms,
            Double minBathrooms,
            String keyword,
            Boolean availableForRent,
            Boolean availableForSale
    ) {
        try {
            Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
            Pageable pageable = PageRequest.of(page, size, sort);

            Specification<Property> spec = Specification.where(null);

            if (propertyType != null && !propertyType.isEmpty()) {
                try {
                    PropertyType typeEnum = PropertyType.valueOf(propertyType.toUpperCase());
                    spec = spec.and(PropertySpecification.hasPropertyType(typeEnum));
                } catch (IllegalArgumentException e) {
                    logger.warn("Invalid property type provided: {}", propertyType);
                }
            }
            if (city != null && !city.isEmpty()) {
                spec = spec.and(PropertySpecification.hasCity(city));
            }
            if (minRent != null) {
                spec = spec.and(PropertySpecification.hasMinRentPrice(minRent));
            }
            if (maxRent != null) {
                spec = spec.and(PropertySpecification.hasMaxRentPrice(maxRent));
            }
            if (minBedrooms != null) {
                spec = spec.and(PropertySpecification.hasMinBedrooms(minBedrooms));
            }
            if (minBathrooms != null) {
                spec = spec.and(PropertySpecification.hasMinBathrooms(minBathrooms));
            }
            if (keyword != null && !keyword.isEmpty()) {
                spec = spec.and(PropertySpecification.containsKeyword(keyword));
            }
            if (availableForRent != null || availableForSale != null) {
                spec = spec.and(PropertySpecification.withFilters(
                        null, null, null, null, null, null, null,
                        availableForRent, availableForSale
                ));
            }

            Page<Property> propertiesPage = propertyRepository.findAll(spec, pageable);
            return propertiesPage.map(this::convertToDTO);
        } catch (Exception e) {
            logger.error("PropertyService Error in getAllProperties: " + e.getMessage(), e);
            throw e;
        }
    }


    @Transactional(readOnly = true)
    public PropertyDTO getPropertyById(Long id) {
        try {
            logger.debug("PropertyService Debug: Fetching property with ID: {}", id);
            Property property = propertyRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));
            return convertToDTO(property);
        } catch (Exception e) {
            logger.error("PropertyService Error in getPropertyById: " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public PropertyDTO updateProperty(Long id, PropertyDTO propertyDTO, List<MultipartFile> newImages, User authenticatedUser) throws IOException {
        try {
            logger.debug("PropertyService Debug: Attempting to update Property ID: {}", id);

            Property existingProperty = propertyRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));

            if (!Objects.equals(existingProperty.getOwner().getId(), authenticatedUser.getId())) {
                logger.warn("PropertyService Warning: User {} attempted to update property {} but is not the owner (Property owned by {}).",
                        authenticatedUser.getId(), id, existingProperty.getOwner().getId());
                throw new RuntimeException("You do not have permission to update this property.");
            }

            logger.debug("PropertyService Debug: Authenticated User ID (from JWT/SecurityContext): {}", authenticatedUser.getId());
            logger.debug("PropertyService Debug: Existing Property Owner ID (from Database): {}", existingProperty.getOwner().getId());

            // Use propertyDTO.getAvailable(), etc., as they are now Boolean wrapper types
            BeanUtils.copyProperties(propertyDTO, existingProperty, "id", "owner", "images");
            existingProperty.setAvailable(propertyDTO.getAvailable() != null ? propertyDTO.getAvailable() : false);
            existingProperty.setAvailableForRent(propertyDTO.getAvailableForRent() != null ? propertyDTO.getAvailableForRent() : false);
            existingProperty.setAvailableForSale(propertyDTO.getAvailableForSale() != null ? propertyDTO.getAvailableForSale() : false);


            if (newImages != null && !newImages.isEmpty()) {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                for (MultipartFile file : newImages) {
                    if (!file.isEmpty()) {
                        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                        Path filePath = uploadPath.resolve(fileName);
                        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                        Image image = new Image();
                        image.setImageUrl("/uploads/" + fileName);
                        image.setProperty(existingProperty);
                        imageRepository.save(image);
                        logger.debug("PropertyService Debug: Saved new image: {}", image.getImageUrl());
                    }
                }
            }

            Property updatedProperty = propertyRepository.save(existingProperty);
            logger.debug("PropertyService Debug: Property ID {} updated successfully.", updatedProperty.getId());
            return convertToDTO(updatedProperty);
        } catch (Exception e) {
            logger.error("PropertyService Error in updateProperty: " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void deleteProperty(Long id, User authenticatedUser) {
        try {
            logger.debug("PropertyService Debug: Attempting to delete Property ID: {}", id);
            Property property = propertyRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));

            if (!Objects.equals(property.getOwner().getId(), authenticatedUser.getId())) {
                logger.warn("PropertyService Warning: User {} attempted to delete property {} but is not the owner (Property owned by {}).",
                        authenticatedUser.getId(), id, property.getOwner().getId());
                throw new RuntimeException("You do not have permission to delete this property.");
            }

            property.getImages().forEach(image -> {
                Path imagePath = Paths.get(UPLOAD_DIR).resolve(Paths.get(image.getImageUrl()).getFileName().toString());
                try {
                    Files.deleteIfExists(imagePath);
                    logger.debug("PropertyService Debug: Deleted image file: {}", imagePath);
                } catch (IOException e) {
                    logger.warn("PropertyService Warning: Could not delete image file {}: {}", imagePath, e.getMessage());
                }
                imageRepository.delete(image);
            });

            propertyRepository.delete(property);
            logger.debug("PropertyService Debug: Property ID {} deleted successfully.", id);
        } catch (Exception e) {
            logger.error("PropertyService Error in deleteProperty: " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public PropertyDTO markPropertyAvailableForRent(Long propertyId, User authenticatedUser) {
        try {
            logger.debug("PropertyService Debug: Marking property {} available for rent.", propertyId);
            Property property = propertyRepository.findById(propertyId)
                    .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + propertyId));

            if (!Objects.equals(property.getOwner().getId(), authenticatedUser.getId())) {
                logger.warn("PropertyService Warning: User {} attempted to mark property {} available for rent but is not the owner.",
                        authenticatedUser.getId(), propertyId);
                throw new RuntimeException("You do not have permission to perform this action.");
            }

            property.setAvailableForRent(true);
            property.setAvailable(true);
            Property updatedProperty = propertyRepository.save(property);
            logger.debug("PropertyService Debug: Property {} marked available for rent.", propertyId);
            return convertToDTO(updatedProperty);
        } catch (Exception e) {
            logger.error("PropertyService Error in markPropertyAvailableForRent: " + e.getMessage(), e);
            throw e;
        }
    }

    // NEW METHOD: Mark property as rented/unavailable
    @Transactional
    public PropertyDTO markPropertyAsRented(Long propertyId, User authenticatedUser) {
        try { // Added try-catch block
            logger.debug("PropertyService Debug: Marking property {} as rented/unavailable.", propertyId);
            Property property = propertyRepository.findById(propertyId)
                    .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + propertyId));

            // Authorization check: Ensure the authenticated user is the owner of the property
            if (!Objects.equals(property.getOwner().getId(), authenticatedUser.getId())) {
                logger.warn("PropertyService Warning: User {} attempted to mark property {} as rented but is not the owner.",
                        authenticatedUser.getId(), propertyId);
                throw new RuntimeException("You do not have permission to perform this action.");
            }

            property.setAvailableForRent(false); // No longer available for rent
            property.setAvailable(false); // Generally unavailable
            Property updatedProperty = propertyRepository.save(property);
            logger.debug("PropertyService Debug: Property {} marked as rented/unavailable.", propertyId);
            return convertToDTO(updatedProperty);
        } catch (Exception e) { // Catch any exception
            logger.error("PropertyService Error in markPropertyAsRented: " + e.getMessage(), e); // Log with stack trace
            throw e; // Re-throw the exception to propagate it
        }
    }


    @Transactional(readOnly = true)
    public List<PropertyDTO> getPropertiesByOwner(User owner) {
        try {
            logger.debug("PropertyService Debug: Fetching properties for owner: {}", owner.getEmail());
            List<Property> properties = propertyRepository.findByOwnerId(owner.getId());
            return properties.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("PropertyService Error in getPropertiesByOwner: " + e.getMessage(), e);
            throw e;
        }
    }

    private PropertyDTO convertToDTO(Property property) {
        PropertyDTO propertyDTO = new PropertyDTO();
        BeanUtils.copyProperties(property, propertyDTO);

        // NEW LOGGING: Diagnose owner object state
        logger.debug("PropertyService Debug: Converting property ID {} to DTO.", property.getId());
        if (property.getOwner() != null) {
            logger.debug("PropertyService Debug: Owner object is NOT null. Owner ID: {}, FirstName: {}, LastName: {}",
                    property.getOwner().getId(), property.getOwner().getFirstName(), property.getOwner().getLastName());
            propertyDTO.setOwnerId(property.getOwner().getId());
            propertyDTO.setOwnerFirstName(property.getOwner().getFirstName());
            propertyDTO.setOwnerLastName(property.getOwner().getLastName());
        } else {
            logger.warn("PropertyService Warning: Owner object is NULL for property ID {}. This might indicate a data integrity issue.", property.getId());
            propertyDTO.setOwnerId(null);
            propertyDTO.setOwnerFirstName("N/A"); // Explicitly set N/A
            propertyDTO.setOwnerLastName("N/A"); // Explicitly set N/A
        }

        if (property.getImages() != null && !property.getImages().isEmpty()) {
            propertyDTO.setImages(property.getImages().stream()
                    .map(image -> new PropertyDTO.ImageDTO(image.getId(), image.getImageUrl()))
                    .collect(Collectors.toList()));
        }
        return propertyDTO;
    }
}

