package co.tide.announcementapi.infrastructure.memory;

import co.tide.announcementapi.domain.entity.Rating;
import co.tide.announcementapi.domain.persistence.AnnouncementRatingFetcher;
import co.tide.announcementapi.domain.persistence.RatingActionPersister;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryAnnouncementRatingStore implements RatingActionPersister, AnnouncementRatingFetcher {

    private final Map<Integer, Map<Rating, Integer>> RATING_STORE = new HashMap<>();

    public Optional<Map<Rating, Integer>> fetch(
        int announcementId
    ) {
        return Optional.ofNullable(
            RATING_STORE.get(announcementId)
        );
    }

    public void persistOnLikeAction(
        int announcementId
    ) {
        computeByRating(announcementId, Rating.LIKE);
    }

    public void persistOnDislikeAction(
        int announcementId
    ) {
        computeByRating(announcementId, Rating.DISLIKE);
    }

    private void computeByRating(
        int announcementId,
        Rating rating
    ) {
        Map<Rating, Integer> ratings = RATING_STORE.getOrDefault(announcementId, getDefaultValues());
        ratings.put(rating, ratings.get(rating) + 1);

        RATING_STORE.put(announcementId, ratings);
    }

    private Map<Rating, Integer> getDefaultValues() {

        Map<Rating, Integer> defaultValues = new ConcurrentHashMap<>();
        defaultValues.put(Rating.LIKE, 0);
        defaultValues.put(Rating.DISLIKE, 0);

        return defaultValues;
    }
}
