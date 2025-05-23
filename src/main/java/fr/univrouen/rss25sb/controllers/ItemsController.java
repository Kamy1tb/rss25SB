package fr.univrouen.rss25sb.controllers;

import fr.univrouen.rss25sb.model.Item;
import fr.univrouen.rss25sb.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Controller for the items page
 */
@Controller
public class ItemsController {

    private final ItemService itemService;

    @Autowired
    public ItemsController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Display the items page with pagination and search functionality
     * @param model the model to add attributes to
     * @param page the page number (default 0)
     * @param size the page size (default 10)
     * @param search the search term (optional)
     * @return the name of the Thymeleaf template
     */
    @GetMapping("/items")
    public String items(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search) {

        List<Item> items;
        
        // If search term is provided, filter items by title
        if (search != null && !search.isEmpty()) {
            items = itemService.findByTitleContaining(search);
            model.addAttribute("search", search);
        } else {
            items = itemService.findAll();
        }
        
        // Calculate pagination
        int totalItems = items.size();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        
        // Ensure page is within bounds
        if (page < 0) {
            page = 0;
        } else if (page >= totalPages && totalPages > 0) {
            page = totalPages - 1;
        }
        
        // Get items for current page
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, totalItems);
        
        List<Item> pageItems = fromIndex < totalItems 
                ? items.subList(fromIndex, toIndex) 
                : List.of();
        
        model.addAttribute("items", pageItems);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("size", size);
        
        return "items";
    }
}