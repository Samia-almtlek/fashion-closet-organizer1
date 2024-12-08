package com.fashioncloset.fashion_closet_organizer1.repository;



import com.fashioncloset.fashion_closet_organizer1.model.ClothingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothingItemRepository extends JpaRepository<ClothingItem, Long> {
   /* List<ClothingItem> findByCategory(String category);
    List<ClothingItem> findByColor(String color);
    */
    List<ClothingItem> findBySeason(String season);


}
