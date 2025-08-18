package com.edelala.mur.entity;

import com.fasterxml.jackson.annotation.JsonBackReference; // <-- NEW: Import this annotation
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // Added nullable=false for imageUrl, as it should always have a value
    private String imageUrl; // Store the path to the image

    @ManyToOne(fetch = FetchType.LAZY) // Added FetchType.LAZY for performance
    @JoinColumn(name = "property_id", nullable = false) // Added nullable=false as every image must belong to a property
    @JsonBackReference // <-- NEW: This tells Jackson to ignore this side during serialization
    private Property property;

    // Lombok's @Data annotation already handles these, but if you've removed Lombok,
    // ensure they are present.
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

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
