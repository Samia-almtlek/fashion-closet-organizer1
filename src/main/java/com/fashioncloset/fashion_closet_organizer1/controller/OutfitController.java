package com.fashioncloset.fashion_closet_organizer1.controller;

import com.fashioncloset.fashion_closet_organizer1.model.ClothingItem;
import com.fashioncloset.fashion_closet_organizer1.model.Outfit;
import com.fashioncloset.fashion_closet_organizer1.service.ClothingItemService;
import com.fashioncloset.fashion_closet_organizer1.service.OutfitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/outfits")
public class OutfitController {
    private final OutfitService outfitService;
    private final ClothingItemService clothingItemService;

    public OutfitController(OutfitService outfitService, ClothingItemService clothingItemService) {
        this.outfitService = outfitService;
        this.clothingItemService = clothingItemService;
    }

    @GetMapping
    public String viewOutfits(@RequestParam(required = false) String season, Model model) {
        List<Outfit> outfits = (season != null && !season.isEmpty())
                ? outfitService.getOutfitsBySeason(season)
                : outfitService.getAllOutfits();
        model.addAttribute("outfits", outfits);
        model.addAttribute("clothingItems", clothingItemService.getAllItems());
        model.addAttribute("selectedSeason", season);
        return "outfits";
    }
    @PostMapping("/create")
    public String createOutfit(@RequestParam String season, @RequestParam(required = false) List<Long> itemIds, Model model) {
        System.out.println("=== CREATE OUTFIT ===");
        System.out.println("Season: " + season);
        System.out.println("Item IDs: " + itemIds);

        if (itemIds == null || itemIds.isEmpty()) {
            System.out.println("Error: No items selected!");
            model.addAttribute("error", "Please select at least one clothing item.");
            return "redirect:/outfits";
        }

        try {
            Outfit outfit = new Outfit();
            outfit.setSeason(season);
            List<ClothingItem> selectedItems = clothingItemService.getItemsByIds(itemIds);
            System.out.println("Selected Items: " + selectedItems);

            outfit.setClothingItems(selectedItems);
            Outfit savedOutfit = outfitService.saveOutfit(outfit);
            System.out.println("Outfit saved successfully: " + savedOutfit);
        } catch (Exception e) {
            System.out.println("Error occurred while creating outfit: " + e.getMessage());
            model.addAttribute("error", "An error occurred while creating the outfit.");
            return "redirect:/outfits";
        }

        return "redirect:/outfits";
    }

    @PostMapping("/delete/{id}")
    public String deleteOutfit(@PathVariable Long id) {
        outfitService.deleteOutfit(id);
        return "redirect:/outfits";
    }
}
