package com.edelala.mur.entity;



//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Property {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String title;
//    private String description;
//    private String address;
//    private String city;
//    private String state;
//    private String zipCode;
//    @Enumerated(EnumType.STRING)
//    private PropertyType propertyType; // APARTMENT, CONDO, TOWNHOUSE, SINGLE_HOUSE
//    private Integer bedrooms;
//    private Integer bathrooms;
//    private BigDecimal rentPrice; // For rent listings
//    private BigDecimal salePrice; // For sale listings (can be null if only for rent)
//    private boolean isAvailableForRent = true;
//    private boolean isAvailableForSale = false;
//    @ManyToOne
//    @JoinColumn(name = "owner_id")
//    private User owner;
//    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Image> images = new ArrayList<>();
//
//    // ... other property details
//
//    //Getter and Setter
//
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }
//
//    public String getZipCode() {
//        return zipCode;
//    }
//
//    public void setZipCode(String zipCode) {
//        this.zipCode = zipCode;
//    }
//
//    public PropertyType getPropertyType() {
//        return propertyType;
//    }
//
//    public void setPropertyType(PropertyType propertyType) {
//        this.propertyType = propertyType;
//    }
//
//    public Integer getBedrooms() {
//        return bedrooms;
//    }
//
//    public void setBedrooms(Integer bedrooms) {
//        this.bedrooms = bedrooms;
//    }
//
//    public Integer getBathrooms() {
//        return bathrooms;
//    }
//
//    public void setBathrooms(Integer bathrooms) {
//        this.bathrooms = bathrooms;
//    }
//
//    public BigDecimal getRentPrice() {
//        return rentPrice;
//    }
//
//    public void setRentPrice(BigDecimal rentPrice) {
//        this.rentPrice = rentPrice;
//    }
//
//    public BigDecimal getSalePrice() {
//        return salePrice;
//    }
//
//    public void setSalePrice(BigDecimal salePrice) {
//        this.salePrice = salePrice;
//    }
//
//    public boolean isAvailableForRent() {
//        return isAvailableForRent;
//    }
//
//    public void setAvailableForRent(boolean availableForRent) {
//        isAvailableForRent = availableForRent;
//    }
//
//    public boolean isAvailableForSale() {
//        return isAvailableForSale;
//    }
//
//    public void setAvailableForSale(boolean availableForSale) {
//        isAvailableForSale = availableForSale;
//    }
//
//    public User getOwner() {
//        return owner;
//    }
//
//    public void setOwner(User owner) {
//        this.owner = owner;
//    }
//
//    public List<Image> getImages() {
//        return images;
//    }
//
//    public void setImages(List<Image> images) {
//        this.images = images;
//    }
//}

//@Entity
//@Data // Lombok annotation for getters, setters, equals, hashCode, toString
//@NoArgsConstructor // Lombok annotation for no-arg constructor
//@AllArgsConstructor // Lombok annotation for all-arg constructor
//public class Property {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String title;
//    private String description;
//    private String address;
//    private String city;
//    private String state;
//    private String zipCode;
//
//    @Enumerated(EnumType.STRING)
//    private PropertyType propertyType; // APARTMENT, CONDO, TOWNHOUSE, SINGLE_HOUSE
//
//    private Integer bedrooms;
//    private Integer bathrooms;
//    private BigDecimal rentPrice; // For rent listings
//    private BigDecimal salePrice; // For sale listings (can be null if only for rent)
//
//    // --- Added missing 'isAvailable' field ---
//    private boolean isAvailable = true; // General availability status
//    // --- End of added field ---
//
//    private boolean isAvailableForRent = true;
//    private boolean isAvailableForSale = false;
//
//    @ManyToOne(fetch = FetchType.LAZY) // Use LAZY fetch to avoid N+1 problems
//    @JoinColumn(name = "owner_id")
//    private User owner;
//
//    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<Image> images = new ArrayList<>();
//
//    // Lombok's @Data annotation handles all standard getters and setters.
//    // If not using Lombok, you would need to define them explicitly here.
//    // For reference, here are the explicit getters/setters for 'isAvailable'
//    public boolean isAvailable() {
//        return isAvailable;
//    }
//
//    public void setAvailable(boolean available) {
//        isAvailable = available;
//    }
//
//    // Your existing getters and setters (Lombok will generate them)
//    // If you're not using Lombok, ensure all your existing getters/setters are present.
//    // For example, from your provided code:
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//    public String getTitle() { return title; }
//    public void setTitle(String title) { this.title = title; }
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//    public String getAddress() { return address; }
//    public void setAddress(String address) { this.address = address; }
//    public String getCity() { return city; }
//    public void setCity(String city) { this.city = city; }
//    public String getState() { return state; }
//    public void setState(String state) { this.state = state; }
//    public String getZipCode() { return zipCode; }
//    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
//    public PropertyType getPropertyType() { return propertyType; }
//    public void setPropertyType(PropertyType propertyType) { this.propertyType = propertyType; }
//    public Integer getBedrooms() { return bedrooms; }
//    public void setBedrooms(Integer bedrooms) { this.bedrooms = bedrooms; }
//    public Integer getBathrooms() { return bathrooms; }
//    public void setBathrooms(Integer bathrooms) { this.bathrooms = bathrooms; }
//    public BigDecimal getRentPrice() { return rentPrice; }
//    public void setRentPrice(BigDecimal rentPrice) { this.rentPrice = rentPrice; }
//    public BigDecimal getSalePrice() { return salePrice; }
//    public void setSalePrice(BigDecimal salePrice) { this.salePrice = salePrice; }
//    public boolean isAvailableForRent() { return isAvailableForRent; }
//    public void setAvailableForRent(boolean availableForRent) { isAvailableForRent = availableForRent; }
//    public boolean isAvailableForSale() { return isAvailableForSale; }
//    public void setAvailableForSale(boolean availableForSale) { isAvailableForSale = availableForSale; }
//    public User getOwner() { return owner; }
//    public void setOwner(User owner) { this.owner = owner; }
//    public List<Image> getImages() { return images; }
//    public void setImages(List<Image> images) { this.images = images; }
//}


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference; // Import JsonManagedReference
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // <--- NEW: Add this annotation
//public class Property {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String title;
//    private String description;
//    private String address;
//    private String city;
//    private String state;
//    private String zipCode;
//
//    @Enumerated(EnumType.STRING)
//    private PropertyType propertyType; // APARTMENT, CONDO, TOWNHOUSE, SINGLE_HOUSE
//
//    private Integer bedrooms;
//    private Integer bathrooms;
//    private BigDecimal rentPrice; // For rent listings
//    private BigDecimal salePrice; // For sale listings (can be null if only for rent)
//
//    private boolean isAvailable = true; // General availability status
//    private boolean isAvailableForRent = true;
//    private boolean isAvailableForSale = false;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "owner_id")
//    @JsonIgnore // Still ignore owner to prevent lazy loading issues on Property listing.
//    // If owner details are needed, a DTO would be a better approach.
//    private User owner;
//
//    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    @JsonManagedReference
//    private List<Image> images = new ArrayList<>();
//
//    // Lombok's @Data annotation handles all standard getters and setters.
//    // Explicit getters and setters are listed below for clarity, but if Lombok is active,
//    // they are not strictly necessary to be written out.
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//    public String getTitle() { return title; }
//    public void setTitle(String title) { this.title = title; }
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//    public String getAddress() { return address; }
//    public void setAddress(String address) { this.address = address; }
//    public String getCity() { return city; }
//    public void setCity(String city) { this.city = city; }
//    public String getState() { return state; }
//    public void setState(String state) { this.state = state; }
//    public String getZipCode() { return zipCode; }
//    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
//    public PropertyType getPropertyType() { return propertyType; }
//    public void setPropertyType(PropertyType propertyType) { this.propertyType = propertyType; }
//    public Integer getBedrooms() { return bedrooms; }
//    public void setBedrooms(Integer bedrooms) { this.bedrooms = bedrooms; }
//    public Integer getBathrooms() { return bathrooms; }
//    public void setBathrooms(Integer bathrooms) { this.bathrooms = bathrooms; }
//    public BigDecimal getRentPrice() { return rentPrice; }
//    public void setRentPrice(BigDecimal rentPrice) { this.rentPrice = rentPrice; }
//    public BigDecimal getSalePrice() { return salePrice; }
//    public void setSalePrice(BigDecimal salePrice) { this.salePrice = salePrice; }
//    public boolean isAvailable() { return isAvailable; }
//    public void setAvailable(boolean available) { isAvailable = available; }
//    public boolean isAvailableForRent() { return isAvailableForRent; }
//    public void setAvailableForRent(boolean availableForRent) { isAvailableForRent = availableForRent; }
//    public boolean isAvailableForSale() { return isAvailableForSale; }
//    public void setAvailableForSale(boolean availableForSale) { isAvailableForSale = availableForSale; }
//    public User getOwner() { return owner; } // Added getter for owner as it's a field
//    public void setOwner(User owner) { this.owner = owner; }
//    public List<Image> getImages() { return images; }
//    public void setImages(List<Image> images) { this.images = images; }
//}

//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Handles lazy loading proxies for JSON serialization
//public class Property {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String title;
//    private String description;
//    private String address;
//    private String city;
//    private String state;
//    private String zipCode;
//
//    @Enumerated(EnumType.STRING)
//    private PropertyType propertyType; // APARTMENT, CONDO, TOWNHOUSE, SINGLE_HOUSE
//
//    private Integer bedrooms;
//    private Integer bathrooms;
//    private BigDecimal rentPrice; // For rent listings
//    private BigDecimal salePrice; // For sale listings (can be null if only for rent)
//
//    private boolean available = true; // General availability status (Spring Boot auto-generates isAvailable() for this)
//    private boolean availableForRent = true; // <--- Check this field (Spring Boot auto-generates isAvailableForRent())
//    private boolean availableForSale = false;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "owner_id")
//    @JsonIgnore // Important: Ignore owner to prevent circular references and lazy loading issues during Property serialization.
//    // If owner details are needed on the frontend, a DTO would be a better approach for specific views.
//    private User owner;
//
//    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    @JsonManagedReference // Handles circular reference with Image entity
//    private List<Image> images = new ArrayList<>();
//
//    // Lombok's @Data annotation automatically handles all standard getters and setters.
//    // So, explicit getters like getAvailableForRent() are NOT required if @Data is present,
//    // as Lombok will generate isAvailableForRent() and setAvailableForRent().
//    // If you were not using Lombok, you would explicitly write:
//    // public boolean isAvailable() { return available; }
//    // public void setAvailable(boolean available) { this.available = available; }
//    // public boolean isAvailableForRent() { return availableForRent; } <--- This is the key getter
//    // public void setAvailableForRent(boolean availableForRent) { this.availableForRent = availableForRent; }
//    // etc.
//}

//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Handles lazy loading proxies for JSON serialization
//public class Property {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String title;
//    private String description;
//    private String address;
//    private String city;
//    private String state;
//    private String zipCode;
//
//    @Enumerated(EnumType.STRING)
//    private PropertyType propertyType; // APARTMENT, CONDO, TOWNHOUSE, SINGLE_HOUSE
//
//    private Integer bedrooms;
//    private Integer bathrooms;
//    private BigDecimal rentPrice; // For rent listings
//    private BigDecimal salePrice; // For sale listings (can be null if only for rent)
//
//    private boolean available = true; // General availability status (Spring Boot auto-generates isAvailable() for this)
//    private boolean availableForRent = true; // <--- Check this field (Spring Boot auto-generates isAvailableForRent())
//    private boolean availableForSale = false;
//
//    @ManyToOne(fetch = FetchType.EAGER) // Changed to EAGER to ensure owner details are loaded with property
//    @JoinColumn(name = "owner_id")
//    // @JsonIgnore // REMOVED: Do NOT ignore owner so frontend can display name
//    private User owner;
//
//    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    @JsonManagedReference // Handles circular reference with Image entity
//    private List<Image> images = new ArrayList<>();
//
//    // Lombok's @Data annotation handles all standard getters and setters.
//    // Explicit getters and setters are listed below for clarity, but if Lombok is active,
//    // they are not strictly necessary to be written out.
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//    public String getTitle() { return title; }
//    public void setTitle(String title) { this.title = title; }
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//    public String getAddress() { return address; }
//    public void setAddress(String address) { this.address = address; }
//    public String getCity() { return city; }
//    public void setCity(String city) { this.city = city; }
//    public String getState() { return state; }
//    public void setState(String state) { this.state = state; }
//    public String getZipCode() { return zipCode; }
//    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
//    public PropertyType getPropertyType() { return propertyType; }
//    public void setPropertyType(PropertyType propertyType) { this.propertyType = propertyType; }
//    public Integer getBedrooms() { return bedrooms; }
//    public void setBedrooms(Integer bedrooms) { this.bedrooms = bedrooms; }
//    public Integer getBathrooms() { return bathrooms; }
//    public void setBathrooms(Integer bathrooms) { this.bathrooms = bathrooms; }
//    public BigDecimal getRentPrice() { return rentPrice; }
//    public void setRentPrice(BigDecimal rentPrice) { this.rentPrice = rentPrice; }
//    public BigDecimal getSalePrice() { return salePrice; }
//    public void setSalePrice(BigDecimal salePrice) { this.salePrice = salePrice; }
//
//    // FIX: Corrected assignment in boolean setters
//    public boolean isAvailable() { return available; }
//    public void setAvailable(boolean available) { this.available = available; } // Corrected line
//    public boolean isAvailableForRent() { return availableForRent; }
//    public void setAvailableForRent(boolean availableForRent) { this.availableForRent = availableForRent; }
//    public boolean isAvailableForSale() { return availableForSale; }
//    public void setAvailableForSale(boolean availableForSale) { this.availableForSale = availableForSale; }
//
//    public User getOwner() { return owner; }
//    public void setOwner(User owner) { this.owner = owner; }
//    public List<Image> getImages() { return images; }
//    public void setImages(List<Image> images) { this.images = images; }
//}
//jul 10 comment to improve filter

//@Entity
//@Data // Lombok will generate getters, setters, toString, equals, hashCode
//@NoArgsConstructor
//@AllArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Handles lazy loading proxies for JSON serialization
//public class Property {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String title;
//    private String description;
//    private String address;
//    private String city;
//    private String state;
//    private String zipCode;
//
//    @Enumerated(EnumType.STRING)
//    private PropertyType propertyType; // APARTMENT, CONDO, TOWNHOUSE, SINGLE_HOUSE
//
//    private Integer bedrooms;
//    private Double bathrooms; // Changed to Double as per your DTO and usage
//
//    private BigDecimal rentPrice; // For rent listings
//    private BigDecimal salePrice; // For sale listings (can be null if only for rent)
//
//    // --- CHANGED PRIMITIVE BOOLEANS TO BOOLEAN WRAPPER TYPES ---
//    private Boolean available = true; // General availability status
//    private Boolean availableForRent = true;
//    private Boolean availableForSale = false;
//    // --- END CHANGES ---
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "owner_id")
//    private User owner;
//
//    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    @JsonManagedReference
//    private List<Image> images = new ArrayList<>();
//
//    // Lombok's @Data annotation handles all standard getters and setters.
//    // Explicit getters and setters for the boolean fields are no longer needed
//    // as Lombok will correctly generate getAvailable(), getAvailableForRent(), etc.
//    // for Boolean wrapper types.
//    // Keep other explicit getters/setters if you have specific reasons for them,
//    // otherwise, Lombok handles them.
//
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//    public String getTitle() { return title; }
//    public void setTitle(String title) { this.title = title; }
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//    public String getAddress() { return address; }
//    public void setAddress(String address) { this.address = address; }
//    public String getCity() { return city; }
//    public void setCity(String city) { this.city = city; }
//    public String getState() { return state; }
//    public void setState(String state) { this.state = state; }
//    public String getZipCode() { return zipCode; }
//    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
//    public PropertyType getPropertyType() { return propertyType; }
//    public void setPropertyType(PropertyType propertyType) { this.propertyType = propertyType; }
//    public Integer getBedrooms() { return bedrooms; }
//    public void setBedrooms(Integer bedrooms) { this.bedrooms = bedrooms; }
//    public Double getBathrooms() { return bathrooms; } // Keep as Double
//    public void setBathrooms(Double bathrooms) { this.bathrooms = bathrooms; } // Keep as Double
//    public BigDecimal getRentPrice() { return rentPrice; }
//    public void setRentPrice(BigDecimal rentPrice) { this.rentPrice = rentPrice; }
//    public BigDecimal getSalePrice() { return salePrice; }
//    public void setSalePrice(BigDecimal salePrice) { this.salePrice = salePrice; }
//
//    // Removed explicit boolean getters/setters, Lombok will generate getAvailable(), getAvailableForRent(), etc.
//
//    public User getOwner() { return owner; }
//    public void setOwner(User owner) { this.owner = owner; }
//    public List<Image> getImages() { return images; }
//    public void setImages(List<Image> images) { this.images = images; }
//}
//
//july 29

@Entity
@Data // Lombok will generate getters, setters, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Handles lazy loading proxies for JSON serialization
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String address;
    private String city;
    private String state;
    private String zipCode;

    @Enumerated(EnumType.STRING)
    private PropertyType propertyType; // APARTMENT, CONDO, TOWNHOUSE, SINGLE_HOUSE

    private Integer bedrooms;
    private Double bathrooms; // Changed to Double as per your DTO and usage

    private BigDecimal rentPrice; // For rent listings
    private BigDecimal salePrice; // For sale listings (can be null if only for rent)

    private Boolean available = true; // General availability status
    private Boolean availableForRent = true;
    private Boolean availableForSale = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false) // FIX: Added nullable = false
    private User owner;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Image> images = new ArrayList<>();

    // Lombok's @Data annotation handles all standard getters and setters.
    // Explicit getters and setters for the boolean fields are no longer needed
    // as Lombok will correctly generate getAvailable(), getAvailableForRent(), etc.
    // for Boolean wrapper types.
    // Keep other explicit getters/setters if you have specific reasons for them,
    // otherwise, Lombok handles them.

    // Removed explicit getters/setters for fields already covered by @Data
    // If you had custom logic in these, you'd need to keep them.
    // For standard getters/setters, @Data is sufficient.
}
