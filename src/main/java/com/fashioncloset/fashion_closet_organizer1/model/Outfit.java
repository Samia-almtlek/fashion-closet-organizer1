package com.fashioncloset.fashion_closet_organizer1.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "outfits")
public class Outfit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "season", nullable = false)
    private String season;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "outfit_clothing_items",
            joinColumns = @JoinColumn(name = "outfit_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "clothing_item_id", referencedColumnName = "id")
    )
    private List<ClothingItem> clothingItems = new ArrayList<>();

    /**
     * Adds a clothing item to the list.
     *
     * @param item the item to be added
     */
    public void addClothingItem(ClothingItem item) {
        if (!clothingItems.contains(item)) {
            clothingItems.add(item);
        }
    }

    /**
     * Removes a clothing item from the list.
     *
     * @param item the item to be removed
     */
    public void removeClothingItem(ClothingItem item) {
        clothingItems.remove(item);
    }
}
