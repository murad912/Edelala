package com.edelala.mur.repo;

import com.edelala.mur.entity.Property;
import com.edelala.mur.entity.RentRequest;
import com.edelala.mur.entity.User;
import org.hibernate.annotations.processing.Find;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentRequestRepository extends JpaRepository<RentRequest, Long> {
    // Find all rent requests for a specific property
    List<RentRequest> findByProperty(Property property);

    // Find all rent requests by a specific renter
    List<RentRequest> findByRenter(User renter);

    // Find all rent requests where the property's owner is the given user
    List<RentRequest> findByPropertyOwner(User owner);

    // NEW: Find all pending rent requests for properties owned by a specific owner
    // This leverages JPA's query derivation: findByProperty_Owner ensures we join to Property,
    // and then filter by owner and where isApproved is NULL.
    List<RentRequest> findByProperty_OwnerAndIsApprovedIsNull(User owner);

    // NEW: Find requests by property and renter (for checking existing active requests)
    List<RentRequest> findByPropertyAndRenter(Property property, User renter);
}
