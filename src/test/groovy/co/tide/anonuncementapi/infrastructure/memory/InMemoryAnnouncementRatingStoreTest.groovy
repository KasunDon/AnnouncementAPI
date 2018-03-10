package co.tide.announcementapi.infrastructure.memory

import co.tide.announcementapi.domain.entity.Rating
import co.tide.announcementapi.domain.persistence.AnnouncementRatingFetcher
import spock.lang.Shared
import spock.lang.Specification

class InMemoryAnnouncementRatingStoreTest extends Specification {

    @Shared
    def inMemoryAnnouncementRatingStore = new InMemoryAnnouncementRatingStore()

    def "InMemoryAnnouncementRatingStore should be instance of AnnouncementRatingFetcher"() {
        expect:
        inMemoryAnnouncementRatingStore instanceof AnnouncementRatingFetcher
    }

    def "it should allow fetch rating using announcement id"() {
        setup:
        def announcementId = 1
        def rating = [(Rating.LIKE): 1] as Map

        inMemoryAnnouncementRatingStore.@RATING_STORE.put(1, rating)

        when:
        def optionalRating = inMemoryAnnouncementRatingStore.fetch(announcementId)

        then:
        optionalRating instanceof Optional
        optionalRating.get() == rating
    }

    def "it should return optional empty when announcement id doesn't exists"() {
        setup:
        def announcementId = 0

        when:
        def optionalRating = inMemoryAnnouncementRatingStore.fetch(announcementId)

        then:
        optionalRating instanceof Optional
        optionalRating.isPresent() == false
    }


    def "it should record/count likes for given announcement id"() {
        setup:
        def announcementId = 2

        when:
        inMemoryAnnouncementRatingStore.persistOnLikeAction(announcementId)

        def count = inMemoryAnnouncementRatingStore.fetch(announcementId);

        then:
        count.get().get(Rating.LIKE) == 1
        count.get().get(Rating.DISLIKE) == 0

        //--------------------------------------------------------------

        when:
        inMemoryAnnouncementRatingStore.persistOnLikeAction(announcementId)

        def count2 = inMemoryAnnouncementRatingStore.fetch(announcementId);

        then:
        count2.get().get(Rating.LIKE) == 2
        count.get().get(Rating.DISLIKE) == 0
    }

    def "it should record/count dislikes for given announcement id"() {
        setup:
        def announcementId = 3

        when:
        inMemoryAnnouncementRatingStore.persistOnDislikeAction(announcementId)

        def count = inMemoryAnnouncementRatingStore.fetch(announcementId)

        then:
        count.get().get(Rating.LIKE) == 0
        count.get().get(Rating.DISLIKE) == 1

        //--------------------------------------------------------------

        when:
        inMemoryAnnouncementRatingStore.persistOnDislikeAction(announcementId)

        def count2 = inMemoryAnnouncementRatingStore.fetch(announcementId);

        then:
        count2.get().get(Rating.LIKE) == 0
        count.get().get(Rating.DISLIKE) == 2
    }

    def cleanupSpec() {
        inMemoryAnnouncementRatingStore.@RATING_STORE.clear()
    }
}
