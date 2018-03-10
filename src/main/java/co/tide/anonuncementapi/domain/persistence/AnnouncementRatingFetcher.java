package co.tide.announcementapi.domain.persistence;

import co.tide.announcementapi.domain.entity.Rating;

import java.util.Map;
import java.util.Optional;

public interface AnnouncementRatingFetcher {

    Optional<Map<Rating, Integer>> fetch(int announcementId);
}
