package com.edelala.mur.dto;

import com.edelala.mur.entity.PropertyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;


import java.math.BigDecimal;
import java.util.List;

//@Data
//public class PropertyDTO {
//
//    @NotBlank(message = "Title is required")
//    private String title;
//
//    @NotBlank(message = "Description is required")
//    private String description;
//
//    @NotBlank(message = "Address is required")
//    private String address;
//
//    @NotBlank(message = "City is required")
//    private String city;
//
//    @NotBlank(message = "State is required")
//    private String state;
//
//    @NotBlank(message = "Zip code is required")
//    private String zipCode;
//
//    @NotNull(message = "Property type is required")
//    private PropertyType propertyType;
//
//    @NotNull(message = "Number of bedrooms is required")
//    @Positive(message = "Bedrooms must be positive")
//    private Integer bedrooms;
//
//    @NotNull(message = "Number of bathrooms is required")
//    @Positive(message = "Bathrooms must be positive")
//    private Integer bathrooms;
//
//    @NotNull(message = "Rent price is required for rental listings")
//    @Positive(message = "Rent price must be positive")
//    private BigDecimal rentPrice;
//
//    private BigDecimal salePrice; // Optional for sale listings
//
//    // You might include other relevant fields here
//
//    //Getter and Setter
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
//}


/*  jun 24 working code

public class PropertyDTO {


@NotBlank(message = "Title is required")

private String title;


@NotBlank(message = "Description is required")

private String description;


@NotBlank(message = "Address is required")

private String address;


@NotBlank(message = "City is required")

private String city;


@NotBlank(message = "State is required")

private String state;


@NotBlank(message = "Zip code is required")

private String zipCode;


@NotNull(message = "Property type is required")

private PropertyType propertyType;

@NotNull(message = "Number of bedrooms is required")

@Positive(message = "Bedrooms must be positive")

private Integer bedrooms;

@NotNull(message = "Number of bathrooms is required")

@Positive(message = "Bathrooms must be positive")

private Integer bathrooms;

@NotNull(message = "Rent price is required for rental listings")

@Positive(message = "Rent price must be positive")

private BigDecimal rentPrice;

private BigDecimal salePrice; // Optional for sale listings

// --- Added missing boolean fields for availability ---

private boolean isAvailable;

private boolean isAvailableForRent;

private boolean isAvailableForSale;

// --- End of added fields ---



// Getter and Setter for existing fields



public String getTitle() {

return title;

}

public void setTitle(String title) {

this.title = title;

}

public String getDescription() {

return description;

}



public void setDescription(String description) {

this.description = description;

}



public String getAddress() {

return address;

}



public void setAddress(String address) {

this.address = address;

}



public String getCity() {

return city;

}



public void setCity(String city) {

this.city = city;

}



public String getState() {

return state;

}



public void setState(String state) {

this.state = state;

}



public String getZipCode() {

return zipCode;

}



public void setZipCode(String zipCode) {

this.zipCode = zipCode;

}



public PropertyType getPropertyType() {

return propertyType;

}



public void setPropertyType(PropertyType propertyType) {

this.propertyType = propertyType;

}



public Integer getBedrooms() {

return bedrooms;

}



public void setBedrooms(Integer bedrooms) {

this.bedrooms = bedrooms;

}



public Integer getBathrooms() {

return bathrooms;

}



public void setBathrooms(Integer bathrooms) {

this.bathrooms = bathrooms;

}



public BigDecimal getRentPrice() {

return rentPrice;

}



public void setRentPrice(BigDecimal rentPrice) {

this.rentPrice = rentPrice;

}



public BigDecimal getSalePrice() {

return salePrice;

}



public void setSalePrice(BigDecimal salePrice) {

this.salePrice = salePrice;

}



// --- Getters and Setters for new boolean fields ---

public boolean isAvailable() {

return isAvailable;

}



public void setAvailable(boolean available) {

isAvailable = available;

}



public boolean isAvailableForRent() {

return isAvailableForRent;

}



public void setAvailableForRent(boolean availableForRent) {

isAvailableForRent = availableForRent;

}



public boolean isAvailableForSale() {

return isAvailableForSale;

}



public void setAvailableForSale(boolean availableForSale) {

isAvailableForSale = availableForSale;

}

// --- End of new getters/setters ---

}
 */

public class PropertyDTO {

    private Long id; // Added ID for existing properties
    private Long ownerId; // Added for owner identification
    private String ownerFirstName; // Added for owner display
    private String ownerLastName; // Added for owner display

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Zip code is required")
    private String zipCode;

    @NotNull(message = "Property type is required")
    private PropertyType propertyType;

    @NotNull(message = "Number of bedrooms is required")
    @Positive(message = "Bedrooms must be positive")
    private Integer bedrooms;

    @NotNull(message = "Number of bathrooms is required")
    @Positive(message = "Bathrooms must be positive")
    private Double bathrooms; // Changed to Double as per your entity/service

    // @NotNull(message = "Rent price is required for rental listings") // Removed @NotNull as it might be optional if only for sale
    @Positive(message = "Rent price must be positive")
    private BigDecimal rentPrice;

    private BigDecimal salePrice; // Optional for sale listings

    private Boolean available; // Changed to Boolean wrapper type for nullability
    private Boolean availableForRent; // Changed to Boolean wrapper type for nullability
    private Boolean availableForSale; // Changed to Boolean wrapper type for nullability

    private List<ImageDTO> images; // List of image DTOs

    // Nested DTO for images
    public static class ImageDTO {
        private Long id;
        private String imageUrl;

        public ImageDTO(Long id, String imageUrl) {
            this.id = id;
            this.imageUrl = imageUrl;
        }

        // Getters and Setters for ImageDTO
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }


    // --- Getters and Setters for all fields ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Double getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Double bathrooms) {
        this.bathrooms = bathrooms;
    }

    public BigDecimal getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(BigDecimal rentPrice) {
        this.rentPrice = rentPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    // Changed from isAvailable() to getAvailable() for consistency with BeanUtils.copyProperties
    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    // Changed from isAvailableForRent() to getAvailableForRent() for consistency
    public Boolean getAvailableForRent() {
        return availableForRent;
    }

    public void setAvailableForRent(Boolean availableForRent) {
        this.availableForRent = availableForRent;
    }

    // Changed from isAvailableForSale() to getAvailableForSale() for consistency
    public Boolean getAvailableForSale() {
        return availableForSale;
    }

    public void setAvailableForSale(Boolean availableForSale) {
        this.availableForSale = availableForSale;
    }

    public List<ImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageDTO> images) {
        this.images = images;
    }
}
//july 10 modify