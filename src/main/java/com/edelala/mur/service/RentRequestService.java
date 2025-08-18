package com.edelala.mur.service;

import com.edelala.mur.entity.Property;
import com.edelala.mur.entity.RentRequest;
import com.edelala.mur.entity.User;; // CORRECTED: This should be the correct import path
import com.edelala.mur.exception.ResourceNotFoundException;
import com.edelala.mur.repo.PropertyRepository;
import com.edelala.mur.repo.RentRequestRepository;
import com.edelala.mur.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


//jun 23 final code
//@Service
//@RequiredArgsConstructor
//public class RentRequestService {
//
//    private final RentRequestRepository rentRequestRepository;
//    private final EmailService emailService;
//    private final PropertyService propertyService;
//    private final PropertyRepository propertyRepository;
//
//
//    /**
//     * Creates a new rent request for a specified property by a renter.
//     * Notifies the property owner via email.
//     *
//     * @param propertyId The ID of the property for which the rent request is being made.
//     * @param message The message from the renter.
//     * @param renter The User entity of the renter.
//     * @return The created and saved RentRequest entity.
//     * @throws RuntimeException if the property is not found or is not available for rent.
//     */
//    @Transactional
//    public RentRequest createRentRequest(Long propertyId, String message, User renter) {
//        Property property = propertyService.getPropertyById(propertyId)
//                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + propertyId));
//
//        if (!property.isAvailableForRent()) {
//            throw new RuntimeException("Property is not available for rent.");
//        }
//
//        // Check if there's an existing PENDING or APPROVED request for this property by this renter
//        boolean hasExistingActiveRequest = rentRequestRepository
//                .findByPropertyAndRenter(property, renter)
//                .stream()
//                .anyMatch(req -> req.getIsApproved() == null || req.getIsApproved() == true);
//
//        if (hasExistingActiveRequest) {
//            throw new IllegalArgumentException("You already have a pending or approved rent request for this property.");
//        }
//
//        RentRequest rentRequest = new RentRequest();
//        rentRequest.setProperty(property);
//        rentRequest.setRenter(renter);
//        rentRequest.setMessage(message);
//        rentRequest.setRequestDate(LocalDateTime.now());
//        rentRequest.setIsApproved(null); // Null for a new, pending request
//        rentRequest.setPaymentStatus("pending"); // Initialize payment status
//
//        RentRequest savedRequest = rentRequestRepository.save(rentRequest);
//
//        // Notify the owner about the new rent request
//        emailService.sendRentRequestNotification(property.getOwner(), renter, property);
//
//        return savedRequest;
//    }
//
//    /**
//     * Retrieves all rent requests for properties owned by a specific owner.
//     *
//     * @param owner The User entity representing the owner.
//     * @return A list of RentRequest entities for the owner's properties.
//     */
//    @Transactional(readOnly = true)
//    public List<RentRequest> getRentRequestsForOwnerProperties(User owner) {
//        List<Property> ownedProperties = propertyRepository.findByOwner(owner);
//        return ownedProperties.stream()
//                .flatMap(property -> rentRequestRepository.findByProperty(property).stream())
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * Retrieves all rent requests for a specific property, ensuring the requesting owner
//     * actually owns that property.
//     *
//     * @param propertyId The ID of the property.
//     * @param owner The User entity representing the owner making the request.
//     * @return A list of RentRequest entities for the given property and owner.
//     * @throws RuntimeException if the property is not found or not owned by the user.
//     */
//    @Transactional(readOnly = true)
//    public List<RentRequest> getRentRequestsByPropertyAndOwner(Long propertyId, User owner) {
//        Property property = propertyService.getPropertyById(propertyId)
//                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + propertyId));
//
//        if (!property.getOwner().getId().equals(owner.getId())) {
//            throw new RuntimeException("Property is not owned by the authenticated owner.");
//        }
//        return rentRequestRepository.findByProperty(property);
//    }
//
//    /**
//     * Retrieves all rent requests made by a specific renter.
//     *
//     * @param renter The User entity of the renter.
//     * @return A list of RentRequest entities made by the renter.
//     */
//    @Transactional(readOnly = true)
//    public List<RentRequest> getRentRequestsByRenter(User renter) {
//        return rentRequestRepository.findByRenter(renter);
//    }
//
//    /**
//     * Approves a specific rent request, ensuring the requesting owner
//     * actually owns the property associated with the request.
//     *
//     * @param requestId The ID of the rent request to approve.
//     * @param owner The User entity of the owner attempting to approve.
//     * @return The updated RentRequest entity.
//     * @throws RuntimeException if the rent request is not found or not owned by the user.
//     */
//    @Transactional
//    public RentRequest approveRentRequest(Long requestId, User owner) {
//        RentRequest rentRequest = rentRequestRepository.findById(requestId)
//                .orElseThrow(() -> new RuntimeException("Rent request not found with ID: " + requestId));
//
//        if (!rentRequest.getProperty().getOwner().getId().equals(owner.getId())) {
//            throw new RuntimeException("You do not have permission to approve this rent request.");
//        }
//
//        if (rentRequest.getIsApproved() != null) {
//            throw new IllegalStateException("Rent request has already been processed (approved/rejected).");
//        }
//
//        rentRequest.setIsApproved(true);
//        RentRequest savedRequest = rentRequestRepository.save(rentRequest);
//
//        // FIX: Pass the owner to the markAsRented method
//        propertyService.markAsRented(rentRequest.getProperty().getId(), owner);
//
//        emailService.sendRentRequestApprovalNotification(rentRequest.getProperty().getOwner(), rentRequest.getRenter(), rentRequest.getProperty());
//
//        return savedRequest;
//    }
//
//    /**
//     * Rejects a specific rent request, ensuring the requesting owner
//     * actually owns the property associated with the request.
//     *
//     * @param requestId The ID of the rent request to reject.
//     * @param owner The User entity of the owner attempting to reject.
//     * @return The updated RentRequest entity.
//     * @throws RuntimeException if the rent request is not found or not owned by the user.
//     */
//    @Transactional
//    public RentRequest rejectRentRequest(Long requestId, User owner) {
//        RentRequest rentRequest = rentRequestRepository.findById(requestId)
//                .orElseThrow(() -> new RuntimeException("Rent request not found with ID: " + requestId));
//
//        if (!rentRequest.getProperty().getOwner().getId().equals(owner.getId())) {
//            throw new RuntimeException("You do not have permission to reject this rent request.");
//        }
//
//        if (rentRequest.getIsApproved() != null) {
//            throw new IllegalStateException("Rent request has already been processed (approved/rejected).");
//        }
//
//        rentRequest.setIsApproved(false); // Mark as not approved
//        RentRequest savedRequest = rentRequestRepository.save(rentRequest);
//
//        emailService.sendRentRequestRejectionNotification(rentRequest.getProperty().getOwner(), rentRequest.getRenter(), rentRequest.getProperty());
//
//        return savedRequest;
//    }
//
//    /**
//     * Retrieves a single rent request by its ID.
//     *
//     * @param requestId The ID of the rent request.
//     * @return An Optional containing the RentRequest if found, or empty if not.
//     */
//    @Transactional(readOnly = true)
//    public Optional<RentRequest> getRentRequestById(Long requestId) {
//        return rentRequestRepository.findById(requestId);
//    }
//
//    /**
//     * Updates the payment status of a specific rent request.
//     *
//     * @param rentRequestId The ID of the rent request to update.
//     * @param newPaymentStatus The new payment status (e.g., "PAID", "PENDING", "FAILED").
//     * @throws RuntimeException if the rent request is not found.
//     */
//    @Transactional
//    public void updatePaymentStatus(Long rentRequestId, String newPaymentStatus) {
//        RentRequest rentRequest = rentRequestRepository.findById(rentRequestId)
//                .orElseThrow(() -> new RuntimeException("Rent request not found with ID: " + rentRequestId));
//        rentRequest.setPaymentStatus(newPaymentStatus);
//        rentRequestRepository.save(rentRequest);
//    }
//} //comment jul 10 to improve filter

//@Service
//@RequiredArgsConstructor
//public class RentRequestService {
//
//    private final RentRequestRepository rentRequestRepository;
//    private final EmailService emailService;
//    private final PropertyService propertyService;
//    private final PropertyRepository propertyRepository;
//
//
//    /**
//     * Creates a new rent request for a specified property by a renter.
//     * Notifies the property owner via email.
//     *
//     * @param propertyId The ID of the property for which the rent request is being made.
//     * @param message The message from the renter.
//     * @param renter The User entity of the renter.
//     * @return The created and saved RentRequest entity.
//     * @throws RuntimeException if the property is not found or is not available for rent.
//     */
//    @Transactional
//    public RentRequest createRentRequest(Long propertyId, String message, User renter) {
//        try {
//            Property property = propertyRepository.findById(propertyId)
//                    .orElseThrow(() -> new RuntimeException("Property not found with ID: " + propertyId));
//
//            if (!property.getAvailableForRent()) {
//                throw new RuntimeException("Property is not available for rent.");
//            }
//
//            // Check if there's an existing PENDING or APPROVED request for this property by this renter
//            boolean hasExistingActiveRequest = rentRequestRepository
//                    .findByPropertyAndRenter(property, renter)
//                    .stream()
//                    .anyMatch(req -> req.getIsApproved() == null || req.getIsApproved() == true);
//
//            if (hasExistingActiveRequest) {
//                throw new IllegalArgumentException("You already have a pending or approved rent request for this property.");
//            }
//
//            RentRequest rentRequest = new RentRequest();
//            rentRequest.setProperty(property);
//            rentRequest.setRenter(renter);
//            rentRequest.setMessage(message);
//            rentRequest.setRequestDate(LocalDateTime.now());
//            rentRequest.setIsApproved(null); // Null for a new, pending request
//            rentRequest.setPaymentStatus("pending"); // Initialize payment status
//
//            RentRequest savedRequest = rentRequestRepository.save(rentRequest);
//
//            // Notify the owner about the new rent request
//            emailService.sendRentRequestNotification(property.getOwner(), renter, property);
//
//            return savedRequest;
//        } catch (Exception e) {
//            System.err.println("RentRequestService Error in createRentRequest: " + e.getMessage());
//            e.printStackTrace();
//            throw e; // Re-throw to be caught by controller
//        }
//    }
//
//    /**
//     * Retrieves all rent requests for properties owned by a specific owner.
//     *
//     * @param owner The User entity representing the owner.
//     * @return A list of RentRequest entities for the owner's properties.
//     */
//    @Transactional(readOnly = true)
//    public List<RentRequest> getRentRequestsForOwnerProperties(User owner) {
//        try {
//            List<Property> ownedProperties = propertyRepository.findByOwnerId(owner.getId());
//            return ownedProperties.stream()
//                    .flatMap(property -> rentRequestRepository.findByProperty(property).stream())
//                    .collect(Collectors.toList());
//        } catch (Exception e) {
//            System.err.println("RentRequestService Error in getRentRequestsForOwnerProperties: " + e.getMessage());
//            e.printStackTrace();
//            throw e;
//        }
//    }
//
//    /**
//     * Retrieves all rent requests for a specific property, ensuring the requesting owner
//     * actually owns that property.
//     *
//     * @param propertyId The ID of the property.
//     * @param owner The User entity representing the owner making the request.
//     * @return A list of RentRequest entities for the given property and owner.
//     * @throws RuntimeException if the property is not found or not owned by the user.
//     */
//    @Transactional(readOnly = true)
//    public List<RentRequest> getRentRequestsByPropertyAndOwner(Long propertyId, User owner) {
//        try {
//            Property property = propertyRepository.findById(propertyId)
//                    .orElseThrow(() -> new RuntimeException("Property not found with ID: " + propertyId));
//
//            if (!property.getOwner().getId().equals(owner.getId())) {
//                throw new RuntimeException("Property is not owned by the authenticated owner.");
//            }
//            return rentRequestRepository.findByProperty(property);
//        } catch (Exception e) {
//            System.err.println("RentRequestService Error in getRentRequestsByPropertyAndOwner: " + e.getMessage());
//            e.printStackTrace();
//            throw e;
//        }
//    }
//
//    /**
//     * Retrieves all rent requests made by a specific renter.
//     *
//     * @param renter The User entity of the renter.
//     * @return A list of RentRequest entities made by the renter.
//     */
//    @Transactional(readOnly = true)
//    public List<RentRequest> getRentRequestsByRenter(User renter) {
//        try {
//            return rentRequestRepository.findByRenter(renter);
//        } catch (Exception e) {
//            System.err.println("RentRequestService Error in getRentRequestsByRenter: " + e.getMessage());
//            e.printStackTrace();
//            throw e;
//        }
//    }
//
//    /**
//     * Approves a specific rent request, ensuring the requesting owner
//     * actually owns the property associated with the request.
//     *
//     * @param requestId The ID of the rent request to approve.
//     * @param owner The User entity of the owner attempting to approve.
//     * @return The updated RentRequest entity.
//     * @throws RuntimeException if the rent request is not found or not owned by the user.
//     */
//    @Transactional
//    public RentRequest approveRentRequest(Long requestId, User owner) {
//        try {
//            RentRequest rentRequest = rentRequestRepository.findById(requestId)
//                    .orElseThrow(() -> new RuntimeException("Rent request not found with ID: " + requestId));
//
//            if (!rentRequest.getProperty().getOwner().getId().equals(owner.getId())) {
//                throw new RuntimeException("You do not have permission to approve this rent request.");
//            }
//
//            if (rentRequest.getIsApproved() != null) {
//                throw new IllegalStateException("Rent request has already been processed (approved/rejected).");
//            }
//
//            rentRequest.setIsApproved(true);
//            RentRequest savedRequest = rentRequestRepository.save(rentRequest);
//
//            // This is the line that likely causes the issue
//            propertyService.markPropertyAsRented(rentRequest.getProperty().getId(), owner);
//
//            emailService.sendRentRequestApprovalNotification(rentRequest.getProperty().getOwner(), rentRequest.getRenter(), rentRequest.getProperty());
//
//            return savedRequest;
//        } catch (Exception e) {
//            System.err.println("RentRequestService Error in approveRentRequest: " + e.getMessage());
//            e.printStackTrace(); // Crucial for seeing the full stack trace
//            throw e; // Re-throw to be caught by controller
//        }
//    }
//
//    /**
//     * Rejects a specific rent request, ensuring the requesting owner
//     * actually owns the property associated with the request.
//     *
//     * @param requestId The ID of the rent request to reject.
//     * @param owner The User entity of the owner attempting to reject.
//     * @return The updated RentRequest entity.
//     * @throws RuntimeException if the rent request is not found or not owned by the user.
//     */
//    @Transactional
//    public RentRequest rejectRentRequest(Long requestId, User owner) {
//        try {
//            RentRequest rentRequest = rentRequestRepository.findById(requestId)
//                    .orElseThrow(() -> new RuntimeException("Rent request not found with ID: " + requestId));
//
//            if (!rentRequest.getProperty().getOwner().getId().equals(owner.getId())) {
//                throw new RuntimeException("You do not have permission to reject this rent request.");
//            }
//
//            if (rentRequest.getIsApproved() != null) {
//                throw new IllegalStateException("Rent request has already been processed (approved/rejected).");
//            }
//
//            rentRequest.setIsApproved(false); // Mark as not approved
//            RentRequest savedRequest = rentRequestRepository.save(rentRequest);
//
//            emailService.sendRentRequestRejectionNotification(rentRequest.getProperty().getOwner(), rentRequest.getRenter(), rentRequest.getProperty());
//
//            return savedRequest;
//        } catch (Exception e) {
//            System.err.println("RentRequestService Error in rejectRentRequest: " + e.getMessage());
//            e.printStackTrace(); // Crucial for seeing the full stack trace
//            throw e; // Re-throw to be caught by controller
//        }
//    }
//
//    /**
//     * Retrieves a single rent request by its ID.
//     *
//     * @param requestId The ID of the rent request.
//     * @return An Optional containing the RentRequest if found, or empty if not.
//     */
//    @Transactional(readOnly = true)
//    public Optional<RentRequest> getRentRequestById(Long requestId) {
//        try {
//            return rentRequestRepository.findById(requestId);
//        } catch (Exception e) {
//            System.err.println("RentRequestService Error in getRentRequestById: " + e.getMessage());
//            e.printStackTrace();
//            throw e;
//        }
//    }
//
//    /**
//     * Updates the payment status of a specific rent request.
//     *
//     * @param rentRequestId The ID of the rent request to update.
//     * @param newPaymentStatus The new payment status (e.g., "PAID", "PENDING", "FAILED").
//     * @throws RuntimeException if the rent request is not found.
//     */
//    @Transactional
//    public void updatePaymentStatus(Long rentRequestId, String newPaymentStatus) {
//        try {
//            RentRequest rentRequest = rentRequestRepository.findById(rentRequestId)
//                    .orElseThrow(() -> new RuntimeException("Rent request not found with ID: " + rentRequestId));
//            rentRequest.setPaymentStatus(newPaymentStatus);
//            rentRequestRepository.save(rentRequest);
//        } catch (Exception e) {
//            System.err.println("RentRequestService Error in updatePaymentStatus: " + e.getMessage());
//            e.printStackTrace();
//            throw e;
//        }
//    }
//} //july 14

//@Service
//@RequiredArgsConstructor
//public class RentRequestService {
//
//    private static final Logger logger = LoggerFactory.getLogger(RentRequestService.class);
//
//    private final RentRequestRepository rentRequestRepository;
//    private final PropertyRepository propertyRepository;
//    private final UserRepository userRepository;
//    private final PropertyService propertyService; // Inject PropertyService
//    private final EmailService emailService; // Inject EmailService
//
//    @Transactional
//    public RentRequest createRentRequest(Long propertyId, String message, User renter) {
//        logger.debug("RentRequestService Debug: Creating rent request for propertyId: {} by renter: {}", propertyId, renter.getEmail());
//
//        Property property = propertyRepository.findById(propertyId)
//                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + propertyId));
//
//        if (!property.getAvailableForRent()) {
//            throw new IllegalArgumentException("Property is not available for rent.");
//        }
//
//        RentRequest rentRequest = new RentRequest();
//        rentRequest.setProperty(property);
//        rentRequest.setRenter(renter);
//        rentRequest.setMessage(message);
//        rentRequest.setRequestDate(java.time.LocalDateTime.now());
//        rentRequest.setIsApproved(null); // Pending status
//
//        RentRequest savedRequest = rentRequestRepository.save(rentRequest);
//        logger.debug("RentRequestService Debug: Rent request saved with ID: {}", savedRequest.getId());
//
//        // Notify the property owner
//        emailService.sendRentRequestNotification(property.getOwner(), renter, property);
//        logger.debug("RentRequestService Debug: Sent rent request notification to owner: {}", property.getOwner().getEmail());
//
//        return savedRequest;
//    }
//
//    @Transactional(readOnly = true)
//    public Optional<RentRequest> getRentRequestById(Long id) {
//        logger.debug("RentRequestService Debug: Fetching rent request with ID: {}", id);
//        return rentRequestRepository.findById(id);
//    }
//
//    @Transactional(readOnly = true)
//    public List<RentRequest> getRentRequestsForOwnerProperties(User owner) {
//        logger.debug("RentRequestService Debug: Fetching rent requests for properties owned by: {}", owner.getEmail());
//        // Corrected: Use the existing findByPropertyOwner method from the repository
//        List<RentRequest> requests = rentRequestRepository.findByPropertyOwner(owner);
//        logger.debug("RentRequestService Debug: Found {} rent requests for owner {}.", requests.size(), owner.getEmail());
//        return requests;
//    }
//
//    @Transactional(readOnly = true)
//    public List<RentRequest> getRentRequestsByPropertyAndOwner(Long propertyId, User owner) {
//        logger.debug("RentRequestService Debug: Fetching rent requests for propertyId: {} by owner: {}", propertyId, owner.getEmail());
//        Property property = propertyRepository.findById(propertyId)
//                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + propertyId));
//
//        if (!Objects.equals(property.getOwner().getId(), owner.getId())) {
//            logger.warn("RentRequestService Warning: User {} attempted to access rent requests for property {} but is not the owner.",
//                    owner.getId(), propertyId);
//            throw new RuntimeException("You do not have permission to view requests for this property.");
//        }
//        List<RentRequest> requests = rentRequestRepository.findByProperty(property);
//        logger.debug("RentRequestService Debug: Found {} rent requests for property {} owned by {}.", requests.size(), propertyId, owner.getEmail());
//        return requests;
//    }
//
//    @Transactional(readOnly = true)
//    public List<RentRequest> getRentRequestsByRenter(User renter) {
//        logger.debug("RentRequestService Debug: Fetching rent requests by renter: {}", renter.getEmail());
//        List<RentRequest> requests = rentRequestRepository.findByRenter(renter);
//        logger.debug("RentRequestService Debug: Found {} rent requests for renter {}.", requests.size(), renter.getEmail());
//        return requests;
//    }
//
//    /**
//     * Updates the payment status of a specific rent request.
//     * @param rentRequestId The ID of the rent request to update.
//     * @param newStatus The new payment status string.
//     */
//    @Transactional
//    public void updatePaymentStatus(Long rentRequestId, String newStatus) {
//        logger.debug("RentRequestService Debug: Updating payment status for rent request ID {} to {}", rentRequestId, newStatus);
//        RentRequest rentRequest = rentRequestRepository.findById(rentRequestId)
//                .orElseThrow(() -> new ResourceNotFoundException("Rent request not found with id: " + rentRequestId));
//        rentRequest.setPaymentStatus(newStatus);
//        rentRequestRepository.save(rentRequest);
//        logger.debug("RentRequestService Debug: Rent request ID {} payment status updated successfully.", rentRequestId);
//    }
//
//
//    @Transactional
//    public RentRequest approveRentRequest(Long requestId, User owner) {
//        try { // Added try-catch block here
//            logger.debug("RentRequestService Debug: Approving rent request with ID: {} by owner: {}", requestId, owner.getEmail());
//
//            RentRequest rentRequest = rentRequestRepository.findById(requestId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Rent request not found with id: " + requestId));
//
//            // Ensure the authenticated owner is indeed the owner of the property associated with the rent request
//            if (!Objects.equals(rentRequest.getProperty().getOwner().getId(), owner.getId())) {
//                logger.warn("RentRequestService Warning: Owner {} attempted to approve request {} but is not the property owner (Property owned by {}).",
//                        owner.getId(), requestId, rentRequest.getProperty().getOwner().getId());
//                throw new RuntimeException("You do not have permission to approve this rent request.");
//            }
//
//            if (rentRequest.getIsApproved() != null) {
//                throw new IllegalArgumentException("Rent request has already been processed.");
//            }
//
//            rentRequest.setIsApproved(true);
//            RentRequest approvedRequest = rentRequestRepository.save(rentRequest);
//            logger.debug("RentRequestService Debug: Rent request {} approved. Updating property availability.", requestId);
//
//            // Mark the property as rented/unavailable
//            propertyService.markPropertyAsRented(rentRequest.getProperty().getId(), owner);
//            logger.debug("RentRequestService Debug: Property {} marked as rented.", rentRequest.getProperty().getId());
//
//            // Send approval notification to the renter
//            emailService.sendRentRequestApprovalNotification(owner, rentRequest.getRenter(), rentRequest.getProperty());
//            logger.debug("RentRequestService Debug: Sent approval notification to renter: {}", rentRequest.getRenter().getEmail());
//
//            return approvedRequest;
//        } catch (Exception e) { // Catch any exception
//            logger.error("RentRequestService Error in approveRentRequest for request ID {}: {}", requestId, e.getMessage(), e); // Log with stack trace
//            throw e; // Re-throw the exception to propagate it up
//        }
//    }
//
//    @Transactional
//    public RentRequest rejectRentRequest(Long requestId, User owner) {
//        try {
//            logger.debug("RentRequestService Debug: Rejecting rent request with ID: {} by owner: {}", requestId, owner.getEmail());
//
//            RentRequest rentRequest = rentRequestRepository.findById(requestId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Rent request not found with id: " + requestId));
//
//            // Ensure the authenticated owner is indeed the owner of the property associated with the rent request
//            if (!Objects.equals(rentRequest.getProperty().getOwner().getId(), owner.getId())) {
//                logger.warn("RentRequestService Warning: Owner {} attempted to reject request {} but is not the property owner.",
//                        owner.getId(), requestId);
//                throw new RuntimeException("You do not have permission to reject this rent request.");
//            }
//
//            if (rentRequest.getIsApproved() != null) {
//                throw new IllegalArgumentException("Rent request has already been processed.");
//            }
//
//            rentRequest.setIsApproved(false);
//            RentRequest rejectedRequest = rentRequestRepository.save(rentRequest);
//            logger.debug("RentRequestService Debug: Rent request {} rejected.", requestId);
//
//            // Send rejection notification to the renter
//            emailService.sendRentRequestRejectionNotification(owner, rentRequest.getRenter(), rentRequest.getProperty());
//            logger.debug("RentRequestService Debug: Sent rejection notification to renter: {}", rentRequest.getRenter().getEmail());
//
//            return rejectedRequest;
//        } catch (Exception e) {
//            logger.error("RentRequestService Error in rejectRentRequest for request ID {}: {}", requestId, e.getMessage(), e);
//            throw e;
//        }
//    }
//}
//comment july 20 to add notification


@Service
public class RentRequestService {

    private static final Logger logger = LoggerFactory.getLogger(RentRequestService.class);

    private final RentRequestRepository rentRequestRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final PropertyService propertyService;
    private final EmailService emailService;
    private final NotificationService notificationService; // NEW: Inject NotificationService

    @Autowired
    public RentRequestService(RentRequestRepository rentRequestRepository, PropertyRepository propertyRepository,
                              UserRepository userRepository, PropertyService propertyService,
                              EmailService emailService, NotificationService notificationService) { // NEW: Add NotificationService
        this.rentRequestRepository = rentRequestRepository;
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
        this.propertyService = propertyService;
        this.emailService = emailService;
        this.notificationService = notificationService; // NEW: Initialize
    }

    @Transactional
    public RentRequest createRentRequest(Long propertyId, String message, User renter) {
        logger.debug("RentRequestService Debug: Creating rent request for propertyId: {} by renter: {}", propertyId, renter.getEmail());

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + propertyId));

        if (!property.getAvailableForRent()) {
            throw new IllegalArgumentException("Property is not available for rent.");
        }

        RentRequest rentRequest = new RentRequest();
        rentRequest.setProperty(property);
        rentRequest.setRenter(renter);
        rentRequest.setMessage(message);
        rentRequest.setRequestDate(java.time.LocalDateTime.now());
        rentRequest.setIsApproved(null); // Pending status

        RentRequest savedRequest = rentRequestRepository.save(rentRequest);
        logger.debug("RentRequestService Debug: Rent request saved with ID: {}", savedRequest.getId());

        // Notify the property owner
        emailService.sendRentRequestNotification(property.getOwner(), renter, property);
        logger.debug("RentRequestService Debug: Sent rent request notification to owner: {}", property.getOwner().getEmail());

        // NEW: Create notification for the owner
        notificationService.createNotification(
                property.getOwner().getId(),
                renter.getId(),
                "NEW_RENT_REQUEST",
                "New rent request for your property '" + property.getTitle() + "' from " + renter.getFirstName(),
                savedRequest.getId(),
                "RENT_REQUEST"
        );

        return savedRequest;
    }

    @Transactional(readOnly = true)
    public Optional<RentRequest> getRentRequestById(Long id) {
        logger.debug("RentRequestService Debug: Fetching rent request with ID: {}", id);
        return rentRequestRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<RentRequest> getRentRequestsForOwnerProperties(User owner) {
        logger.debug("RentRequestService Debug: Fetching rent requests for properties owned by: {}", owner.getEmail());
        List<RentRequest> requests = rentRequestRepository.findByPropertyOwner(owner);
        logger.debug("RentRequestService Debug: Found {} rent requests for owner {}.", requests.size(), owner.getEmail());
        return requests;
    }

    @Transactional(readOnly = true)
    public List<RentRequest> getRentRequestsByPropertyAndOwner(Long propertyId, User owner) {
        logger.debug("RentRequestService Debug: Fetching rent requests for propertyId: {} by owner: {}", propertyId, owner.getEmail());
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + propertyId));

        if (!Objects.equals(property.getOwner().getId(), owner.getId())) {
            logger.warn("RentRequestService Warning: User {} attempted to access rent requests for property {} but is not the owner.",
                    owner.getId(), propertyId);
            throw new RuntimeException("You do not have permission to view requests for this property.");
        }
        List<RentRequest> requests = rentRequestRepository.findByProperty(property);
        logger.debug("RentRequestService Debug: Found {} rent requests for property {} owned by {}.", requests.size(), propertyId, owner.getEmail());
        return requests;
    }

    @Transactional(readOnly = true)
    public List<RentRequest> getRentRequestsByRenter(User renter) {
        logger.debug("RentRequestService Debug: Fetching rent requests by renter: {}", renter.getEmail());
        List<RentRequest> requests = rentRequestRepository.findByRenter(renter);
        logger.debug("RentRequestService Debug: Found {} rent requests for renter {}.", requests.size(), renter.getEmail());
        return requests;
    }

    @Transactional
    public void updatePaymentStatus(Long rentRequestId, String newStatus) {
        logger.debug("RentRequestService Debug: Updating payment status for rent request ID {} to {}", rentRequestId, newStatus);
        RentRequest rentRequest = rentRequestRepository.findById(rentRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Rent request not found with id: " + rentRequestId));
        rentRequest.setPaymentStatus(newStatus);
        rentRequestRepository.save(rentRequest);
        logger.debug("RentRequestService Debug: Rent request ID {} payment status updated successfully.", rentRequestId);
    }


    @Transactional
    public RentRequest approveRentRequest(Long requestId, User owner) {
        try {
            logger.debug("RentRequestService Debug: Approving rent request with ID: {} by owner: {}", requestId, owner.getEmail());

            RentRequest rentRequest = rentRequestRepository.findById(requestId)
                    .orElseThrow(() -> new ResourceNotFoundException("Rent request not found with id: " + requestId));

            if (!Objects.equals(rentRequest.getProperty().getOwner().getId(), owner.getId())) {
                logger.warn("RentRequestService Warning: Owner {} attempted to approve request {} but is not the property owner (Property owned by {}).",
                        owner.getId(), requestId, rentRequest.getProperty().getOwner().getId());
                throw new RuntimeException("You do not have permission to approve this rent request.");
            }

            if (rentRequest.getIsApproved() != null) {
                throw new IllegalArgumentException("Rent request has already been processed.");
            }

            rentRequest.setIsApproved(true);
            RentRequest approvedRequest = rentRequestRepository.save(rentRequest);
            logger.debug("RentRequestService Debug: Rent request {} approved. Updating property availability.", requestId);

            propertyService.markPropertyAsRented(rentRequest.getProperty().getId(), owner);
            logger.debug("RentRequestService Debug: Property {} marked as rented.", rentRequest.getProperty().getId());

            emailService.sendRentRequestApprovalNotification(owner, rentRequest.getRenter(), rentRequest.getProperty());
            logger.debug("RentRequestService Debug: Sent approval notification to renter: {}", rentRequest.getRenter().getEmail());

            // NEW: Create notification for the renter
            notificationService.createNotification(
                    rentRequest.getRenter().getId(),
                    owner.getId(),
                    "REQUEST_APPROVED",
                    "Your rent request for property '" + rentRequest.getProperty().getTitle() + "' has been approved!",
                    approvedRequest.getId(),
                    "RENT_REQUEST"
            );

            return approvedRequest;
        } catch (Exception e) {
            logger.error("RentRequestService Error in approveRentRequest for request ID {}: {}", requestId, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public RentRequest rejectRentRequest(Long requestId, User owner) {
        try {
            logger.debug("RentRequestService Debug: Rejecting rent request with ID: {} by owner: {}", requestId, owner.getEmail());

            RentRequest rentRequest = rentRequestRepository.findById(requestId)
                    .orElseThrow(() -> new ResourceNotFoundException("Rent request not found with id: " + requestId));

            if (!Objects.equals(rentRequest.getProperty().getOwner().getId(), owner.getId())) {
                logger.warn("RentRequestService Warning: Owner {} attempted to reject request {} but is not the property owner.",
                        owner.getId(), requestId);
                throw new RuntimeException("You do not have permission to reject this rent request.");
            }

            if (rentRequest.getIsApproved() != null) {
                throw new IllegalArgumentException("Rent request has already been processed.");
            }

            rentRequest.setIsApproved(false);
            RentRequest rejectedRequest = rentRequestRepository.save(rentRequest);
            logger.debug("RentRequestService Debug: Rent request {} rejected.", requestId);

            emailService.sendRentRequestRejectionNotification(owner, rentRequest.getRenter(), rentRequest.getProperty());
            logger.debug("RentRequestService Debug: Sent rejection notification to renter: {}", rentRequest.getRenter().getEmail());

            // NEW: Create notification for the renter
            notificationService.createNotification(
                    rentRequest.getRenter().getId(),
                    owner.getId(),
                    "REQUEST_REJECTED",
                    "Your rent request for property '" + rentRequest.getProperty().getTitle() + "' has been rejected.",
                    rejectedRequest.getId(),
                    "RENT_REQUEST"
            );

            return rejectedRequest;
        } catch (Exception e) {
            logger.error("RentRequestService Error in rejectRentRequest for request ID {}: {}", requestId, e.getMessage(), e);
            throw e;
        }
    }
}
