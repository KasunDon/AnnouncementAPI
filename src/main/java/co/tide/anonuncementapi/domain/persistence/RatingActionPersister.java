package co.tide.announcementapi.domain.persistence;

public interface RatingActionPersister {

    void persistOnLikeAction(int announcementId);

    void persistOnDislikeAction(int announcementId);
}
