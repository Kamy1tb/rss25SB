package fr.univrouen.rss25sb.service;

import fr.univrouen.rss25sb.model.Feed;
import fr.univrouen.rss25sb.repository.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for Feed entity operations
 */
@Service
public class FeedService {

    private final FeedRepository feedRepository;

    @Autowired
    public FeedService(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    /**
     * Save a feed
     * @param feed the feed to save
     * @return the saved feed
     */
    @Transactional
    public Feed save(Feed feed) {
        return feedRepository.save(feed);
    }

    /**
     * Find a feed by ID
     * @param id the ID to search for
     * @return the feed if found
     */
    public Optional<Feed> findById(Long id) {
        return feedRepository.findById(id);
    }

    /**
     * Find feeds by title
     * @param title the title to search for
     * @return list of matching feeds
     */
    public List<Feed> findByTitle(String title) {
        return feedRepository.findByTitle(title);
    }

    /**
     * Find feeds by publication date
     * @param pubDate the publication date to search for
     * @return list of matching feeds
     */
    public List<Feed> findByPubDate(String pubDate) {
        return feedRepository.findByPubDate(pubDate);
    }

    /**
     * Find feeds by title containing the given text
     * @param text the text to search for in titles
     * @return list of matching feeds
     */
    public List<Feed> findByTitleContaining(String text) {
        return feedRepository.findByTitleContaining(text);
    }

    /**
     * Get all feeds
     * @return list of all feeds
     */
    public List<Feed> findAll() {
        return feedRepository.findAll();
    }

    /**
     * Delete a feed by ID
     * @param id the ID of the feed to delete
     */
    @Transactional
    public void deleteById(Long id) {
        feedRepository.deleteById(id);
    }

    /**
     * Check if a feed with the same title and publication date already exists
     * @param title the title to check
     * @param pubDate the publication date to check
     * @return true if a feed with the same title and publication date exists
     */
    public boolean existsByTitleAndPubDate(String title, String pubDate) {
        List<Feed> feeds = feedRepository.findByTitle(title);
        return feeds.stream()
                .filter(feed -> feed.getItems() != null && !feed.getItems().isEmpty()) // Only consider feeds with items
                .anyMatch(feed -> feed.getPubDate().equals(pubDate));
    }
}
