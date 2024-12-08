package com.fashioncloset.fashion_closet_organizer1.repository;

import com.fashioncloset.fashion_closet_organizer1.model.Outfit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OutfitRepository extends JpaRepository<Outfit, Long> {
    List<Outfit> findBySeason(String season);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM outfit_clothing_items WHERE clothing_item_id = :clothingItemId", nativeQuery = true)
    void deleteByClothingItemId(Long clothingItemId);
}
