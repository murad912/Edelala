package com.edelala.mur.util;//package com.edelala.mur.util;

import com.edelala.mur.entity.PropertyType;
import com.edelala.mur.entity.Role;
import com.edelala.mur.repo.PropertyRepository;
import com.edelala.mur.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

////
//import com.edelala.mur.entity.Property;
//import com.edelala.mur.entity.PropertyType;
//import com.edelala.mur.entity.Role;
//import com.edelala.mur.entity.User;
//
//import com.edelala.mur.repo.PropertyRepository;
//import com.edelala.mur.repo.UserRepository;
//import com.edelala.mur.service.PropertyService; // Import PropertyService
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.data.domain.Pageable; // Import Pageable
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime; // Import LocalDateTime
//
//@Component
//@RequiredArgsConstructor
//public class DataLoader implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final PropertyRepository propertyRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final PropertyService propertyService; // Inject PropertyService
//
//    @Override
//    public void run(String... args) throws Exception {
//        // Create admin user if not exists
//        if (userRepository.findByEmail("admin@example.com").isEmpty()) {
//            User admin = new User();
//            admin.setFirstName("Admin");
//            admin.setLastName("User");
//            admin.setEmail("admin@example.com");
//            admin.setPassword(passwordEncoder.encode("adminpass"));
//            admin.setRole(Role.ADMIN);
//            userRepository.save(admin);
//            System.out.println("Admin user created.");
//        }
//
//        // Create owner user if not exists
//        User owner = userRepository.findByEmail("owner@example.com")
//                .orElseGet(() -> {
//                    User newOwner = new User();
//                    newOwner.setFirstName("Owner");
//                    newOwner.setLastName("User");
//                    newOwner.setEmail("owner@example.com");
//                    newOwner.setPassword(passwordEncoder.encode("ownerpass"));
//                    newOwner.setRole(Role.OWNER);
//                    return userRepository.save(newOwner);
//                });
//
//        // Create renter user if not exists
//        if (userRepository.findByEmail("renter@example.com").isEmpty()) {
//            User renter = new User();
//            renter.setFirstName("Renter");
//            renter.setLastName("User");
//            renter.setEmail("renter@example.com");
//            renter.setPassword(passwordEncoder.encode("renterpass"));
//            renter.setRole(Role.RENTER);
//            userRepository.save(renter);
//            System.out.println("Renter user created.");
//        }
//
//        // Create properties for 'owner@example.com' if not exists
//        // Use propertyService to check if owner has any properties
//        if (propertyService.getOwnerPropertiesFilteredAndSorted(owner, null, null, null, null, Pageable.unpaged()).isEmpty()) {
//            System.out.println("Creating initial properties for owner@example.com...");
//
//            Property property1 = new Property();
//            property1.setTitle("Spacious House with Garden");
//            property1.setDescription("A beautiful suburban house with a large garden and 3 bedrooms.");
//            property1.setAddress("123 Garden Lane");
//            property1.setCity("Anytown");
//            property1.setState("NY");
//            property1.setZipCode("10001");
//            property1.setBedrooms(3);
//            property1.setBathrooms(BigDecimal.valueOf(2)); // FIX: Convert int to BigDecimal
//            property1.setRentPrice(BigDecimal.valueOf(2500.00));
//            property1.setPropertyType(PropertyType.SINGLE_HOUSE); // Use direct enum
//            property1.setOwner(owner);
//            property1.setAvailable(true);
//            property1.setAvailableForRent(true);
//            property1.setAvailableForSale(false);
//            property1.setListingDate(LocalDateTime.now()); // FIX: Add listingDate
//            propertyRepository.save(property1);
//            System.out.println("Property 1 created.");
//
//            Property property3 = new Property();
//            property3.setTitle("Modern Condo in City Center");
//            property3.setDescription("Stylish 2-bedroom condo in the vibrant city center, perfect for urban living.");
//            property3.setAddress("789 Central Ave");
//            property3.setCity("Anytown");
//            property3.setState("NY");
//            property3.setZipCode("10003");
//            property3.setBedrooms(2);
//            property3.setBathrooms(BigDecimal.valueOf(2)); // FIX: Convert int to BigDecimal
//            property3.setRentPrice(BigDecimal.valueOf(1800.00));
//            property3.setPropertyType(PropertyType.CONDO); // Use direct enum
//            property3.setOwner(owner);
//            property3.setAvailable(true);
//            property3.setAvailableForRent(false);
//            property3.setAvailableForSale(true);
//            property3.setListingDate(LocalDateTime.now().minusDays(1)); // FIX: Add listingDate (e.g., yesterday)
//            propertyRepository.save(property3);
//            System.out.println("Property 3 created.");
//
//            // Add the "Family Condo" property you mentioned earlier for consistency
//            Property property4 = new Property();
//            property4.setTitle("Family Condo");
//            property4.setDescription("Cozy family condo perfect for a small family.");
//            property4.setAddress("10816 Stone Canyon Rd");
//            property4.setCity("Dallas");
//            property4.setState("TX");
//            property4.setZipCode("75230");
//            property4.setBedrooms(1);
//            property4.setBathrooms(BigDecimal.valueOf(1));
//            property4.setRentPrice(BigDecimal.valueOf(1200.00));
//            property4.setPropertyType(PropertyType.APARTMENT); // Assuming APARTMENT is correct type
//            property4.setOwner(owner);
//            property4.setAvailable(true);
//            property4.setAvailableForRent(true);
//            property4.setAvailableForSale(false);
//            property4.setListingDate(LocalDateTime.now().minusDays(2));
//            propertyRepository.save(property4);
//            System.out.println("Family Condo property created.");
//
//        }
//    }
//}  //jun 24 comment to upgrade owner Dashboard

//import com.edelala.mur.entity.Property;
//import com.edelala.mur.entity.PropertyType;
//import com.edelala.mur.entity.Role;
//import com.edelala.mur.entity.User;
//import com.edelala.mur.repo.PropertyRepository;
//import com.edelala.mur.repo.UserRepository;
//import com.edelala.mur.service.PropertyService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//
//@Component
//@RequiredArgsConstructor
//public class DataLoader implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final PropertyRepository propertyRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final PropertyService propertyService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        // Create admin user if not exists
//        if (userRepository.findByEmail("admin@example.com").isEmpty()) {
//            User admin = new User();
//            admin.setFirstName("Admin");
//            admin.setLastName("User");
//            admin.setEmail("admin@example.com");
//            admin.setUsername("admin@example.com"); // Keeping username as DB constraint expects it
//            admin.setPassword(passwordEncoder.encode("adminpass"));
//            admin.setRole(Role.ADMIN);
//            userRepository.save(admin);
//            System.out.println("Admin user created.");
//        }
//
//        // Create owner user if not exists
//        User owner = userRepository.findByEmail("owner@example.com")
//                .orElseGet(() -> {
//                    User newOwner = new User();
//                    newOwner.setFirstName("Owner");
//                    newOwner.setLastName("User");
//                    newOwner.setEmail("owner@example.com");
//                    newOwner.setUsername("owner@example.com"); // Keeping username as DB constraint expects it
//                    newOwner.setPassword(passwordEncoder.encode("ownerpass"));
//                    newOwner.setRole(Role.OWNER);
//                    return userRepository.save(newOwner);
//                });
//
//        // Create renter user if not exists
//        if (userRepository.findByEmail("renter@example.com").isEmpty()) {
//            User renter = new User();
//            renter.setFirstName("Renter");
//            renter.setLastName("User");
//            renter.setEmail("renter@example.com");
//            renter.setUsername("renter@example.com"); // Keeping username as DB constraint expects it
//            renter.setPassword(passwordEncoder.encode("renterpass"));
//            renter.setRole(Role.RENTER);
//            userRepository.save(renter);
//            System.out.println("Renter user created.");
//        }
//
//        // Create properties for 'owner@example.com' if not exists
//        // FIX: Use the new getPropertiesByOwner service method signature
//        if (propertyService.getPropertiesByOwner(owner, null, null, null, null, Pageable.unpaged()).isEmpty()) {
//            System.out.println("Creating initial properties for owner@example.com...");
//
//            Property property1 = new Property();
//            property1.setTitle("Spacious House with Garden");
//            property1.setDescription("A beautiful suburban house with a large garden and 3 bedrooms.");
//            property1.setAddress("123 Garden Lane");
//            property1.setCity("Anytown");
//            property1.setState("NY");
//            property1.setZipCode("10001");
//            property1.setBedrooms(3);
//            property1.setBathrooms(2); // FIX: Set as Integer directly
//            property1.setRentPrice(BigDecimal.valueOf(2500.00));
//            property1.setPropertyType(PropertyType.SINGLE_HOUSE);
//            property1.setOwner(owner);
//            property1.setAvailable(true);
//            property1.setAvailableForRent(true);
//            property1.setAvailableForSale(false);
//            // If you want a listingDate, add it to Property entity and here: property1.setListingDate(LocalDateTime.now());
//            propertyRepository.save(property1);
//            System.out.println("Property 1 created.");
//
//            Property property3 = new Property();
//            property3.setTitle("Modern Condo in City Center");
//            property3.setDescription("Stylish 2-bedroom condo in the vibrant city center, perfect for urban living.");
//            property3.setAddress("789 Central Ave");
//            property3.setCity("Anytown");
//            property3.setState("NY");
//            property3.setZipCode("10003");
//            property3.setBedrooms(2);
//            property3.setBathrooms(2); // FIX: Set as Integer directly
//            property3.setRentPrice(BigDecimal.valueOf(1800.00));
//            property3.setPropertyType(PropertyType.CONDO);
//            property3.setOwner(owner);
//            property3.setAvailable(true);
//            property3.setAvailableForRent(false);
//            property3.setAvailableForSale(true);
//            // If you want a listingDate, add it to Property entity and here: property3.setListingDate(LocalDateTime.now().minusDays(1));
//            propertyRepository.save(property3);
//            System.out.println("Property 3 created.");
//
//            Property property4 = new Property();
//            property4.setTitle("Family Condo");
//            property4.setDescription("Cozy family condo perfect for a small family.");
//            property4.setAddress("10816 Stone Canyon Rd");
//            property4.setCity("Dallas");
//            property4.setState("TX");
//            property4.setZipCode("75230");
//            property4.setBedrooms(1);
//            property4.setBathrooms(1); // FIX: Set as Integer directly
//            property4.setRentPrice(BigDecimal.valueOf(1200.00));
//            property4.setPropertyType(PropertyType.APARTMENT);
//            property4.setOwner(owner);
//            property4.setAvailable(true);
//            property4.setAvailableForRent(true);
//            property4.setAvailableForSale(false);
//            // If you want a listingDate, add it to Property entity and here: property4.setListingDate(LocalDateTime.now().minusDays(2));
//            propertyRepository.save(property4);
//            System.out.println("Family Condo property created.");
//        }
//    }
//}


import com.edelala.mur.entity.Property;
import com.edelala.mur.entity.User;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

// If this is in a separate DataLoader.java, make sure it's annotated with @Configuration
//@Configuration
//public class DataLoader { // Or your main application class, e.g., EdelalaApplication
//
//    @Bean
//    public CommandLineRunner demoData2(PropertyRepository propertyRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        return args -> {
//            // Create or update a test RENTER user
//            Optional<User> existingRenter = userRepository.findByEmail("webalemeadego21@gmail.com");
//            User renter;
//            if (existingRenter.isPresent()) {
//                renter = existingRenter.get();
//                System.out.println("DEBUG: Updating existing Renter: " + renter.getEmail() + " (ID: " + renter.getId() + ")");
//                renter.setFirstName("TestRenter");
//                renter.setLastName("Account");
//                renter.setPassword(passwordEncoder.encode("password"));
//                renter.setRole(Role.RENTER);
//            } else {
//                renter = new User();
//                renter.setUsername("testrenter");
//                renter.setEmail("webalemeadego21@gmail.com");
//                renter.setFirstName("TestRenter");
//                renter.setLastName("Account");
//                renter.setPassword(passwordEncoder.encode("password"));
//                renter.setRole(Role.RENTER);
//                System.out.println("DEBUG: Creating new Renter: " + renter.getEmail());
//            }
//            userRepository.save(renter);
//            System.out.println("DEBUG: Saved Renter with ID: " + renter.getId());
//
//
//            // Create or update a test OWNER user (original generic owner)
//            Optional<User> existingOwner = userRepository.findByEmail("owner@example.com");
//            User owner;
//            if (existingOwner.isPresent()) {
//                owner = existingOwner.get();
//                System.out.println("DEBUG: Updating existing Owner: " + owner.getEmail() + " (ID: " + owner.getId() + ")");
//                owner.setFirstName("Generic");
//                owner.setLastName("Owner");
//                owner.setPassword(passwordEncoder.encode("password"));
//                owner.setRole(Role.OWNER);
//            } else {
//                owner = new User();
//                owner.setUsername("testowner");
//                owner.setEmail("owner@example.com");
//                owner.setFirstName("Generic");
//                owner.setLastName("Owner");
//                owner.setPassword(passwordEncoder.encode("password"));
//                owner.setRole(Role.OWNER);
//                System.out.println("DEBUG: Creating new Owner: " + owner.getEmail());
//            }
//            userRepository.save(owner);
//            System.out.println("DEBUG: Saved Owner with ID: " + owner.getId());
//
//            // Create property for 'owner@example.com'
//            // Since ddl-auto is create-drop, these will be recreated every time
//            propertyRepository.save(createProperty(owner, "Spacious House with Garden", "A beautiful suburban house with a large garden and 3 bedrooms.", "123 Garden Lane", "Dallas", "TX", "10001", 3, 2.0, 2500.00, null, PropertyType.SINGLE_HOUSE, true, true, false));
//            propertyRepository.save(createProperty(owner, "Modern Condo in City Center", "Stylish 2-bedroom condo in the vibrant city center, perfect for urban living.", "789 Central Ave", "Anytown", "NY", "10003", 2, 2.0, 1800.00, null, PropertyType.CONDO, true, false, true));
//
//
//            // Create or update your specific OWNER user ('owner2')
//            Optional<User> existingOwner2 = userRepository.findByEmail("barnoonni.aangoodha@gmail.com");
//            User owner2;
//            if (existingOwner2.isPresent()) {
//                owner2 = existingOwner2.get();
//                System.out.println("DEBUG: Updating existing Owner2: " + owner2.getEmail() + " (ID: " + owner2.getId() + ")");
//                owner2.setFirstName("Murad");
//                owner2.setLastName("Abdulmalik");
//                owner2.setPassword(passwordEncoder.encode("password"));
//                owner2.setRole(Role.OWNER);
//            } else {
//                owner2 = new User();
//                owner2.setUsername("muradowner");
//                owner2.setEmail("barnoonni.aangoodha@gmail.com");
//                owner2.setFirstName("Murad");
//                owner2.setLastName("Abdulmalik");
//                owner2.setPassword(passwordEncoder.encode("password"));
//                owner2.setRole(Role.OWNER);
//                System.out.println("DEBUG: Creating new Owner2: " + owner2.getEmail());
//            }
//            userRepository.save(owner2);
//            System.out.println("DEBUG: Saved Owner2 with ID: " + owner2.getId());
//
//            // Create properties for your specific owner (owner2)
//            // This will ensure owner2 has enough properties to test pagination
//            propertyRepository.save(createProperty(owner2, "Spacious Family Home", "A beautiful house with a large backyard, perfect for families.", "456 Oak Ave", "Anytown", "NY", "10002", 4, 3.0, 3500.00, null, PropertyType.SINGLE_HOUSE, true, true, true));
//            propertyRepository.save(createProperty(owner2, "Cozy Apartment Downtown", "1-bedroom apartment close to all amenities.", "101 Main St", "Anytown", "NY", "10001", 1, 1.0, 1200.00, null, PropertyType.APARTMENT, true, true, false));
//            propertyRepository.save(createProperty(owner2, "Luxury Penthouse", "Top-floor penthouse with stunning city views.", "500 High Rise", "Metropolis", "CA", "90210", 3, 3.0, 7500.00, null, PropertyType.CONDO, true, true, true));
//            propertyRepository.save(createProperty(owner2, "Suburban Townhouse", "Quiet neighborhood townhouse with 3 beds, 2.5 baths.", "789 Quiet Ln", "Anytown", "NY", "10004", 3, 3.0, 2800.00, null, PropertyType.TOWNHOUSE, true, true, false));
//            propertyRepository.save(createProperty(owner2, "Charming Cottage", "Small, cozy cottage perfect for a single person or couple.", "22 Elm St", "Anytown", "NY", "10005", 2, 1.0, 1500.00, null, PropertyType.SINGLE_HOUSE, true, true, false));
//            propertyRepository.save(createProperty(owner2, "Urban Loft", "Industrial style loft with high ceilings and open plan.", "300 Artsy Blvd", "Metropolis", "CA", "90211", 1, 1.0, 2000.00, null, PropertyType.APARTMENT, true, true, false));
//            propertyRepository.save(createProperty(owner2, "Executive Condo", "Upscale condo with modern finishes and gym access.", "150 Elite Dr", "Metropolis", "CA", "90212", 2, 2.0, 3000.00, null, PropertyType.CONDO, true, true, true));
//            propertyRepository.save(createProperty(owner2, "Sprawling Ranch Home", "Large ranch-style home with expansive yard.", "999 Countryside Rd", "Anytown", "NY", "10006", 5, 4.0, 4500.00, null, PropertyType.SINGLE_HOUSE, true, true, false));
//            propertyRepository.save(createProperty(owner2, "Compact Studio", "Affordable studio apartment for minimalist living.", "555 Small St", "Anytown", "NY", "10007", 0, 1.0, 900.00, null, PropertyType.APARTMENT, true, true, false));
//            propertyRepository.save(createProperty(owner2, "Historic Brownstone", "Renovated brownstone with classic charm.", "777 Old Town Rd", "Anytown", "NY", "10008", 4, 3.0, 4000.00, null, PropertyType.TOWNHOUSE, true, true, true));
//            propertyRepository.save(createProperty(owner2, "Riverside Apartment", "Apartment with beautiful river views.", "88 Riverfront Pkwy", "Metropolis", "CA", "90213", 2, 2.0, 2300.00, null, PropertyType.APARTMENT, true, true, false));
//        };
//    }
//
//    // Helper method to create a Property object more concisely
//    private Property createProperty(User owner, String title, String description, String address, String city, String state, String zipCode,
//                                    Integer bedrooms, Double bathrooms, Double rentPrice, Double salePrice, PropertyType propertyType,
//                                    boolean available, boolean availableForRent, boolean availableForSale) {
//        Property property = new Property();
//        property.setTitle(title);
//        property.setDescription(description);
//        property.setAddress(address);
//        property.setCity(city);
//        property.setState(state);
//        property.setZipCode(zipCode);
//        property.setBedrooms(bedrooms);
//        property.setBathrooms(bathrooms);
//        property.setRentPrice(rentPrice != null ? BigDecimal.valueOf(rentPrice) : null);
//        property.setSalePrice(salePrice != null ? BigDecimal.valueOf(salePrice) : null);
//        property.setPropertyType(propertyType);
//        property.setOwner(owner);
//        property.setAvailable(available);
//        property.setAvailableForRent(availableForRent);
//        property.setAvailableForSale(availableForSale);
//        return property;
//    }
//}
//I comment to improve data for better testing

@Configuration // Mark this class as a Spring configuration class
@RequiredArgsConstructor
public class DataLoader {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            System.out.println("--- Starting DataLoader ---");

            // --- Create or Update RENTER User ---
            Optional<User> existingRenter = userRepository.findByEmail("webalemeadego21@gmail.com");
            User renter = existingRenter.orElseGet(User::new);
            renter.setUsername("testrenter");
            renter.setEmail("webalemeadego21@gmail.com");
            renter.setFirstName("Test");
            renter.setLastName("Renter");
            renter.setPassword(passwordEncoder.encode("password"));
            renter.setRole(Role.RENTER);
            userRepository.save(renter);
            System.out.println("DEBUG: Renter (ID: " + renter.getId() + ") created/updated.");

            // --- Create or Update OWNER 1 User (Generic) ---
            Optional<User> existingOwner1 = userRepository.findByEmail("owner@example.com");
            User owner1 = existingOwner1.orElseGet(User::new);
            owner1.setUsername("testowner");
            owner1.setEmail("owner@example.com");
            owner1.setFirstName("Generic");
            owner1.setLastName("Owner");
            owner1.setPassword(passwordEncoder.encode("password"));
            owner1.setRole(Role.OWNER);
            userRepository.save(owner1);
            System.out.println("DEBUG: Owner 1 (ID: " + owner1.getId() + ") created/updated.");

            // --- Create or Update OWNER 2 User (Murad) ---
            Optional<User> existingOwner2 = userRepository.findByEmail("barnoonni.aangoodha@gmail.com");
            User owner2 = existingOwner2.orElseGet(User::new);
            owner2.setUsername("muradowner");
            owner2.setEmail("barnoonni.aangoodha@gmail.com");
            owner2.setFirstName("Murad");
            owner2.setLastName("Abdulmalik");
            owner2.setPassword(passwordEncoder.encode("password"));
            owner2.setRole(Role.OWNER);
            userRepository.save(owner2);
            System.out.println("DEBUG: Owner 2 (ID: " + owner2.getId() + ") created/updated.");

            // --- Load Properties (only if database is empty) ---
            if (propertyRepository.count() == 0) {
                System.out.println("DEBUG: Database is empty, loading demo properties...");

                // Properties for owner1 (owner@example.com)
                propertyRepository.save(createProperty(owner1, "Spacious House with Garden", "A beautiful suburban house with a large garden and 3 bedrooms.", "123 Garden Lane", "Dallas", "TX", "75201", 3, 2.0, 2500.00, null, PropertyType.SINGLE_HOUSE, true, true, false));
                propertyRepository.save(createProperty(owner1, "Modern Condo in City Center", "Stylish 2-bedroom condo in the vibrant city center, perfect for urban living. Features a gym and pool.", "789 Central Ave", "Austin", "TX", "78701", 2, 2.0, 1800.00, null, PropertyType.CONDO, true, true, true));
                propertyRepository.save(createProperty(owner1, "Cozy Townhouse in Suburbs", "Family-friendly townhouse with 3 beds, 2.5 baths, and a small patio.", "456 Elm St", "Houston", "TX", "77001", 3, 2.5, 2200.00, null, PropertyType.TOWNHOUSE, true, true, false));
                propertyRepository.save(createProperty(owner1, "Luxury Loft with Skyline View", "High-end loft apartment with incredible city views and modern amenities.", "100 Sky Tower", "Dallas", "TX", "75202", 1, 1.0, 3000.00, null, PropertyType.APARTMENT, true, true, false));
                propertyRepository.save(createProperty(owner1, "Beachfront Villa", "Stunning villa directly on the beach, 5 bedrooms, perfect for vacation.", "1 Ocean Dr", "Miami", "FL", "33101", 5, 4.0, null, 1500000.00, PropertyType.SINGLE_HOUSE, true, false, true)); // For Sale Only
                propertyRepository.save(createProperty(owner1, "Compact Studio Apartment", "Affordable studio in a bustling neighborhood, great for students.", "200 University Rd", "Austin", "TX", "78705", 0, 1.0, 900.00, null, PropertyType.APARTMENT, true, true, false));
                propertyRepository.save(createProperty(owner1, "Historic Home near Downtown", "Charming historic house with original features, requires some renovation.", "300 Heritage Ln", "Houston", "TX", "77002", 4, 2.0, null, 750000.00, PropertyType.SINGLE_HOUSE, true, false, true)); // For Sale Only
                propertyRepository.save(createProperty(owner1, "Riverside Condo", "2 bed, 2 bath condo with river views and walking trails nearby.", "500 Riverfront", "Dallas", "TX", "75207", 2, 2.0, 2100.00, null, PropertyType.CONDO, true, true, false));


                // Properties for owner2 (barnoonni.aangoodha@gmail.com)
                propertyRepository.save(createProperty(owner2, "Spacious Family Home", "A beautiful house with a large backyard, perfect for families and pets.", "456 Oak Ave", "Dallas", "TX", "75202", 4, 3.0, 3500.00, null, PropertyType.SINGLE_HOUSE, true, true, true));
                propertyRepository.save(createProperty(owner2, "Cozy Apartment Downtown", "1-bedroom apartment close to all amenities, including a public park.", "101 Main St", "Austin", "TX", "78704", 1, 1.0, 1200.00, null, PropertyType.APARTMENT, true, true, false));
                propertyRepository.save(createProperty(owner2, "Luxury Penthouse", "Top-floor penthouse with stunning city views, private elevator access.", "500 High Rise", "Metropolis", "CA", "90210", 3, 3.0, 7500.00, null, PropertyType.CONDO, true, true, true));
                propertyRepository.save(createProperty(owner2, "Suburban Townhouse", "Quiet neighborhood townhouse with 3 beds, 2.5 baths, great schools.", "789 Quiet Ln", "Houston", "TX", "77003", 3, 2.5, 2800.00, null, PropertyType.TOWNHOUSE, true, true, false));
                propertyRepository.save(createProperty(owner2, "Charming Cottage", "Small, cozy cottage perfect for a single person or couple, with a small garden.", "22 Elm St", "Dallas", "TX", "75203", 2, 1.0, 1500.00, null, PropertyType.SINGLE_HOUSE, true, true, false));
                propertyRepository.save(createProperty(owner2, "Urban Loft", "Industrial style loft with high ceilings and open plan, close to art galleries.", "300 Artsy Blvd", "Austin", "TX", "78702", 1, 1.0, 2000.00, null, PropertyType.APARTMENT, true, true, false));
                propertyRepository.save(createProperty(owner2, "Executive Condo", "Upscale condo with modern finishes and gym access, secure building.", "150 Elite Dr", "Metropolis", "CA", "90212", 2, 2.0, 3000.00, null, PropertyType.CONDO, true, true, true));
                propertyRepository.save(createProperty(owner2, "Sprawling Ranch Home", "Large ranch-style home with expansive yard, perfect for entertaining.", "999 Countryside Rd", "Houston", "TX", "77004", 5, 4.0, 4500.00, null, PropertyType.SINGLE_HOUSE, true, true, false));
                propertyRepository.save(createProperty(owner2, "Compact Studio", "Affordable studio apartment for minimalist living, near public transport.", "555 Small St", "Dallas", "TX", "75204", 0, 1.0, 900.00, null, PropertyType.APARTMENT, true, true, false));
                propertyRepository.save(createProperty(owner2, "Historic Brownstone", "Renovated brownstone with classic charm, quiet street.", "777 Old Town Rd", "Austin", "TX", "78703", 4, 3.0, 4000.00, null, PropertyType.TOWNHOUSE, true, true, true));
                propertyRepository.save(createProperty(owner2, "Riverside Apartment", "Apartment with beautiful river views, close to walking trails.", "88 Riverfront Pkwy", "Metropolis", "CA", "90213", 2, 2.0, 2300.00, null, PropertyType.APARTMENT, true, true, false));
                propertyRepository.save(createProperty(owner2, "Rural Farmhouse", "Spacious farmhouse on acres of land, perfect for a quiet life.", "1 Farm Rd", "Ruralville", "TX", "77000", 4, 2.5, 2000.00, null, PropertyType.SINGLE_HOUSE, true, true, false));
                propertyRepository.save(createProperty(owner2, "Lakefront Cabin", "Rustic cabin with private lake access, ideal for fishing.", "10 Lake Dr", "Laketown", "CA", "90001", 2, 1.0, null, 500000.00, PropertyType.SINGLE_HOUSE, true, false, true)); // For Sale Only
                propertyRepository.save(createProperty(owner2, "Mountain View Home", "Modern home with stunning mountain views, hiking trails nearby.", "20 Peak Rd", "Mountain City", "CO", "80001", 3, 2.0, 3200.00, null, PropertyType.SINGLE_HOUSE, true, true, false));
                propertyRepository.save(createProperty(owner2, "Downtown High-Rise", "Sleek apartment in a new high-rise building, concierge service.", "333 Sky Scraper", "New York", "NY", "10001", 2, 2.0, 4500.00, null, PropertyType.APARTMENT, true, true, true));
                propertyRepository.save(createProperty(owner2, "Historic District Apartment", "Charming apartment in a historic district, walking distance to shops.", "123 Old St", "Boston", "MA", "02101", 1, 1.0, 1900.00, null, PropertyType.APARTMENT, true, true, false));
                propertyRepository.save(createProperty(owner2, "Spacious Duplex", "Two-unit duplex, great for investment or multi-family living.", "77 Duplex Dr", "Chicago", "IL", "60601", 4, 2.0, 3000.00, null, PropertyType.SINGLE_HOUSE, true, true, true));
                propertyRepository.save(createProperty(owner2, "Quiet Cul-de-sac Home", "Peaceful home at the end of a cul-de-sac, large yard.", "88 Secluded Ct", "Dallas", "TX", "75205", 3, 2.5, 2700.00, null, PropertyType.SINGLE_HOUSE, true, true, false));
                propertyRepository.save(createProperty(owner2, "Waterfront Townhome", "Modern townhome with direct waterfront access and boat dock.", "99 Waterway Ln", "Miami", "FL", "33139", 3, 3.0, 4000.00, null, PropertyType.TOWNHOUSE, true, true, true));
                propertyRepository.save(createProperty(owner2, "Desert Oasis Home", "Unique home with desert landscaping and private pool.", "11 Sand Dune Rd", "Phoenix", "AZ", "85001", 4, 3.0, null, 900000.00, PropertyType.SINGLE_HOUSE, true, false, true)); // For Sale Only
                propertyRepository.save(createProperty(owner2, "Ski-in/Ski-out Condo", "Condo directly on the ski slopes, perfect for winter sports.", "5 Ski Resort", "Aspen", "CO", "81611", 2, 2.0, null, 1200000.00, PropertyType.CONDO, true, false, true)); // For Sale Only

                System.out.println("DEBUG: Demo properties loaded.");
            } else {
                System.out.println("DEBUG: Properties already exist, skipping demo data loading.");
            }
            System.out.println("--- DataLoader Finished ---");
        };
    }

    // Helper method to create a Property object more concisely
    private Property createProperty(User owner, String title, String description, String address, String city, String state, String zipCode,
                                    Integer bedrooms, Double bathrooms, Double rentPrice, Double salePrice, PropertyType propertyType,
                                    Boolean available, Boolean availableForRent, Boolean availableForSale) {
        Property property = new Property();
        property.setTitle(title);
        property.setDescription(description);
        property.setAddress(address);
        property.setCity(city);
        property.setState(state);
        property.setZipCode(zipCode);
        property.setBedrooms(bedrooms);
        property.setBathrooms(bathrooms);
        property.setRentPrice(rentPrice != null ? BigDecimal.valueOf(rentPrice) : null);
        property.setSalePrice(salePrice != null ? BigDecimal.valueOf(salePrice) : null);
        property.setPropertyType(propertyType);
        property.setOwner(owner);
        property.setAvailable(available);
        property.setAvailableForRent(availableForRent);
        property.setAvailableForSale(availableForSale);
        return property;
    }
}
