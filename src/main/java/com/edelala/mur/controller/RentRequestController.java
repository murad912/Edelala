package com.edelala.mur.controller;


import com.edelala.mur.entity.RentRequest;
import com.edelala.mur.entity.User;
import com.edelala.mur.service.RentRequestService;
import com.edelala.mur.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;


//@RestController
//@RequestMapping("/api/rent-requests")
//@RequiredArgsConstructor
//public class RentRequestController {
//
//    private final RentRequestService rentRequestService;
//    private final UserService userService; // <--- IMPORTANT: Inject UserService
//
//    /**
//     * Endpoint for renters to submit a new rent request.
//     * The renter's identity is extracted from the JWT in the security context.
//     *
//     * @param requestBody A map containing "propertyId" (as String) and "message".
//     * @param userDetails The authenticated Renter's UserDetails.
//     * @return ResponseEntity with the created RentRequest or an error message.
//     */
//    @PostMapping
//    @PreAuthorize("hasRole('RENTER')") // Only renters can submit requests
//    public ResponseEntity<?> createRentRequest(@RequestBody Map<String, Object> requestBody,
//                                               @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            // Safely parse propertyId from String to Long
//            Long propertyId = Long.parseLong(String.valueOf(requestBody.get("propertyId")));
//
//            // Safely parse message to String using String.valueOf()
//            String message = String.valueOf(requestBody.get("message"));
//
//            // --- IMPORTANT FIX: Get the actual User entity from UserService using the username (email) ---
//            User renter = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Renter not found after authentication."));
//
//            RentRequest newRequest = rentRequestService.createRentRequest(propertyId, message, renter);
//            return new ResponseEntity<>(newRequest, HttpStatus.CREATED);
//        } catch (NumberFormatException e) {
//            return new ResponseEntity<>("Invalid 'propertyId' format. It must be a valid number.", HttpStatus.BAD_REQUEST);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>("An unexpected error occurred while creating rent request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /**
//     * Endpoint for owners to view all rent requests for properties THEY OWN.
//     *
//     * @param userDetails The authenticated Owner's UserDetails.
//     * @return ResponseEntity with a list of RentRequest entities.
//     */
//    @GetMapping("/owner/my-properties-requests") // Clearer endpoint name
//    @PreAuthorize("hasRole('OWNER')") // Only owners can view requests for their properties
//    public ResponseEntity<List<RentRequest>> getRentRequestsForOwnedProperties(@AuthenticationPrincipal UserDetails userDetails) {
//        // --- IMPORTANT FIX: Get the actual User entity from UserService using the username (email) ---
//        User owner = userService.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//        List<RentRequest> requests = rentRequestService.getRentRequestsForOwnerProperties(owner);
//        return ResponseEntity.ok(requests);
//    }
//
//    /**
//     * Endpoint for owners to view rent requests for a SPECIFIC property they own.
//     * This is the endpoint you introduced.
//     *
//     * @param propertyId The ID of the property.
//     * @param userDetails The authenticated Owner's UserDetails.
//     * @return ResponseEntity with a list of RentRequest entities for the specific property.
//     */
//    @GetMapping("/owner/property/{propertyId}")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<List<RentRequest>> getRentRequestsByProperty(@PathVariable Long propertyId,
//                                                                       @AuthenticationPrincipal UserDetails userDetails) {
//        // --- IMPORTANT FIX: Get the actual User entity from UserService using the username (email) ---
//        User owner = userService.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//        List<RentRequest> rentRequests = rentRequestService.getRentRequestsByPropertyAndOwner(propertyId, owner);
//        return ResponseEntity.ok(rentRequests);
//    }
//
//    /**
//     * Endpoint for renters to view their own submitted rent requests.
//     *
//     * @param userDetails The authenticated Renter's UserDetails.
//     * @return ResponseEntity with a list of RentRequest entities.
//     */
//    @GetMapping("/renter/my-requests")
//    @PreAuthorize("hasRole('RENTER')") // Only renters can view their own requests
//    public ResponseEntity<List<RentRequest>> getMyRentRequests(@AuthenticationPrincipal UserDetails userDetails) {
//        // --- IMPORTANT FIX: Get the actual User entity from UserService using the username (email) ---
//        User renter = userService.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Renter not found after authentication."));
//        List<RentRequest> requests = rentRequestService.getRentRequestsByRenter(renter);
//        return ResponseEntity.ok(requests);
//    }
//
//    /**
//     * Endpoint for owners to approve a specific rent request.
//     * Uses PUT for state change (approval).
//     *
//     * @param requestId The ID of the rent request to approve.
//     * @param userDetails The authenticated Owner's UserDetails (for security check within service).
//     * @return ResponseEntity with the approved RentRequest.
//     */
//    @PutMapping("/owner/{requestId}/approve") // RESTful PUT and clear path
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<RentRequest> approveRentRequest(@PathVariable Long requestId,
//                                                          @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            // --- IMPORTANT FIX: Get the actual User entity from UserService using the username (email) ---
//            User owner = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//            RentRequest approvedRequest = rentRequestService.approveRentRequest(requestId, owner);
//            return ResponseEntity.ok(approvedRequest);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    /**
//     * Endpoint for owners to reject a specific rent request.
//     * Uses PUT for state change (rejection).
//     *
//     * @param requestId The ID of the rent request to reject.
//     * @param userDetails The authenticated Owner's UserDetails (for security check within service).
//     * @return ResponseEntity with the rejected RentRequest.
//     */
//    @PutMapping("/owner/{requestId}/reject") // RESTful PUT and clear path
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<RentRequest> rejectRentRequest(@PathVariable Long requestId,
//                                                         @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            // --- IMPORTANT FIX: Get the actual User entity from UserService using the username (email) ---
//            User owner = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//            RentRequest rejectedRequest = rentRequestService.rejectRentRequest(requestId, owner);
//            return ResponseEntity.ok(rejectedRequest);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//}

//@RestController
//@RequestMapping("/api/rent-requests")
//@RequiredArgsConstructor
//public class RentRequestController {
//
//    private final RentRequestService rentRequestService;
//    private final UserService userService;
//
//    /**
//     * Endpoint for renters to submit a new rent request.
//     * The renter's identity is extracted from the JWT in the security context.
//     *
//     * @param requestBody A map containing "propertyId" (as String) and "message".
//     * @param userDetails The authenticated Renter's UserDetails.
//     * @return ResponseEntity with the created RentRequest or an error message.
//     */
//    @PostMapping
//    @PreAuthorize("hasRole('RENTER')")
//    public ResponseEntity<?> createRentRequest(@RequestBody Map<String, Object> requestBody,
//                                               @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            Long propertyId = Long.parseLong(String.valueOf(requestBody.get("propertyId")));
//            String message = String.valueOf(requestBody.get("message"));
//
//            User renter = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Renter not found after authentication."));
//
//            RentRequest newRequest = rentRequestService.createRentRequest(propertyId, message, renter);
//            return new ResponseEntity<>(newRequest, HttpStatus.CREATED);
//        } catch (NumberFormatException e) {
//            return new ResponseEntity<>("Invalid 'propertyId' format. It must be a valid number.", HttpStatus.BAD_REQUEST);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>("An unexpected error occurred while creating rent request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /**
//     * Endpoint for owners to view all rent requests for properties THEY OWN.
//     *
//     * @param userDetails The authenticated Owner's UserDetails.
//     * @return ResponseEntity with a list of RentRequest entities.
//     */
//    @GetMapping("/owner/my-properties-requests")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<List<RentRequest>> getRentRequestsForOwnedProperties(@AuthenticationPrincipal UserDetails userDetails) {
//        User owner = userService.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//        List<RentRequest> requests = rentRequestService.getRentRequestsForOwnerProperties(owner);
//        return ResponseEntity.ok(requests);
//    }
//
//    /**
//     * Endpoint for owners to view rent requests for a SPECIFIC property they own.
//     *
//     * @param propertyId The ID of the property.
//     * @param userDetails The authenticated Owner's UserDetails.
//     * @return ResponseEntity with a list of RentRequest entities for the specific property.
//     */
//    @GetMapping("/owner/property/{propertyId}")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<List<RentRequest>> getRentRequestsByProperty(@PathVariable Long propertyId,
//                                                                       @AuthenticationPrincipal UserDetails userDetails) {
//        User owner = userService.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//        List<RentRequest> rentRequests = rentRequestService.getRentRequestsByPropertyAndOwner(propertyId, owner);
//        return ResponseEntity.ok(rentRequests);
//    }
//
//    /**
//     * Endpoint for renters to view their own submitted rent requests.
//     *
//     * @param userDetails The authenticated Renter's UserDetails.
//     * @return ResponseEntity with a list of RentRequest entities.
//     */
//    @GetMapping("/renter/my-requests")
//    @PreAuthorize("hasRole('RENTER')")
//    public ResponseEntity<List<RentRequest>> getMyRentRequests(@AuthenticationPrincipal UserDetails userDetails) {
//        User renter = userService.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Renter not found after authentication."));
//        List<RentRequest> requests = rentRequestService.getRentRequestsByRenter(renter);
//        return ResponseEntity.ok(requests);
//    }
//
//    /**
//     * Endpoint for owners to approve a specific rent request.
//     * Uses PUT for state change (approval).
//     *
//     * @param requestId The ID of the rent request to approve.
//     * @param userDetails The authenticated Owner's UserDetails (for security check within service).
//     * @return ResponseEntity with the approved RentRequest.
//     */
//    @PutMapping("/owner/{requestId}/approve")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<RentRequest> approveRentRequest(@PathVariable Long requestId,
//                                                          @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            User owner = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//            RentRequest approvedRequest = rentRequestService.approveRentRequest(requestId, owner);
//            return ResponseEntity.ok(approvedRequest);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Use BAD_REQUEST for service-level errors
//        }
//    }
//
//    /**
//     * Endpoint for owners to reject a specific rent request.
//     * Uses PUT for state change (rejection).
//     *
//     * @param requestId The ID of the rent request to reject.
//     * @param userDetails The authenticated Owner's UserDetails (for security check within service).
//     * @return ResponseEntity with the rejected RentRequest.
//     */
//    @PutMapping("/owner/{requestId}/reject")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<RentRequest> rejectRentRequest(@PathVariable Long requestId,
//                                                         @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            User owner = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//            RentRequest rejectedRequest = rentRequestService.rejectRentRequest(requestId, owner);
//            return ResponseEntity.ok(rejectedRequest);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Use BAD_REQUEST for service-level errors
//        }
//    }
//}
// jun 11 works

import com.edelala.mur.entity.RentRequest;
import com.edelala.mur.entity.Property; // Import Property entity
import com.edelala.mur.entity.User;
import com.edelala.mur.service.RentRequestService;
import com.edelala.mur.service.UserService;
import com.edelala.mur.service.PropertyService; // Import PropertyService
import lombok.RequiredArgsConstructor; // For constructor injection
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map; // Keep for createRentRequest method
import java.util.Optional; // Keep for createRentRequest method

//@RestController
//@RequestMapping("/api/rent-requests")
//@RequiredArgsConstructor // Generates constructor for final fields (rentRequestService, userService, propertyService)
//public class RentRequestController {
//
//    private final RentRequestService rentRequestService;
//    private final UserService userService;
//    private final PropertyService propertyService; // Inject PropertyService
//
//
//    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('RENTER') or hasRole('OWNER')")
//    public ResponseEntity<RentRequest> getRentRequestById(@PathVariable Long id,
//                                                          @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            RentRequest rentRequest = rentRequestService.getRentRequestById(id) // Use your service method
//                    .orElseThrow(() -> new RuntimeException("Rent request not found."));
//
//            // Authorization check: Ensure only the renter who made the request
//            // or the owner of the property associated with the request can view it.
//            User currentUser = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Authenticated user not found."));
//
//            if (rentRequest.getRenter().getId().equals(currentUser.getId()) ||
//                    (rentRequest.getProperty() != null &&
//                            rentRequest.getProperty().getOwner().getId().equals(currentUser.getId()))) {
//                // Current user is either the renter of this request OR the owner of the associated property
//                return ResponseEntity.ok(rentRequest);
//            } else {
//                // User is neither the renter nor the owner of this specific request
//                return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 Forbidden
//            }
//
//        } catch (RuntimeException e) {
//            System.err.println("Error fetching rent request by ID " + id + ": " + e.getMessage());
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
//        }
//    }
//
//    /**
//     * Endpoint for renters to submit a new rent request.
//     * The renter's identity is extracted from the JWT in the security context.
//     *
//     * @param requestBody A map containing "propertyId" (as String) and "message".
//     * @param userDetails The authenticated Renter's UserDetails.
//     * @return ResponseEntity with the created RentRequest or an error message.
//     */
//    @PostMapping
//    @PreAuthorize("hasRole('RENTER')")
//    public ResponseEntity<?> createRentRequest(@RequestBody Map<String, Object> requestBody,
//                                               @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            Long propertyId = Long.parseLong(String.valueOf(requestBody.get("propertyId")));
//            String message = String.valueOf(requestBody.get("message"));
//
//            User renter = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Renter not found after authentication."));
//
//            RentRequest newRequest = rentRequestService.createRentRequest(propertyId, message, renter);
//            return new ResponseEntity<>(newRequest, HttpStatus.CREATED);
//        } catch (NumberFormatException e) {
//            return new ResponseEntity<>("Invalid 'propertyId' format. It must be a valid number.", HttpStatus.BAD_REQUEST);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>("An unexpected error occurred while creating rent request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /**
//     * Endpoint for owners to view all rent requests for properties THEY OWN.
//     *
//     * @param userDetails The authenticated Owner's UserDetails.
//     * @return ResponseEntity with a list of RentRequest entities.
//     */
//    @GetMapping("/owner/my-properties-requests")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<List<RentRequest>> getRentRequestsForOwnedProperties(@AuthenticationPrincipal UserDetails userDetails) {
//        User owner = userService.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//        List<RentRequest> requests = rentRequestService.getRentRequestsForOwnerProperties(owner);
//        return ResponseEntity.ok(requests);
//    }
//
//    /**
//     * Endpoint for owners to view rent requests for a SPECIFIC property they own.
//     *
//     * @param propertyId The ID of the property.
//     * @param userDetails The authenticated Owner's UserDetails.
//     * @return ResponseEntity with a list of RentRequest entities for the specific property.
//     */
//    @GetMapping("/owner/property/{propertyId}")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<List<RentRequest>> getRentRequestsByProperty(@PathVariable Long propertyId,
//                                                                       @AuthenticationPrincipal UserDetails userDetails) {
//        User owner = userService.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//        List<RentRequest> rentRequests = rentRequestService.getRentRequestsByPropertyAndOwner(propertyId, owner);
//        return ResponseEntity.ok(rentRequests);
//    }
//
//    /**
//     * Endpoint for renters to view their own submitted rent requests.
//     *
//     * @param userDetails The authenticated Renter's UserDetails.
//     * @return ResponseEntity with a list of RentRequest entities.
//     */
//    @GetMapping("/renter/my-requests")
//    @PreAuthorize("hasRole('RENTER')")
//    public ResponseEntity<List<RentRequest>> getMyRentRequests(@AuthenticationPrincipal UserDetails userDetails) {
//        User renter = userService.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Renter not found after authentication."));
//        List<RentRequest> requests = rentRequestService.getRentRequestsByRenter(renter);
//        return ResponseEntity.ok(requests);
//    }
//
//    /**
//     * Endpoint for owners to approve a specific rent request.
//     * Uses PUT for state change (approval).
//     *
//     * @param requestId The ID of the rent request to approve.
//     * @param userDetails The authenticated Owner's UserDetails (for security check within service).
//     * @return ResponseEntity with the approved RentRequest.
//     */
//    @PutMapping("/owner/{requestId}/approve")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<RentRequest> approveRentRequest(@PathVariable Long requestId,
//                                                          @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            User owner = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//            RentRequest approvedRequest = rentRequestService.approveRentRequest(requestId, owner);
//
//            // FIX: Update property status when request is approved
//            Property property = approvedRequest.getProperty();
//            if (property != null) {
//                property.setAvailableForRent(false); // Mark as unavailable for rent
//                property.setAvailable(false); // Mark as generally unavailable/occupied
//                propertyService.save(property); // FIX: Changed to propertyService.save(property)
//                System.out.println("Property " + property.getId() + " marked as unavailable for rent/occupied due to approved request.");
//            }
//
//            return ResponseEntity.ok(approvedRequest);
//        } catch (RuntimeException e) {
//            System.err.println("Error approving rent request: " + e.getMessage());
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Use BAD_REQUEST for service-level errors
//        }
//    }
//
//    /**
//     * Endpoint for owners to reject a specific rent request.
//     * Uses PUT for state change (rejection).
//     *
//     * @param requestId The ID of the rent request to reject.
//     * @param userDetails The authenticated Owner's UserDetails (for security check within service).
//     * @return ResponseEntity with the rejected RentRequest.
//     */
//    @PutMapping("/owner/{requestId}/reject")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<RentRequest> rejectRentRequest(@PathVariable Long requestId,
//                                                         @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            User owner = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//            RentRequest rejectedRequest = rentRequestService.rejectRentRequest(requestId, owner);
//
//            // FIX: If rejected, ensure property remains/becomes available for rent
//            Property property = rejectedRequest.getProperty();
//            if (property != null) {
//                property.setAvailableForRent(true);
//                property.setAvailable(true); // Mark as generally available again
//                propertyService.save(property); // FIX: Changed to propertyService.save(property)
//                System.out.println("Property " + property.getId() + " remains/becomes available for rent after rejected request.");
//            }
//
//            return ResponseEntity.ok(rejectedRequest);
//        } catch (RuntimeException e) {
//            System.err.println("Error rejecting rent request: " + e.getMessage());
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Use BAD_REQUEST for service-level errors
//        }
//    }
//} //jun 24 comment to upgrade owner dashboard

//@RestController
//@RequestMapping("/api/rent-requests")
//@RequiredArgsConstructor // Generates constructor for final fields (rentRequestService, userService, propertyService)
//public class RentRequestController {
//
//    private final RentRequestService rentRequestService;
//    private final UserService userService;
//    private final PropertyService propertyService; // Inject PropertyService
//
//
//    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('RENTER') or hasRole('OWNER')")
//    public ResponseEntity<RentRequest> getRentRequestById(@PathVariable Long id,
//                                                          @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            RentRequest rentRequest = rentRequestService.getRentRequestById(id) // Use your service method
//                    .orElseThrow(() -> new RuntimeException("Rent request not found."));
//
//            // Authorization check: Ensure only the renter who made the request
//            // or the owner of the property associated with the request can view it.
//            User currentUser = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Authenticated user not found."));
//
//            if (rentRequest.getRenter().getId().equals(currentUser.getId()) ||
//                    (rentRequest.getProperty() != null &&
//                            rentRequest.getProperty().getOwner().getId().equals(currentUser.getId()))) {
//                // Current user is either the renter of this request OR the owner of the associated property
//                return ResponseEntity.ok(rentRequest);
//            } else {
//                // User is neither the renter nor the owner of this specific request
//                return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 Forbidden
//            }
//
//        } catch (RuntimeException e) {
//            System.err.println("Error fetching rent request by ID " + id + ": " + e.getMessage());
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
//        }
//    }
//
//    /**
//     * Endpoint for renters to submit a new rent request.
//     * The renter's identity is extracted from the JWT in the security context.
//     *
//     * @param requestBody A map containing "propertyId" (as String) and "message".
//     * @param userDetails The authenticated Renter's UserDetails.
//     * @return ResponseEntity with the created RentRequest or an error message.
//     */
//    @PostMapping
//    @PreAuthorize("hasRole('RENTER')")
//    public ResponseEntity<?> createRentRequest(@RequestBody Map<String, Object> requestBody,
//                                               @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            Long propertyId = Long.parseLong(String.valueOf(requestBody.get("propertyId")));
//            String message = String.valueOf(requestBody.get("message"));
//
//            User renter = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Renter not found after authentication."));
//
//            RentRequest newRequest = rentRequestService.createRentRequest(propertyId, message, renter);
//            return new ResponseEntity<>(newRequest, HttpStatus.CREATED);
//        } catch (NumberFormatException e) {
//            return new ResponseEntity<>("Invalid 'propertyId' format. It must be a valid number.", HttpStatus.BAD_REQUEST);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>("An unexpected error occurred while creating rent request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /**
//     * Endpoint for owners to view all rent requests for properties THEY OWN.
//     *
//     * @param userDetails The authenticated Owner's UserDetails.
//     * @return ResponseEntity with a list of RentRequest entities.
//     */
//    @GetMapping("/owner/my-properties-requests")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<List<RentRequest>> getRentRequestsForOwnedProperties(@AuthenticationPrincipal UserDetails userDetails) {
//        User owner = userService.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//        List<RentRequest> requests = rentRequestService.getRentRequestsForOwnerProperties(owner);
//        return ResponseEntity.ok(requests);
//    }
//
//    /**
//     * Endpoint for owners to view rent requests for a SPECIFIC property they own.
//     *
//     * @param propertyId The ID of the property.
//     * @param userDetails The authenticated Owner's UserDetails.
//     * @return ResponseEntity with a list of RentRequest entities for the specific property.
//     */
//    @GetMapping("/owner/property/{propertyId}")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<List<RentRequest>> getRentRequestsByProperty(@PathVariable Long propertyId,
//                                                                       @AuthenticationPrincipal UserDetails userDetails) {
//        User owner = userService.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//        List<RentRequest> rentRequests = rentRequestService.getRentRequestsByPropertyAndOwner(propertyId, owner);
//        return ResponseEntity.ok(rentRequests);
//    }
//
//    /**
//     * Endpoint for renters to view their own submitted rent requests.
//     *
//     * @param userDetails The authenticated Renter's UserDetails.
//     * @return ResponseEntity with a list of RentRequest entities.
//     */
//    @GetMapping("/renter/my-requests")
//    @PreAuthorize("hasRole('RENTER')")
//    public ResponseEntity<List<RentRequest>> getMyRentRequests(@AuthenticationPrincipal UserDetails userDetails) {
//        User renter = userService.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("Renter not found after authentication."));
//        List<RentRequest> requests = rentRequestService.getRentRequestsByRenter(renter);
//        return ResponseEntity.ok(requests);
//    }
//
//    /**
//     * Endpoint for owners to approve a specific rent request.
//     * Uses PUT for state change (approval).
//     *
//     * @param requestId The ID of the rent request to approve.
//     * @param userDetails The authenticated Owner's UserDetails (for security check within service).
//     * @return ResponseEntity with the approved RentRequest.
//     */
//    @PutMapping("/owner/{requestId}/approve")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<RentRequest> approveRentRequest(@PathVariable Long requestId,
//                                                          @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            User owner = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//            RentRequest approvedRequest = rentRequestService.approveRentRequest(requestId, owner);
//
//            // Removed: propertyService.save(property);
//            // The markAsRented method in RentRequestService already updates and saves the property status.
//
//            return ResponseEntity.ok(approvedRequest);
//        } catch (RuntimeException e) {
//            System.err.println("Error approving rent request: " + e.getMessage());
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Use BAD_REQUEST for service-level errors
//        }
//    }
//
//    /**
//     * Endpoint for owners to reject a specific rent request.
//     * Uses PUT for state change (rejection).
//     *
//     * @param requestId The ID of the rent request to reject.
//     * @param userDetails The authenticated Owner's UserDetails (for security check within service).
//     * @return ResponseEntity with the rejected RentRequest.
//     */
//    @PutMapping("/owner/{requestId}/reject")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<RentRequest> rejectRentRequest(@PathVariable Long requestId,
//                                                         @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            User owner = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//            RentRequest rejectedRequest = rentRequestService.rejectRentRequest(requestId, owner);
//
//            // Removed: propertyService.save(property);
//            // The markAsAvailableForRent method in RentRequestService already updates and saves the property status.
//
//            return ResponseEntity.ok(rejectedRequest);
//        } catch (RuntimeException e) {
//            System.err.println("Error rejecting rent request: " + e.getMessage());
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Use BAD_REQUEST for service-level errors
//        }
//    }
//} july 13 comment


//@RestController
//@RequestMapping("/api/rent-requests")
//@RequiredArgsConstructor // Generates constructor for final fields (rentRequestService, userService, propertyService)
//public class RentRequestController {
//
//    private final RentRequestService rentRequestService;
//    private final UserService userService;
//    private final PropertyService propertyService; // Inject PropertyService
//
//
//    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('RENTER') or hasRole('OWNER')")
//    public ResponseEntity<RentRequest> getRentRequestById(@PathVariable Long id,
//                                                          @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            RentRequest rentRequest = rentRequestService.getRentRequestById(id) // Use your service method
//                    .orElseThrow(() -> new RuntimeException("Rent request not found."));
//
//            // Authorization check: Ensure only the renter who made the request
//            // or the owner of the property associated with the request can view it.
//            User currentUser = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Authenticated user not found."));
//
//            if (rentRequest.getRenter().getId().equals(currentUser.getId()) ||
//                    (rentRequest.getProperty() != null &&
//                            rentRequest.getProperty().getOwner().getId().equals(currentUser.getId()))) {
//                // Current user is either the renter of this request OR the owner of the associated property
//                return ResponseEntity.ok(rentRequest);
//            } else {
//                // User is neither the renter nor the owner of this specific request
//                return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 Forbidden
//            }
//
//        } catch (RuntimeException e) {
//            System.err.println("Error fetching rent request by ID " + id + ": " + e.getMessage());
//            e.printStackTrace(); // Add stack trace for debugging
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
//        }
//    }
//
//    /**
//     * Endpoint for renters to submit a new rent request.
//     * The renter's identity is extracted from the JWT in the security context.
//     *
//     * @param requestBody A map containing "propertyId" (as String) and "message".
//     * @param userDetails The authenticated Renter's UserDetails.
//     * @return ResponseEntity with the created RentRequest or an error message.
//     */
//    @PostMapping
//    @PreAuthorize("hasRole('RENTER')")
//    public ResponseEntity<?> createRentRequest(@RequestBody Map<String, Object> requestBody,
//                                               @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            Long propertyId = Long.parseLong(String.valueOf(requestBody.get("propertyId")));
//            String message = String.valueOf(requestBody.get("message"));
//
//            User renter = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Renter not found after authentication."));
//
//            RentRequest newRequest = rentRequestService.createRentRequest(propertyId, message, renter);
//            return new ResponseEntity<>(newRequest, HttpStatus.CREATED);
//        } catch (NumberFormatException e) {
//            System.err.println("Error creating rent request (NumberFormatException): " + e.getMessage());
//            e.printStackTrace();
//            return new ResponseEntity<>("Invalid 'propertyId' format. It must be a valid number.", HttpStatus.BAD_REQUEST);
//        } catch (IllegalArgumentException e) {
//            System.err.println("Error creating rent request (IllegalArgumentException): " + e.getMessage());
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (RuntimeException e) {
//            System.err.println("Error creating rent request (RuntimeException): " + e.getMessage());
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            System.err.println("An unexpected error occurred while creating rent request: " + e.getMessage());
//            e.printStackTrace();
//            return new ResponseEntity<>("An unexpected error occurred while creating rent request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /**
//     * Endpoint for owners to view all rent requests for properties THEY OWN.
//     *
//     * @param userDetails The authenticated Owner's UserDetails.
//     * @return ResponseEntity with a list of RentRequest entities.
//     */
//    @GetMapping("/owner/my-properties-requests")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<List<RentRequest>> getRentRequestsForOwnedProperties(@AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            User owner = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//            List<RentRequest> requests = rentRequestService.getRentRequestsForOwnerProperties(owner);
//            return ResponseEntity.ok(requests);
//        } catch (Exception e) {
//            System.err.println("Error fetching rent requests for owned properties: " + e.getMessage());
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /**
//     * Endpoint for owners to view rent requests for a SPECIFIC property they own.
//     *
//     * @param propertyId The ID of the property.
//     * @param userDetails The authenticated Owner's UserDetails.
//     * @return ResponseEntity with a list of RentRequest entities for the specific property.
//     */
//    @GetMapping("/owner/property/{propertyId}")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<List<RentRequest>> getRentRequestsByProperty(@PathVariable Long propertyId,
//                                                                       @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            User owner = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//            List<RentRequest> rentRequests = rentRequestService.getRentRequestsByPropertyAndOwner(propertyId, owner);
//            return ResponseEntity.ok(rentRequests);
//        } catch (RuntimeException e) {
//            System.err.println("Error fetching rent requests by property: " + e.getMessage());
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            System.err.println("An unexpected error occurred while fetching rent requests by property: " + e.getMessage());
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /**
//     * Endpoint for renters to view their own submitted rent requests.
//     *
//     * @param userDetails The authenticated Renter's UserDetails.
//     * @return ResponseEntity with a list of RentRequest entities.
//     */
//    @GetMapping("/renter/my-requests")
//    @PreAuthorize("hasRole('RENTER')")
//    public ResponseEntity<List<RentRequest>> getMyRentRequests(@AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            User renter = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Renter not found after authentication."));
//            List<RentRequest> requests = rentRequestService.getRentRequestsByRenter(renter);
//            return ResponseEntity.ok(requests);
//        } catch (Exception e) {
//            System.err.println("Error fetching renter's own rent requests: " + e.getMessage());
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /**
//     * Endpoint for owners to approve a specific rent request.
//     * Uses PUT for state change (approval).
//     *
//     * @param requestId The ID of the rent request to approve.
//     * @param userDetails The authenticated Owner's UserDetails (for security check within service).
//     * @return ResponseEntity with the approved RentRequest.
//     */
//    @PutMapping("/owner/{requestId}/approve")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<RentRequest> approveRentRequest(@PathVariable Long requestId,
//                                                          @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            User owner = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//            RentRequest approvedRequest = rentRequestService.approveRentRequest(requestId, owner);
//            return ResponseEntity.ok(approvedRequest);
//        } catch (RuntimeException e) {
//            System.err.println("Error approving rent request: " + e.getMessage());
//            e.printStackTrace(); // Add stack trace for debugging
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Use BAD_REQUEST for service-level errors
//        } catch (Exception e) {
//            System.err.println("An unexpected error occurred while approving rent request: " + e.getMessage());
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /**
//     * Endpoint for owners to reject a specific rent request.
//     * Uses PUT for state change (rejection).
//     *
//     * @param requestId The ID of the rent request to reject.
//     * @param userDetails The authenticated Owner's UserDetails (for security check within service).
//     * @return ResponseEntity with the rejected RentRequest.
//     */
//    @PutMapping("/owner/{requestId}/reject")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<RentRequest> rejectRentRequest(@PathVariable Long requestId,
//                                                         @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            User owner = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//            RentRequest rejectedRequest = rentRequestService.rejectRentRequest(requestId, owner);
//            return ResponseEntity.ok(rejectedRequest);
//        } catch (RuntimeException e) {
//            System.err.println("Error rejecting rent request: " + e.getMessage());
//            e.printStackTrace(); // Add stack trace for debugging
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Use BAD_REQUEST for service-level errors
//        } catch (Exception e) {
//            System.err.println("An unexpected error occurred while rejecting rent request: " + e.getMessage());
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}
//
//this is working code i comment and add same code for testing purpose only

//@RestController
//@RequestMapping("/api/rent-requests")
//@RequiredArgsConstructor // Generates constructor for final fields (rentRequestService, userService, propertyService)
//public class RentRequestController {
//
//    private final RentRequestService rentRequestService;
//    private final UserService userService;
//    private final PropertyService propertyService; // Inject PropertyService
//
//
//    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('RENTER') or hasRole('OWNER')")
//    public ResponseEntity<RentRequest> getRentRequestById(@PathVariable Long id,
//                                                          @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            RentRequest rentRequest = rentRequestService.getRentRequestById(id) // Use your service method
//                    .orElseThrow(() -> new RuntimeException("Rent request not found."));
//
//            // Authorization check: Ensure only the renter who made the request
//            // or the owner of the property associated with the request can view it.
//            User currentUser = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Authenticated user not found."));
//
//            if (rentRequest.getRenter().getId().equals(currentUser.getId()) ||
//                    (rentRequest.getProperty() != null &&
//                            rentRequest.getProperty().getOwner().getId().equals(currentUser.getId()))) {
//                // Current user is either the renter of this request OR the owner of the associated property
//                return ResponseEntity.ok(rentRequest);
//            } else {
//                // User is neither the renter nor the owner of this specific request
//                return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 Forbidden
//            }
//
//        } catch (RuntimeException e) {
//            System.err.println("Error fetching rent request by ID " + id + ": " + e.getMessage());
//            e.printStackTrace(); // Add stack trace for debugging
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
//        }
//    }
//
//    /**
//     * Endpoint for renters to submit a new rent request.
//     * The renter's identity is extracted from the JWT in the security context.
//     *
//     * @param requestBody A map containing "propertyId" (as String) and "message".
//     * @param userDetails The authenticated Renter's UserDetails.
//     * @return ResponseEntity with the created RentRequest or an error message.
//     */
//    @PostMapping
//    @PreAuthorize("hasRole('RENTER')")
//    public ResponseEntity<?> createRentRequest(@RequestBody Map<String, Object> requestBody,
//                                               @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            Long propertyId = Long.parseLong(String.valueOf(requestBody.get("propertyId")));
//            String message = String.valueOf(requestBody.get("message"));
//
//            User renter = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Renter not found after authentication."));
//
//            RentRequest newRequest = rentRequestService.createRentRequest(propertyId, message, renter);
//            return new ResponseEntity<>(newRequest, HttpStatus.CREATED);
//        } catch (NumberFormatException e) {
//            System.err.println("Error creating rent request (NumberFormatException): " + e.getMessage());
//            e.printStackTrace();
//            return new ResponseEntity<>("Invalid 'propertyId' format. It must be a valid number.", HttpStatus.BAD_REQUEST);
//        } catch (IllegalArgumentException e) {
//            System.err.println("Error creating rent request (IllegalArgumentException): " + e.getMessage());
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (RuntimeException e) {
//            System.err.println("Error creating rent request (RuntimeException): " + e.getMessage());
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            System.err.println("An unexpected error occurred while creating rent request: " + e.getMessage());
//            e.printStackTrace();
//            return new ResponseEntity<>("An unexpected error occurred while creating rent request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /**
//     * Endpoint for owners to view all rent requests for properties THEY OWN.
//     *
//     * @param userDetails The authenticated Owner's UserDetails.
//     * @return ResponseEntity with a list of RentRequest entities.
//     */
//    @GetMapping("/owner/my-properties-requests")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<List<RentRequest>> getRentRequestsForOwnedProperties(@AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            User owner = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//            List<RentRequest> requests = rentRequestService.getRentRequestsForOwnerProperties(owner);
//            return ResponseEntity.ok(requests);
//        } catch (Exception e) {
//            System.err.println("Error fetching rent requests for owned properties: " + e.getMessage());
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /**
//     * Endpoint for owners to view rent requests for a SPECIFIC property they own.
//     *
//     * @param propertyId The ID of the property.
//     * @param userDetails The authenticated Owner's UserDetails.
//     * @return ResponseEntity with a list of RentRequest entities for the specific property.
//     */
//    @GetMapping("/owner/property/{propertyId}")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<List<RentRequest>> getRentRequestsByProperty(@PathVariable Long propertyId,
//                                                                       @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            User owner = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//            List<RentRequest> rentRequests = rentRequestService.getRentRequestsByPropertyAndOwner(propertyId, owner);
//            return ResponseEntity.ok(rentRequests);
//        } catch (RuntimeException e) {
//            System.err.println("Error fetching rent requests by property: " + e.getMessage());
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            System.err.println("An unexpected error occurred while fetching rent requests by property: " + e.getMessage());
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /**
//     * Endpoint for renters to view their own submitted rent requests.
//     *
//     * @param userDetails The authenticated Renter's UserDetails.
//     * @return ResponseEntity with a list of RentRequest entities.
//     */
//    @GetMapping("/renter/my-requests")
//    @PreAuthorize("hasRole('RENTER')")
//    public ResponseEntity<List<RentRequest>> getMyRentRequests(@AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            User renter = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Renter not found after authentication."));
//            List<RentRequest> requests = rentRequestService.getRentRequestsByRenter(renter);
//            return ResponseEntity.ok(requests);
//        } catch (Exception e) {
//            System.err.println("Error fetching renter's own rent requests: " + e.getMessage());
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /**
//     * Endpoint for owners to approve a specific rent request.
//     * Uses PUT for state change (approval).
//     *
//     * @param requestId The ID of the rent request to approve.
//     * @param userDetails The authenticated Owner's UserDetails (for security check within service).
//     * @return ResponseEntity with the approved RentRequest.
//     */
//    @PutMapping("/owner/{requestId}/approve")
//    @PreAuthorize("hasRole('OWNER')") // Re-enabled
//    public ResponseEntity<RentRequest> approveRentRequest(@PathVariable Long requestId,
//                                                          @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            User owner = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//            RentRequest approvedRequest = rentRequestService.approveRentRequest(requestId, owner);
//            return ResponseEntity.ok(approvedRequest);
//        } catch (Exception e) { // Catch all exceptions here
//            System.err.println("Error approving rent request: " + e.getMessage());
//            e.printStackTrace(); // Print stack trace to backend console
//
//            // For debugging, send the full stack trace to the frontend.
//            // THIS SHOULD BE REMOVED IN PRODUCTION FOR SECURITY REASONS.
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter(sw);
//            e.printStackTrace(pw);
//            String stackTrace = sw.toString();
//
//            return new ResponseEntity("Error approving rent request: " + e.getMessage() + "\n\nStack Trace:\n" + stackTrace, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /**
//     * Endpoint for owners to reject a specific rent request.
//     * Uses PUT for state change (rejection).
//     *
//     * @param requestId The ID of the rent request to reject.
//     * @param userDetails The authenticated Owner's UserDetails (for security check within service).
//     * @return ResponseEntity with the rejected RentRequest.
//     */
//    @PutMapping("/owner/{requestId}/reject")
//    @PreAuthorize("hasRole('OWNER')")
//    public ResponseEntity<RentRequest> rejectRentRequest(@PathVariable Long requestId,
//                                                         @AuthenticationPrincipal UserDetails userDetails) {
//        try {
//            User owner = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
//            RentRequest rejectedRequest = rentRequestService.rejectRentRequest(requestId, owner);
//            return ResponseEntity.ok(rejectedRequest);
//        } catch (Exception e) {
//            System.err.println("Error rejecting rent request: " + e.getMessage());
//            e.printStackTrace(); // Add stack trace for debugging
//
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter(sw);
//            e.printStackTrace(pw);
//            String stackTrace = sw.toString();
//
//            return new ResponseEntity("Error rejecting rent request: " + e.getMessage() + "\n\nStack Trace:\n" + stackTrace, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}

//July 15

@RestController
@RequestMapping("/api/rent-requests")
@RequiredArgsConstructor // Generates constructor for final fields (rentRequestService, userService, propertyService)
public class RentRequestController {

    private final RentRequestService rentRequestService;
    private final UserService userService;
    private final PropertyService propertyService; // Inject PropertyService


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('RENTER') or hasRole('OWNER')")
    public ResponseEntity<RentRequest> getRentRequestById(@PathVariable Long id,
                                                          @AuthenticationPrincipal UserDetails userDetails) {
        try {
            RentRequest rentRequest = rentRequestService.getRentRequestById(id) // Use your service method
                    .orElseThrow(() -> new RuntimeException("Rent request not found."));

            // Authorization check: Ensure only the renter who made the request
            // or the owner of the property associated with the request can view it.
            User currentUser = userService.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Authenticated user not found."));

            if (rentRequest.getRenter().getId().equals(currentUser.getId()) ||
                    (rentRequest.getProperty() != null &&
                            rentRequest.getProperty().getOwner().getId().equals(currentUser.getId()))) {
                // Current user is either the renter of this request OR the owner of the associated property
                return ResponseEntity.ok(rentRequest);
            } else {
                // User is neither the renter nor the owner of this specific request
                return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 Forbidden
            }

        } catch (RuntimeException e) {
            System.err.println("Error fetching rent request by ID " + id + ": " + e.getMessage());
            e.printStackTrace(); // Add stack trace for debugging
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    /**
     * Endpoint for renters to submit a new rent request.
     * The renter's identity is extracted from the JWT in the security context.
     *
     * @param requestBody A map containing "propertyId" (as String) and "message".
     * @param userDetails The authenticated Renter's UserDetails.
     * @return ResponseEntity with the created RentRequest or an error message.
     */
    @PostMapping
    @PreAuthorize("hasRole('RENTER')")
    public ResponseEntity<?> createRentRequest(@RequestBody Map<String, Object> requestBody,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Long propertyId = Long.parseLong(String.valueOf(requestBody.get("propertyId")));
            String message = String.valueOf(requestBody.get("message"));

            User renter = userService.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Renter not found after authentication."));

            RentRequest newRequest = rentRequestService.createRentRequest(propertyId, message, renter);
            return new ResponseEntity<>(newRequest, HttpStatus.CREATED);
        } catch (NumberFormatException e) {
            System.err.println("Error creating rent request (NumberFormatException): " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("Invalid 'propertyId' format. It must be a valid number.", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            System.err.println("Error creating rent request (IllegalArgumentException): " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            System.err.println("Error creating rent request (RuntimeException): " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while creating rent request: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("An unexpected error occurred while creating rent request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint for owners to view all rent requests for properties THEY OWN.
     *
     * @param userDetails The authenticated Owner's UserDetails.
     * @return ResponseEntity with a list of RentRequest entities.
     */
    @GetMapping("/owner/my-properties-requests")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<RentRequest>> getRentRequestsForOwnedProperties(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User owner = userService.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
            List<RentRequest> requests = rentRequestService.getRentRequestsForOwnerProperties(owner);
            return ResponseEntity.ok(requests);
        } catch (Exception e) {
            System.err.println("Error fetching rent requests for owned properties: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint for owners to view rent requests for a SPECIFIC property they own.
     *
     * @param propertyId The ID of the property.
     * @param userDetails The authenticated Owner's UserDetails.
     * @return ResponseEntity with a list of RentRequest entities for the specific property.
     */
    @GetMapping("/owner/property/{propertyId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<RentRequest>> getRentRequestsByProperty(@PathVariable Long propertyId,
                                                                       @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User owner = userService.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
            List<RentRequest> rentRequests = rentRequestService.getRentRequestsByPropertyAndOwner(propertyId, owner);
            return ResponseEntity.ok(rentRequests);
        } catch (RuntimeException e) {
            System.err.println("Error fetching rent requests by property: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while fetching rent requests by property: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint for renters to view their own submitted rent requests.
     *
     * @param userDetails The authenticated Renter's UserDetails.
     * @return ResponseEntity with a list of RentRequest entities.
     */
    @GetMapping("/renter/my-requests")
    @PreAuthorize("hasRole('RENTER')")
    public ResponseEntity<List<RentRequest>> getMyRentRequests(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User renter = userService.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Renter not found after authentication."));
            List<RentRequest> requests = rentRequestService.getRentRequestsByRenter(renter);
            return ResponseEntity.ok(requests);
        } catch (Exception e) {
            System.err.println("Error fetching renter's own rent requests: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint for owners to approve a specific rent request.
     * Uses PUT for state change (approval).
     *
     * @param requestId The ID of the rent request to approve.
     * @param userDetails The authenticated Owner's UserDetails (for security check within service).
     * @return ResponseEntity with the approved RentRequest.
     */
    // CHANGED: Removed @PathVariable from path, added @RequestParam to method parameter
    @PutMapping("/owner/approve") // Corrected path to match frontend's query param approach
    @PreAuthorize("hasRole('OWNER')") // Re-enabled @PreAuthorize
    public ResponseEntity<RentRequest> approveRentRequest(@RequestParam Long requestId, // Changed to @RequestParam
                                                          @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User owner = userService.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
            RentRequest approvedRequest = rentRequestService.approveRentRequest(requestId, owner);
            return ResponseEntity.ok(approvedRequest);
        } catch (Exception e) { // Catch all exceptions here
            System.err.println("Error approving rent request: " + e.getMessage());
            e.printStackTrace(); // Print stack trace to backend console

            // For debugging, send the full stack trace to the frontend.
            // THIS SHOULD BE REMOVED IN PRODUCTION FOR SECURITY REASONS.
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();

            return new ResponseEntity("Error approving rent request: " + e.getMessage() + "\n\nStack Trace:\n" + stackTrace, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint for owners to reject a specific rent request.
     * Uses PUT for state change (rejection).
     *
     * @param requestId The ID of the rent request to reject.
     * @param userDetails The authenticated Owner's UserDetails (for security check within service).
     * @return ResponseEntity with the rejected RentRequest.
     */
    @PutMapping("/owner/{requestId}/reject")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<RentRequest> rejectRentRequest(@PathVariable Long requestId,
                                                         @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User owner = userService.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Owner not found after authentication."));
            RentRequest rejectedRequest = rentRequestService.rejectRentRequest(requestId, owner);
            return ResponseEntity.ok(rejectedRequest);
        } catch (Exception e) {
            System.err.println("Error rejecting rent request: " + e.getMessage());
            e.printStackTrace(); // Add stack trace for debugging

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();

            return new ResponseEntity("Error rejecting rent request: " + e.getMessage() + "\n\nStack Trace:\n" + stackTrace, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
