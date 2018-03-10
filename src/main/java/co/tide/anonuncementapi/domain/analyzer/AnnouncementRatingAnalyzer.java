package co.tide.announcementapi.domain.analyzer;

import co.tide.announcementapi.domain.entity.Announcement;
import co.tide.announcementapi.domain.entity.Rating;
import co.tide.announcementapi.domain.persistence.RatingActionPersister;

import java.util.Optional;

public class AnnouncementRatingAnalyzer implements AnnouncementAnalyzer {

    private final RatingActionPersister ratingActionPersister;

    public AnnouncementRatingAnalyzer(
        RatingActionPersister ratingActionPersister
    ) {
        if (ratingActionPersister == null) {
            throw new IllegalArgumentException("RatingActionPersister cannot be null.");
        }

        this.ratingActionPersister = ratingActionPersister;
    }

    public void analyze(
        Announcement announcement
    ) {

        Optional<Rating> optionalRating = announcement.getRating();

        if (!optionalRating.isPresent()) {
            return;
        }

        switch (optionalRating.get()) {
            case LIKE:
                ratingActionPersister.persistOnLikeAction(announcement.getId());
                return;

            case DISLIKE:
                ratingActionPersister.persistOnDislikeAction(announcement.getId());
                return;
        }
    }
}
