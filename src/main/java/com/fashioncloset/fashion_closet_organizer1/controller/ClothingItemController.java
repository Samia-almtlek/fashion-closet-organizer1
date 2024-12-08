package com.fashioncloset.fashion_closet_organizer1.controller;

import com.fashioncloset.fashion_closet_organizer1.model.ClothingItem;
import com.fashioncloset.fashion_closet_organizer1.service.ClothingItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/closet")
public class ClothingItemController {

    private static final Logger logger = LoggerFactory.getLogger(ClothingItemController.class);
    private final ClothingItemService clothingItemService;

    public ClothingItemController(ClothingItemService clothingItemService) {
        this.clothingItemService = clothingItemService;
    }

    @GetMapping
    public String viewCloset(Model model) {
        logger.info("Fetching all clothing items...");
        model.addAttribute("items", clothingItemService.getAllItems());
        return "closet";
    }

    @PostMapping("/add")
    public String addClothingItem(@ModelAttribute ClothingItem item, @RequestParam("image") MultipartFile image, Model model) {
        try {
            logger.info("Processing file upload for: {}", image.getOriginalFilename());

            if (image.isEmpty()) {
                logger.error("Uploaded file is empty.");
                model.addAttribute("error", "Uploaded file is empty.");
                return "closet";
            }

            if (image.getSize() > 10 * 1024 * 1024) { // 10MB limit
                logger.error("File size exceeds the maximum allowed size of 10MB.");
                model.addAttribute("error", "File size exceeds the maximum allowed size of 10MB.");
                return "closet";
            }

            // Specify the static folder at `src/main/resources/static/uploads`
            Path uploadDir = Paths.get("src/main/resources/static/uploads");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
                logger.info("Uploads directory created at: {}", uploadDir.toString());
            }

            // Save the file to the folder
            Path imagePath = uploadDir.resolve(image.getOriginalFilename());
            Files.write(imagePath, image.getBytes());
            logger.info("File saved successfully at: {}", imagePath.toString());

            // Save only the file name
            item.setImagePath(image.getOriginalFilename());
            clothingItemService.saveItem(item);
            logger.info("Clothing item saved successfully.");

        } catch (IOException e) {
            logger.error("Error while processing the file upload: ", e);
            model.addAttribute("error", "Error while processing the file upload.");
            return "closet";
        } catch (Exception e) {
            logger.error("Unexpected error occurred: ", e);
            model.addAttribute("error", "Unexpected error occurred.");
            return "closet";
        }

        return "redirect:/closet";
    }

    @PostMapping("/delete/{id}")
    public String deleteClothingItem(@PathVariable Long id) {
        clothingItemService.deleteItem(id); // Delete the item based on its ID
        return "redirect:/closet"; // Redirect to the main page
    }
}
