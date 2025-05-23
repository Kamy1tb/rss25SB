package fr.univrouen.rss25sb.service;

import fr.univrouen.rss25sb.model.Item;
import fr.univrouen.rss25sb.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for Item entity operations
 */
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * Save an item
     * @param item the item to save
     * @return the saved item
     */
    @Transactional
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    /**
     * Find an item by ID
     * @param id the ID to search for
     * @return the item if found
     */
    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    /**
     * Find an item by GUID
     * @param guid the GUID to search for
     * @return the item if found
     */
    public Optional<Item> findByGuid(String guid) {
        return itemRepository.findByGuid(guid);
    }

    /**
     * Find items by title
     * @param title the title to search for
     * @return list of matching items
     */
    public List<Item> findByTitle(String title) {
        return itemRepository.findByTitle(title);
    }

    /**
     * Find items by publication date
     * @param published the publication date to search for
     * @return list of matching items
     */
    public List<Item> findByPublished(String published) {
        return itemRepository.findByPublished(published);
    }

    /**
     * Find items by title containing the given text
     * @param text the text to search for in titles
     * @return list of matching items
     */
    public List<Item> findByTitleContaining(String text) {
        return itemRepository.findByTitleContaining(text);
    }

    /**
     * Find items by publication date greater than or equal to the given date
     * @param date the date to compare with
     * @return list of matching items
     */
    public List<Item> findByPublishedGreaterThanEqual(String date) {
        return itemRepository.findByPublishedGreaterThanEqual(date);
    }

    /**
     * Find items by feed ID
     * @param feedId the feed ID to search for
     * @return list of matching items
     */
    public List<Item> findByFeedId(Long feedId) {
        return itemRepository.findByFeedId(feedId);
    }

    /**
     * Get all items
     * @return list of all items
     */
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    /**
     * Delete an item by ID
     * @param id the ID of the item to delete
     */
    @Transactional
    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    /**
     * Check if an item with the same GUID already exists
     * @param guid the GUID to check
     * @return true if an item with the same GUID exists
     */
    public boolean existsByGuid(String guid) {
        return itemRepository.findByGuid(guid).isPresent();
    }
}