package co.tide.announcementapi.domain.entity;

import java.util.Optional;

public class Announcement {

    private final int id;
    private final Optional<Rating> rating;

    public Announcement(
        int id,
        Optional<Rating> rating
    ) {

        if (rating == null) {
            throw new IllegalArgumentException("Rating cannot be null");
        }

        this.id = id;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public Optional<Rating> getRating() {
        return rating;
    }
}
