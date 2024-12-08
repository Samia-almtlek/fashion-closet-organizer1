package com.fashioncloset.fashion_closet_organizer1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "clothing_item")
public class ClothingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "category", nullable = false)
    private String category; // For example: Tops, Bottoms

    @NotBlank
    @Column(name = "color", nullable = false)
    private String color;

    @NotBlank
    @Column(name = "season", nullable = false)
    private String season; // For example: Summer, Winter

    @Column(name = "image_path", nullable = true)
    private String imagePath;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    private User user; // Links the clothing item to a user

    /**
     * Sets a default image path if none is specified.
     */
    @PrePersist
    @PreUpdate
    public void setDefaultImagePath() {
        if (this.imagePath == null || this.imagePath.isEmpty()) {
            this.imagePath = "default.jpg"; // Default image path
        }
    }
}
