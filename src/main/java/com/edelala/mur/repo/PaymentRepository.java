package com.edelala.mur.repo;


import com.edelala.mur.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

//    Optional<Payment> findByStripePaymentIntentId(String stripePaymentIntentId);
//    Optional<Payment> findByRentRequestId(Long rentRequestId); // Crucial for preventing duplicates

    Optional<Payment> findByStripePaymentIntentId(String stripePaymentIntentId);

    // THIS IS CRUCIAL: Must be present for duplicate prevention logic in PaymentService
    Optional<Payment> findByRentRequestId(Long rentRequestId);


    // This is the method that was missing and causing the compilation error
    Optional<Payment> findByRentRequestIdAndStatus(Long rentRequestId, String status);

}