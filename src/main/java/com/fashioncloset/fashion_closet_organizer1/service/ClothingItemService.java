package com.fashioncloset.fashion_closet_organizer1.service;

import com.fashioncloset.fashion_closet_organizer1.model.ClothingItem;
import com.fashioncloset.fashion_closet_organizer1.repository.ClothingItemRepository;
import com.fashioncloset.fashion_closet_organizer1.repository.OutfitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ClothingItemService {
    private final ClothingItemRepository clothingItemRepository;
    private final OutfitRepository outfitRepository;

    public ClothingItemService(ClothingItemRepository clothingItemRepository, OutfitRepository outfitRepository) {
        this.clothingItemRepository = clothingItemRepository;
        this.outfitRepository = outfitRepository;
    }

    public List<ClothingItem> getAllItems() {
        return clothingItemRepository.findAll();
    }

    public ClothingItem saveItem(ClothingItem item) {
        return clothingItemRepository.save(item);
    }

    @Transactional
    public void deleteItem(Long id) {
        outfitRepository.deleteByClothingItemId(id);
        clothingItemRepository.deleteById(id);
    }

    public ClothingItem getItemById(Long id) {
        return clothingItemRepository.findById(id).orElse(null);
    }

    public List<ClothingItem> getItemsBySeason(String season) {
        return clothingItemRepository.findBySeason(season);
    }

    public List<ClothingItem> getItemsByIds(List<Long> ids) {
        return clothingItemRepository.findAllById(ids);
    }

}
