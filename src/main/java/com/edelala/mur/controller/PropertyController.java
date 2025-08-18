package com.edelala.mur.controller;


import com.edelala.mur.dto.PropertyDTO;
import com.edelala.mur.entity.Property;
import com.edelala.mur.entity.User;
import com.edelala.mur.repo.ImageRepository;
import com.edelala.mur.service.PropertyService;
import com.edelala.mur.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid; // Import jakarta.validation.Valid
import java.io.IOException; // Import IOException
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;




//@RestController
//@RequestMapping("/api/properties")
//@RequiredArgsConstructor
//public class PropertyController {
//
//    private final PropertyService propertyService;
//    private final UserService userService;
//
//    // GET all properties (publicly accessible)
//    @GetMapping
//    public ResponseEntity<Page<Property>> getAllProperties(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(required = false) String propertyType,
//            @RequestParam(required = false) String city,
//            @RequestParam(required = false) BigDecimal minRent,
//            @RequestParam(required = false) BigDecimal maxRent) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Property> properties = propertyService.getFilteredProperties(
//                propertyType, city, minRent, maxRent, pageable);
//        return ResponseEntity.ok(properties);
//    }
//
//    // GET property by ID (publicly accessible)
//    @GetMapping("/{id}")
//    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
//        return propertyService.getPropertyById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    // Owner specific: Get properties listed by the authenticated owner
//    @GetMapping("/owner/me")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<List<Property>> getMyProperties(@AuthenticationPrincipal UserDetails userDetails) {
//        User owner = userService.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Owner not found"));
//        List<Property> properties = propertyService.getPropertiesByOwner(owner);
//        return ResponseEntity.ok(properties);
//    }
//
//    // Owner specific: List a new property
//    @PostMapping(value = "/owner/list", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<Property> listNewProperty(
//            @RequestPart("property") @Valid PropertyDTO propertyDTO, // @Valid for DTO validation
//            @RequestPart(value = "images", required = false) List<MultipartFile> images,
//            @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            User owner = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Owner not found"));
//
//            // FIX: Corrected method call from createProperty to listNewProperty and ensured argument order
//            Property property = propertyService.listNewProperty(propertyDTO, images, owner);
//            return ResponseEntity.status(HttpStatus.CREATED).body(property);
//        } catch (IOException e) { // Catch IOException specifically for file operations
//            System.err.println("Error listing new property (IOException): " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        } catch (RuntimeException e) { // Catch for other service-level RuntimeExceptions
//            System.err.println("Error listing new property (RuntimeException): " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//    }
//
//    /**
//     * Endpoint for owners to update an existing property.
//     * Only accessible to users with the 'OWNER' role.
//     * The owner can only update properties they own.
//     * @param id The ID of the property to update.
//     * @param propertyDTO The updated Property object (as DTO).
//     * @param newImages List of new MultipartFile images to add.
//     * @param userDetails The authenticated owner's UserDetails.
//     * @return The updated Property object.
//     */
//    @PutMapping(value = "/owner/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<Property> updateProperty(
//            @PathVariable Long id,
//            @RequestPart("property") @Valid PropertyDTO propertyDTO,
//            @RequestPart(value = "newImages", required = false) List<MultipartFile> newImages,
//            @AuthenticationPrincipal UserDetails userDetails
//    ) {
//        try {
//            // Retrieve the current owner to verify ownership in the service layer
//            User currentOwner = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Owner not found for authenticated user."));
//
//            // Delegate update and ownership check to the service layer
//            Property updatedProperty = propertyService.updateProperty(id, propertyDTO, currentOwner, newImages);
//            return ResponseEntity.ok(updatedProperty);
//        } catch (IOException e) { // Catch IOException specifically for file operations
//            System.err.println("Error updating property (IOException): " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        } catch (RuntimeException e) {
//            // Catch for property not found, not owned by current user, or other service-level errors
//            // Use specific status codes based on the nature of the RuntimeException
//            if (e.getMessage().contains("Property not found")) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//            } else if (e.getMessage().contains("permission to update")) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // 403 Forbidden
//            }
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Generic bad request for other errors
//        }
//    }
//
//
//    /**
//     * Endpoint for owners to delete a property.
//     * Only accessible to users with the 'OWNER' role.
//     * The owner can only delete properties they own.
//     * @param id The ID of the property to delete.
//     * @param userDetails The authenticated owner's UserDetails.
//     * @return 204 No Content if successful, or 404 Not Found/403 Forbidden.
//     */
//    @DeleteMapping("/owner/{id}")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<Void> deleteProperty(
//            @PathVariable Long id,
//            @AuthenticationPrincipal UserDetails userDetails
//    ) {
//        try {
//            // Retrieve the current owner to verify ownership in the service layer
//            User currentOwner = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Owner not found for authenticated user."));
//
//            // Delegate deletion and ownership check to the service layer
//            propertyService.deleteProperty(id, currentOwner);
//            return ResponseEntity.noContent().build(); // 204 No Content for successful deletion
//        } catch (RuntimeException e) {
//            // e.g., Property not found or not owned by the authenticated owner
//            if (e.getMessage().contains("Property not found")) {
//                return ResponseEntity.notFound().build(); // 404 Not Found
//            } else if (e.getMessage().contains("permission to delete")) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden
//            }
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Generic bad request for other errors
//        }
//    }
//
//    // Owner specific: Mark property as available for rent
//    @PostMapping("/owner/{id}/available-for-rent")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<Property> markPropertyAvailableForRent(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
//        User owner = userService.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Owner not found"));
//        try {
//            Property updatedProperty = propertyService.markAsAvailableForRent(id, owner);
//            return ResponseEntity.ok(updatedProperty);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//    }
//
//    @PostMapping("/owner/{id}/rented")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<Property> markAsRented(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
//        User owner = userService.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Owner not found"));
//        try {
//            Property updatedProperty = propertyService.markAsRented(id, owner);
//            return ResponseEntity.ok(updatedProperty);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//    }
//
//    @PostMapping("/owner/{id}/available-for-sale")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<Property> markAsAvailableForSale(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
//        User owner = userService.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Owner not found"));
//        try {
//            Property updatedProperty = propertyService.markAsAvailableForSale(id, owner);
//            return ResponseEntity.ok(updatedProperty);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//    }
//
//    @PostMapping("/owner/{id}/unavailable")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<Property> markAsUnavailable(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
//        User owner = userService.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Owner not found"));
//        try {
//            Property updatedProperty = propertyService.markAsUnavailable(id, owner);
//            return ResponseEntity.ok(updatedProperty);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//    }
//}
//jun 24 comment to improve owner dashboard

//@RestController
//@RequestMapping("/api/properties")
//@RequiredArgsConstructor
//public class PropertyController {
//
//    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);
//
//    private final PropertyService propertyService;
//    private final UserService userService;
//
//    // GET all properties (publicly accessible)
//    @GetMapping
//    public ResponseEntity<Page<Property>> getAllProperties(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(required = false) String propertyType,
//            @RequestParam(required = false) String city,
//            @RequestParam(required = false) BigDecimal minRent,
//            @RequestParam(required = false) BigDecimal maxRent) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Property> properties = propertyService.getFilteredProperties(
//                propertyType, city, minRent, maxRent, pageable);
//        return ResponseEntity.ok(properties);
//    }
//
//    // GET property by ID (publicly accessible)
//    @GetMapping("/{id}")
//    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
//        return propertyService.getPropertyById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    /**
//     * Owner specific: Get properties listed by the authenticated owner with pagination, filtering, and sorting.
//     * This endpoint now returns a Page<Property> to support pagination and sorting.
//     * @param userDetails The authenticated owner's UserDetails.
//     * @param page Page number (default 0).
//     * @param size Page size (default 100).
//     * @param sort Sorting criteria (e.g., "id,asc", "title,desc"). Received as a single String.
//     * @param available Filter by general availability.
//     * @param availableForRent Filter by availability for rent.
//     * @param availableForSale Filter by availability for sale.
//     * @param propertyType Filter by property type.
//     * @return A Page of properties belonging to the owner.
//     */
//    @GetMapping("/owner/me")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<Page<Property>> getMyProperties(
//            @AuthenticationPrincipal UserDetails userDetails,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "100") int size,
//            @RequestParam(defaultValue = "id,asc") String sort, // Changed to String
//            @RequestParam(required = false) Boolean available,
//            @RequestParam(required = false) Boolean availableForRent,
//            @RequestParam(required = false) Boolean availableForSale,
//            @RequestParam(required = false) String propertyType) {
//        User owner = userService.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Owner not found"));
//
//        // Parse the single sort string into a Sort.Order object
//        List<Sort.Order> sortOrders = new ArrayList<>();
//        if (sort != null && !sort.isEmpty()) {
//            String[] parts = sort.split(",");
//            if (parts.length == 2) {
//                String property = parts[0].trim();
//                Sort.Direction direction = parts[1].trim().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
//                sortOrders.add(new Sort.Order(direction, property));
//            } else if (parts.length == 1) {
//                // If only property name is provided, default to ascending
//                sortOrders.add(new Sort.Order(Sort.Direction.ASC, parts[0].trim()));
//            } else {
//                logger.warn("PropertyController: Malformed sort parameter received: {}. Defaulting to 'id,asc'.", sort);
//                sortOrders.add(new Sort.Order(Sort.Direction.ASC, "id"));
//            }
//        }
//
//        // If after parsing, sortOrders is still empty (e.g., sort was null or empty string), default to sorting by ID ascending
//        if (sortOrders.isEmpty()) {
//            sortOrders.add(new Sort.Order(Sort.Direction.ASC, "id"));
//        }
//
//        logger.debug("PropertyController: Parsed Sort Orders: {}", sortOrders);
//
//        // Build Pageable object from page, size, and parsed sort orders
//        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrders));
//        logger.debug("PropertyController: Created Pageable with Sort: {}", pageable.getSort());
//
//        // Call the updated service method with all parameters
//        Page<Property> properties = propertyService.getPropertiesByOwner(
//                owner, available, availableForRent, availableForSale, propertyType, pageable
//        );
//        return ResponseEntity.ok(properties);
//    }
//
//    // Owner specific: List a new property
//    @PostMapping(value = "/owner/list", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<Property> listNewProperty(
//            @RequestPart("property") @Valid PropertyDTO propertyDTO,
//            @RequestPart(value = "images", required = false) List<MultipartFile> images,
//            @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            User owner = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Owner not found"));
//
//            Property property = propertyService.listNewProperty(propertyDTO, images, owner);
//            return ResponseEntity.status(HttpStatus.CREATED).body(property);
//        } catch (IOException e) {
//            logger.error("Error listing new property (IOException): {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        } catch (RuntimeException e) {
//            logger.error("Error listing new property (RuntimeException): {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//    }
//
//    /**
//     * Endpoint for owners to update an existing property.
//     * Only accessible to users with the 'OWNER' role.
//     * The owner can only update properties they own.
//     * @param id The ID of the property to update.
//     * @param propertyDTO The updated Property object (as DTO).
//     * @param newImages List of new MultipartFile images to add.
//     * @param userDetails The authenticated owner's UserDetails.
//     * @return The updated Property object.
//     */
//    @PutMapping(value = "/owner/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<Property> updateProperty(
//            @PathVariable Long id,
//            @RequestPart("property") @Valid PropertyDTO propertyDTO,
//            @RequestPart(value = "newImages", required = false) List<MultipartFile> newImages,
//            @AuthenticationPrincipal UserDetails userDetails
//    ) {
//        try {
//            User currentOwner = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Owner not found for authenticated user."));
//
//            Property updatedProperty = propertyService.updateProperty(id, propertyDTO, currentOwner, newImages);
//            return ResponseEntity.ok(updatedProperty);
//        } catch (IOException e) {
//            logger.error("Error updating property (IOException): {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        } catch (RuntimeException e) {
//            logger.error("Error updating property (RuntimeException): {}", e.getMessage(), e);
//            if (e.getMessage().contains("Property not found")) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//            } else if (e.getMessage().contains("permission to update")) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
//            }
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//    }
//
//
//    /**
//     * Endpoint for owners to delete a property.
//     * Only accessible to users with the 'OWNER' role.
//     * The owner can only delete properties they own.
//     * @param id The ID of the property to delete.
//     * @param userDetails The authenticated owner's UserDetails.
//     * @return 204 No Content if successful, or 404 Not Found/403 Forbidden.
//     */
//    @DeleteMapping("/owner/{id}")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<Void> deleteProperty(
//            @PathVariable Long id,
//            @AuthenticationPrincipal UserDetails userDetails
//    ) {
//        try {
//            User currentOwner = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Owner not found for authenticated user."));
//
//            propertyService.deleteProperty(id, currentOwner);
//            return ResponseEntity.noContent().build();
//        } catch (RuntimeException e) {
//            logger.error("Error deleting property (RuntimeException): {}", e.getMessage(), e);
//            if (e.getMessage().contains("Property not found")) {
//                return ResponseEntity.notFound().build();
//            } else if (e.getMessage().contains("permission to delete")) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//            }
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//    }
//
//    // Owner specific: Mark property as available for rent
//    @PostMapping("/owner/{id}/available-for-rent")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<Property> markPropertyAvailableForRent(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
//        User owner = userService.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Owner not found"));
//        try {
//            Property updatedProperty = propertyService.markAsAvailableForRent(id, owner);
//            return ResponseEntity.ok(updatedProperty);
//        } catch (RuntimeException e) {
//            logger.error("Error marking property available for rent (RuntimeException): {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//    }
//
//    @PostMapping("/owner/{id}/rented")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<Property> markAsRented(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
//        User owner = userService.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Owner not found"));
//        try {
//            Property updatedProperty = propertyService.markAsRented(id, owner);
//            return ResponseEntity.ok(updatedProperty);
//        } catch (RuntimeException e) {
//            logger.error("Error marking property as rented (RuntimeException): {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//    }
//
//    @PostMapping("/owner/{id}/available-for-sale")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<Property> markAsAvailableForSale(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
//        User owner = userService.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Owner not found"));
//        try {
//            Property updatedProperty = propertyService.markAsAvailableForSale(id, owner);
//            return ResponseEntity.ok(updatedProperty);
//        } catch (RuntimeException e) {
//            logger.error("Error marking property available for sale (RuntimeException): {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//    }
//
//    @PostMapping("/owner/{id}/unavailable")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<Property> markAsUnavailable(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
//        User owner = userService.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Owner not found"));
//        try {
//            Property updatedProperty = propertyService.markAsUnavailable(id, owner);
//            return ResponseEntity.ok(updatedProperty);
//        } catch (RuntimeException e) {
//            logger.error("Error marking property as unavailable (RuntimeException): {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//    }
//}

// ************* july 10 comment to improve filter *********

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    // Endpoint for creating a new property (Owner only)
    @PostMapping(value = "/owner", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PropertyDTO> createProperty(
            @RequestPart("property") PropertyDTO propertyDTO,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        User owner = (User) userDetails;
        PropertyDTO createdProperty = propertyService.createProperty(propertyDTO, images, owner);
        return new ResponseEntity<>(createdProperty, HttpStatus.CREATED);
    }

    // Endpoint for getting all properties with pagination and filters (Public)
    @GetMapping
    public ResponseEntity<Page<PropertyDTO>> getAllProperties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String propertyType,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) BigDecimal minRent, // Changed to BigDecimal
            @RequestParam(required = false) BigDecimal maxRent, // Changed to BigDecimal
            @RequestParam(required = false) Integer minBedrooms,
            @RequestParam(required = false) Double minBathrooms,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean availableForRent,
            @RequestParam(required = false) Boolean availableForSale
    ) {
        Page<PropertyDTO> properties = propertyService.getAllProperties(
                page, size, sortBy, sortDir,
                propertyType, city, minRent, maxRent,
                minBedrooms, minBathrooms, keyword,
                availableForRent, availableForSale
        );
        return ResponseEntity.ok(properties);
    }

    // Endpoint for getting a single property by ID (Public)
    @GetMapping("/{id}")
    public ResponseEntity<PropertyDTO> getPropertyById(@PathVariable Long id) {
        PropertyDTO property = propertyService.getPropertyById(id);
        return ResponseEntity.ok(property);
    }

    // Endpoint for updating a property (Owner only)
    @PutMapping(value = "/owner/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PropertyDTO> updateProperty(
            @PathVariable Long id,
            @RequestPart("property") PropertyDTO propertyDTO,
            @RequestPart(value = "newImages", required = false) List<MultipartFile> newImages,
            @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        User authenticatedUser = (User) userDetails;
        PropertyDTO updatedProperty = propertyService.updateProperty(id, propertyDTO, newImages, authenticatedUser);
        return ResponseEntity.ok(updatedProperty);
    }

    // Endpoint for deleting a property (Owner only)
    @DeleteMapping("/owner/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User authenticatedUser = (User) userDetails;
        propertyService.deleteProperty(id, authenticatedUser);
        return ResponseEntity.noContent().build();
    }

    // Endpoint for marking a property available for rent (Owner only)
    @PostMapping("/owner/{propertyId}/available-for-rent")
    public ResponseEntity<PropertyDTO> markPropertyAvailableForRent(@PathVariable Long propertyId, @AuthenticationPrincipal UserDetails userDetails) {
        User authenticatedUser = (User) userDetails;
        PropertyDTO updatedProperty = propertyService.markPropertyAvailableForRent(propertyId, authenticatedUser);
        return ResponseEntity.ok(updatedProperty);
    }

    // Endpoint for getting properties listed by the authenticated owner
    @GetMapping("/owner/me")
    public ResponseEntity<List<PropertyDTO>> getMyProperties(@AuthenticationPrincipal UserDetails userDetails) {
        User owner = (User) userDetails;
        List<PropertyDTO> properties = propertyService.getPropertiesByOwner(owner);
        return ResponseEntity.ok(properties);
    }
}
