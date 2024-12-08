package com.fashioncloset.fashion_closet_organizer1.service;

import com.fashioncloset.fashion_closet_organizer1.model.ClothingItem;
import com.fashioncloset.fashion_closet_organizer1.model.Outfit;
import com.fashioncloset.fashion_closet_organizer1.repository.ClothingItemRepository;
import com.fashioncloset.fashion_closet_organizer1.repository.OutfitRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OutfitService {
    private final OutfitRepository outfitRepository;
    private final ClothingItemRepository clothingItemRepository;

    public OutfitService(OutfitRepository outfitRepository, ClothingItemRepository clothingItemRepository) {
        this.outfitRepository = outfitRepository;
        this.clothingItemRepository = clothingItemRepository;
    }

    public List<Outfit> getAllOutfits() {
        return outfitRepository.findAll();
    }

    public List<Outfit> getOutfitsBySeason(String season) {
        return outfitRepository.findBySeason(season);
    }

    @Transactional
    public Outfit saveOutfit(Outfit outfit) {
        // Ensure all ClothingItem entities are managed by the persistence context
        List<ClothingItem> managedItems = outfit.getClothingItems().stream()
                .map(item -> clothingItemRepository.findById(item.getId()).orElseThrow(
                        () -> new IllegalArgumentException("ClothingItem with ID " + item.getId() + " does not exist.")
                ))
                .collect(Collectors.toList());

        // Set the clothing items to the Outfit
        outfit.setClothingItems(managedItems);

        // Save the Outfit along with its associated items
        return outfitRepository.save(outfit);
    }

    public void deleteOutfit(Long id) {
        outfitRepository.deleteById(id);
    }
}
