package com.edelala.mur;

import com.edelala.mur.entity.Property;
import com.edelala.mur.entity.PropertyType;
import com.edelala.mur.entity.Role;
import com.edelala.mur.entity.User;
import com.edelala.mur.repo.PropertyRepository;
import com.edelala.mur.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

//@SpringBootApplication
//public class EdelalaApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(EdelalaApplication.class, args);
//	}
//
//	// Configuration to serve static content from the 'uploads' directory
//	@Configuration
//	public static class MvcConfig implements WebMvcConfigurer {
//		@Override
//		public void addResourceHandlers(ResourceHandlerRegistry registry) {
//			// Maps requests starting with /uploads/ to the local 'uploads' directory
//			registry.addResourceHandler("/uploads/**")
//					.addResourceLocations("file:uploads/");
//		}
//	}
//
//	@Bean
//	public CommandLineRunner demoData(PropertyRepository propertyRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
//		return args -> {
//			// Create or update a test RENTER user
//			// FIX: Updated email to renter's real test email
//			Optional<User> existingRenter = userRepository.findByEmail("webalemeadego21@gmail.com");
//			User renter;
//			if (existingRenter.isPresent()) {
//				renter = existingRenter.get();
//				System.out.println("DEBUG: Updating existing Renter: " + renter.getEmail() + " (ID: " + renter.getId() + ")");
//				renter.setFirstName("TestRenter"); // Can keep generic or set a real name
//				renter.setLastName("Account");    // Can keep generic or set a real name
//				renter.setPassword(passwordEncoder.encode("password")); // Re-encode password for consistency
//				renter.setRole(Role.RENTER);
//			} else {
//				renter = new User();
//				renter.setUsername("testrenter");
//				renter.setEmail("webalemeadego21@gmail.com"); // FIX: Set renter's real test email
//				renter.setFirstName("TestRenter");
//				renter.setLastName("Account");
//				renter.setPassword(passwordEncoder.encode("password"));
//				renter.setRole(Role.RENTER);
//				System.out.println("DEBUG: Creating new Renter: " + renter.getEmail());
//			}
//			userRepository.save(renter);
//			System.out.println("DEBUG: Saved Renter with ID: " + renter.getId());
//
//
//			// Create or update a test OWNER user (original generic owner)
//			Optional<User> existingOwner = userRepository.findByEmail("owner@example.com");
//			User owner;
//			if (existingOwner.isPresent()) {
//				owner = existingOwner.get();
//				System.out.println("DEBUG: Updating existing Owner: " + owner.getEmail() + " (ID: " + owner.getId() + ")");
//				owner.setFirstName("Generic");
//				owner.setLastName("Owner");
//				owner.setPassword(passwordEncoder.encode("password"));
//				owner.setRole(Role.OWNER);
//			} else {
//				owner = new User();
//				owner.setUsername("testowner");
//				owner.setEmail("owner@example.com");
//				owner.setFirstName("Generic");
//				owner.setLastName("Owner");
//				owner.setPassword(passwordEncoder.encode("password"));
//				owner.setRole(Role.OWNER);
//				System.out.println("DEBUG: Creating new Owner: " + owner.getEmail());
//			}
//			userRepository.save(owner);
//			System.out.println("DEBUG: Saved Owner with ID: " + owner.getId());
//
//			// Create property for 'owner@example.com' if not exists
//			if (propertyRepository.findByOwner(owner).isEmpty()) {
//				Property property1 = new Property();
//				property1.setTitle("Spacious House with Garden");
//				property1.setDescription("A beautiful suburban house with a large garden and 3 bedrooms.");
//				property1.setAddress("123 Garden Lane");
//				property1.setCity("Anytown");
//				property1.setState("NY");
//				property1.setZipCode("10001");
//				property1.setBedrooms(3);
//				property1.setBathrooms(2);
//				property1.setRentPrice(BigDecimal.valueOf(2500.00));
//				property1.setPropertyType(com.edelala.mur.entity.PropertyType.SINGLE_HOUSE);
//				property1.setOwner(owner);
//				property1.setAvailable(true);
//				property1.setAvailableForRent(true);
//				property1.setAvailableForSale(false);
//				propertyRepository.save(property1);
//
//				Property property3 = new Property();
//				property3.setTitle("Modern Condo in City Center");
//				property3.setDescription("Stylish 2-bedroom condo in the vibrant city center, perfect for urban living.");
//				property3.setAddress("789 Central Ave");
//				property3.setCity("Anytown");
//				property3.setState("NY");
//				property3.setZipCode("10003");
//				property3.setBedrooms(2);
//				property3.setBathrooms(2);
//				property3.setRentPrice(BigDecimal.valueOf(1800.00));
//				property3.setPropertyType(com.edelala.mur.entity.PropertyType.CONDO);
//				property3.setOwner(owner);
//				property3.setAvailable(true);
//				property3.setAvailableForRent(false);
//				property3.setAvailableForSale(true);
//				propertyRepository.save(property3);
//			}
//
//			// Create or update your specific OWNER user ('owner2')
//			// FIX: Updated email to owner's real email and specific first/last name
//			Optional<User> existingOwner2 = userRepository.findByEmail("barnoonni.aangoodha@gmail.com");
//			User owner2;
//			if (existingOwner2.isPresent()) {
//				owner2 = existingOwner2.get();
//				System.out.println("DEBUG: Updating existing Owner2: " + owner2.getEmail() + " (ID: " + owner2.getId() + ")");
//				owner2.setFirstName("Murad");
//				owner2.setLastName("Abdulmalik");
//				owner2.setPassword(passwordEncoder.encode("password"));
//				owner2.setRole(Role.OWNER);
//			} else {
//				owner2 = new User();
//				owner2.setUsername("muradowner"); // Changed username to be unique for this specific owner
//				owner2.setEmail("barnoonni.aangoodha@gmail.com"); // FIX: Set owner's real email
//				owner2.setFirstName("Murad");
//				owner2.setLastName("Abdulmalik");
//				owner2.setPassword(passwordEncoder.encode("password"));
//				owner2.setRole(Role.OWNER);
//				System.out.println("DEBUG: Creating new Owner2: " + owner2.getEmail());
//			}
//			userRepository.save(owner2);
//			System.out.println("DEBUG: Saved Owner2 with ID: " + owner2.getId());
//
//			// Create property for your specific owner if not exists
//			if (propertyRepository.findByOwner(owner2).isEmpty()) {
//				Property property2 = new Property();
//				property2.setTitle("Spacious Family Home");
//				property2.setDescription("A beautiful house with a large backyard, perfect for families.");
//				property2.setAddress("456 Oak Ave");
//				property2.setCity("Anytown");
//				property2.setState("NY");
//				property2.setZipCode("10002");
//				property2.setBedrooms(4);
//				property2.setBathrooms(3);
//				property2.setRentPrice(BigDecimal.valueOf(3500.00));
//				property2.setPropertyType(com.edelala.mur.entity.PropertyType.SINGLE_HOUSE);
//				property2.setOwner(owner2);
//				property2.setAvailable(true);
//				property2.setAvailableForRent(true);
//				property2.setAvailableForSale(true);
//				propertyRepository.save(property2);
//			}
//		};
//	}
//} jul 10 comment

@SpringBootApplication
public class EdelalaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdelalaApplication.class, args);
	}

	// Configuration to serve static content from the 'uploads' directory
	@Configuration
	public static class MvcConfig implements WebMvcConfigurer {
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			// Maps requests starting with /uploads/ to the local 'uploads' directory
			registry.addResourceHandler("/uploads/**")
					.addResourceLocations("file:uploads/");
		}
	}

//	@Bean
//	public CommandLineRunner demoData(PropertyRepository propertyRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
//		return args -> {
//			// Create or update a test RENTER user
//			Optional<User> existingRenter = userRepository.findByEmail("webalemeadego21@gmail.com");
//			User renter;
//			if (existingRenter.isPresent()) {
//				renter = existingRenter.get();
//				System.out.println("DEBUG: Updating existing Renter: " + renter.getEmail() + " (ID: " + renter.getId() + ")");
//				renter.setFirstName("TestRenter");
//				renter.setLastName("Account");
//				renter.setPassword(passwordEncoder.encode("password"));
//				renter.setRole(Role.RENTER);
//			} else {
//				renter = new User();
//				renter.setUsername("testrenter");
//				renter.setEmail("webalemeadego21@gmail.com");
//				renter.setFirstName("TestRenter");
//				renter.setLastName("Account");
//				renter.setPassword(passwordEncoder.encode("password"));
//				renter.setRole(Role.RENTER);
//				System.out.println("DEBUG: Creating new Renter: " + renter.getEmail());
//			}
//			userRepository.save(renter);
//			System.out.println("DEBUG: Saved Renter with ID: " + renter.getId());
//
//
//			// Create or update a test OWNER user (original generic owner)
//			Optional<User> existingOwner = userRepository.findByEmail("owner@example.com");
//			User owner;
//			if (existingOwner.isPresent()) {
//				owner = existingOwner.get();
//				System.out.println("DEBUG: Updating existing Owner: " + owner.getEmail() + " (ID: " + owner.getId() + ")");
//				owner.setFirstName("Generic");
//				owner.setLastName("Owner");
//				owner.setPassword(passwordEncoder.encode("password"));
//				owner.setRole(Role.OWNER);
//			} else {
//				owner = new User();
//				owner.setUsername("testowner");
//				owner.setEmail("owner@example.com");
//				owner.setFirstName("Generic");
//				owner.setLastName("Owner");
//				owner.setPassword(passwordEncoder.encode("password"));
//				owner.setRole(Role.OWNER);
//				System.out.println("DEBUG: Creating new Owner: " + owner.getEmail());
//			}
//			userRepository.save(owner);
//			System.out.println("DEBUG: Saved Owner with ID: " + owner.getId());
//
//			// Create property for 'owner@example.com' if not exists
//			// FIX: Changed findByOwner to findByOwnerId
//			if (propertyRepository.findByOwnerId(owner.getId()).isEmpty()) {
//				Property property1 = new Property();
//				property1.setTitle("Spacious House with Garden");
//				property1.setDescription("A beautiful suburban house with a large garden and 3 bedrooms.");
//				property1.setAddress("123 Garden Lane");
//				property1.setCity("Anytown");
//				property1.setState("NY");
//				property1.setZipCode("10001");
//				property1.setBedrooms(3);
//				property1.setBathrooms(2.0); // Ensure this matches Property entity type (Double)
//				property1.setRentPrice(BigDecimal.valueOf(2500.00));
//				property1.setPropertyType(PropertyType.SINGLE_HOUSE); // Use direct enum
//				property1.setOwner(owner);
//				property1.setAvailable(true);
//				property1.setAvailableForRent(true);
//				property1.setAvailableForSale(false);
//				propertyRepository.save(property1);
//
//				Property property3 = new Property();
//				property3.setTitle("Modern Condo in City Center");
//				property3.setDescription("Stylish 2-bedroom condo in the vibrant city center, perfect for urban living.");
//				property3.setAddress("789 Central Ave");
//				property3.setCity("Anytown");
//				property3.setState("NY");
//				property3.setZipCode("10003");
//				property3.setBedrooms(2);
//				property3.setBathrooms(2.0); // Ensure this matches Property entity type (Double)
//				property3.setRentPrice(BigDecimal.valueOf(1800.00));
//				property3.setPropertyType(PropertyType.CONDO); // Use direct enum
//				property3.setOwner(owner);
//				property3.setAvailable(true);
//				property3.setAvailableForRent(false);
//				property3.setAvailableForSale(true);
//				propertyRepository.save(property3);
//			}
//
//			// Create or update your specific OWNER user ('owner2')
//			Optional<User> existingOwner2 = userRepository.findByEmail("barnoonni.aangoodha@gmail.com");
//			User owner2;
//			if (existingOwner2.isPresent()) {
//				owner2 = existingOwner2.get();
//				System.out.println("DEBUG: Updating existing Owner2: " + owner2.getEmail() + " (ID: " + owner2.getId() + ")");
//				owner2.setFirstName("Murad");
//				owner2.setLastName("Abdulmalik");
//				owner2.setPassword(passwordEncoder.encode("password"));
//				owner2.setRole(Role.OWNER);
//			} else {
//				owner2 = new User();
//				owner2.setUsername("muradowner");
//				owner2.setEmail("barnoonni.aangoodha@gmail.com");
//				owner2.setFirstName("Murad");
//				owner2.setLastName("Abdulmalik");
//				owner2.setPassword(passwordEncoder.encode("password"));
//				owner2.setRole(Role.OWNER);
//				System.out.println("DEBUG: Creating new Owner2: " + owner2.getEmail());
//			}
//			userRepository.save(owner2);
//			System.out.println("DEBUG: Saved Owner2 with ID: " + owner2.getId());
//
//			// Create property for your specific owner if not exists
//			// FIX: Changed findByOwner to findByOwnerId
//			if (propertyRepository.findByOwnerId(owner2.getId()).isEmpty()) {
//				Property property2 = new Property();
//				property2.setTitle("Spacious Family Home");
//				property2.setDescription("A beautiful house with a large backyard, perfect for families.");
//				property2.setAddress("456 Oak Ave");
//				property2.setCity("Anytown");
//				property2.setState("NY");
//				property2.setZipCode("10002");
//				property2.setBedrooms(4);
//				property2.setBathrooms(3.0); // Ensure this matches Property entity type (Double)
//				property2.setRentPrice(BigDecimal.valueOf(3500.00));
//				property2.setPropertyType(PropertyType.SINGLE_HOUSE); // Use direct enum
//				property2.setOwner(owner2);
//				property2.setAvailable(true);
//				property2.setAvailableForRent(true);
//				property2.setAvailableForSale(true);
//				propertyRepository.save(property2);
//			}
//		};
//	}
}
