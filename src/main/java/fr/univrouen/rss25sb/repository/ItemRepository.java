package fr.univrouen.rss25sb.repository;

import fr.univrouen.rss25sb.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Item entity
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    
    /**
     * Find item by guid
     * @param guid the guid to search for
     * @return the item if found
     */
    Optional<Item> findByGuid(String guid);
    
    /**
     * Find items by title
     * @param title the title to search for
     * @return list of matching items
     */
    List<Item> findByTitle(String title);
    
    /**
     * Find items by publication date
     * @param published the publication date to search for
     * @return list of matching items
     */
    List<Item> findByPublished(String published);
    
    /**
     * Find items by title containing the given text
     * @param text the text to search for in titles
     * @return list of matching items
     */
    List<Item> findByTitleContaining(String text);
    
    /**
     * Find items by publication date greater than or equal to the given date
     * @param date the date to compare with
     * @return list of matching items
     */
    List<Item> findByPublishedGreaterThanEqual(String date);
    
    /**
     * Find items by feed id
     * @param feedId the feed id to search for
     * @return list of matching items
     */
    List<Item> findByFeedId(Long feedId);
}