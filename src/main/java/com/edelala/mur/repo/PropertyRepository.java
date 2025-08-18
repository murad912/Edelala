package com.edelala.mur.repo;



import com.edelala.mur.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

//


//@Repository
//public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {
//
////    Page<Property> findByAvailableForRentTrue(Specification<Property> spec, Pageable pageable);
////
////    List<Property> findByAvailableForSaleTrue(Specification<Property> spec, Pageable pageable);
////
////    List<Property> findByOwner(User owner);
////
////    @Query("SELECT p FROM Property p WHERE " +
////            "(:propertyType IS NULL OR p.propertyType = :propertyType) AND " +
////            "(:city IS NULL OR LOWER(p.city) LIKE LOWER(CONCAT('%', :city, '%'))) AND " +
////            "(:minRent IS NULL OR p.rentPrice >= :minRent) AND " +
////            "(:maxRent IS NULL OR p.rentPrice <= :maxRent) AND " +
////            "p.available = true")
////    Page<Property> findFilteredProperties(
////            @Param("propertyType") PropertyType propertyType,
////            @Param("city") String city,
////            @Param("minRent") BigDecimal minRent,
////            @Param("maxRent") BigDecimal maxRent,
////            Pageable pageable);
//    //Comment the above code to improve filter july 10
//}


//I create separate PropertySpecification filter criteria.
//    List<Property> findByOwnerId(Long ownerId);
// Extend JpaSpecificationExecutor to enable dynamic queries using Specifications
public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {

    // This method is used by PropertyService.getPropertiesByOwner and should be kept.
    List<Property> findByOwnerId(Long ownerId);

    // No other custom filter methods are needed here, as JpaSpecificationExecutor handles dynamic filtering.
}
