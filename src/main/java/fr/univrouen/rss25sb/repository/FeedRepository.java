package fr.univrouen.rss25sb.repository;

import fr.univrouen.rss25sb.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Feed entity
 */
@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
    
    /**
     * Find feeds by title
     * @param title the title to search for
     * @return list of matching feeds
     */
    List<Feed> findByTitle(String title);
    
    /**
     * Find feeds by publication date
     * @param pubDate the publication date to search for
     * @return list of matching feeds
     */
    List<Feed> findByPubDate(String pubDate);
    
    /**
     * Find feeds by title containing the given text
     * @param text the text to search for in titles
     * @return list of matching feeds
     */
    List<Feed> findByTitleContaining(String text);
}