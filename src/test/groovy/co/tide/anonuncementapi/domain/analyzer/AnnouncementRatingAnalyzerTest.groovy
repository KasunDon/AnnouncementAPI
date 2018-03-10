package co.tide.announcementapi.domain.analyzer

import co.tide.announcementapi.domain.entity.Announcement
import co.tide.announcementapi.domain.entity.Rating
import co.tide.announcementapi.domain.persistence.RatingActionPersister
import spock.lang.Specification

class AnnouncementRatingAnalyzerTest extends Specification {

    def ratingActionPersister = Mock(RatingActionPersister)
    def announcementRatingAnalyzer = new AnnouncementRatingAnalyzer(ratingActionPersister)

    def "it should be instanceof AnnouncementAnalyzer"() {
        expect:
        announcementRatingAnalyzer instanceof AnnouncementAnalyzer
    }


    def "it should persist all likes for an announcement"() {
        setup:
        def announcement1 = new Announcement(1, Optional.of(Rating.LIKE))
        def announcement2 = new Announcement(2, Optional.of(Rating.DISLIKE))

        when:
        announcementRatingAnalyzer.analyze(announcement1)

        then:
        1 * ratingActionPersister.persistOnLikeAction(1)

        //------------------------------------------------------

        when:
        announcementRatingAnalyzer.analyze(announcement2)

        then:
        0 * ratingActionPersister.persistOnLikeAction(_)

    }

    def "it should persist all dislikes for an announcement"() {
        setup:
        def announcement1 = new Announcement(1, Optional.of(Rating.DISLIKE))
        def announcement2 = new Announcement(2, Optional.of(Rating.LIKE))

        when:
        announcementRatingAnalyzer.analyze(announcement1)

        then:
        1 * ratingActionPersister.persistOnDislikeAction(1)

        //------------------------------------------------------

        when:
        announcementRatingAnalyzer.analyze(announcement2)

        then:
        0 * ratingActionPersister.persistOnDislikeAction(_)
    }

}
