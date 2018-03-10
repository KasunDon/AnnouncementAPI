package co.tide.announcementapi.domain.entity;

import org.apache.commons.lang.math.NumberUtils;

import java.util.Map;
import java.util.Optional;

public class AnnouncementFromParameterFactory {

    public Announcement create(
        Map<String, String> parameters
    ) {

        String rawAnnouncementId = parameters.get("announcementId");
        String rawRatingValue = parameters.get("rating");

        if (rawAnnouncementId == null || rawAnnouncementId.isEmpty()) {
            throw new IllegalArgumentException("announcementId parameter value cannot be null or empty.");
        }

        if (!NumberUtils.isNumber(rawAnnouncementId)) {
            throw new IllegalArgumentException("announcementId parameter must be numeric.");
        }

        if (rawRatingValue != null && rawRatingValue.isEmpty()) {
            throw new IllegalArgumentException("ratting parameter value cannot be null or empty.");
        }

        Optional<Rating> rating = rawRatingValue != null ? Optional.of(Rating.of(rawRatingValue.toUpperCase())) : Optional.empty();
        int announcementId = Integer.parseInt(rawAnnouncementId);

        return new Announcement(announcementId, rating);
    }
}
