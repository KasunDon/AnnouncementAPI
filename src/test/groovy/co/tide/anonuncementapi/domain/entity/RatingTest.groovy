package co.tide.announcementapi.domain.entity

import spock.lang.Specification

class RatingTest extends Specification {

    def "it should return like enum"() {
        when:
        def rating = Rating.of("LIKE")

        then:
        rating == Rating.LIKE

        //-----------------------------------

        when:
        rating = Rating.of("LIKE")

        then:
        rating == Rating.LIKE

    }

    def "it should return dislike enum"() {
        when:
        def rating = Rating.of("DISLIKE")

        then:
        rating == Rating.DISLIKE

        //-----------------------------------

        when:
        rating = Rating.of("DISLIKE")

        then:
        rating == Rating.DISLIKE

    }

    def "it should throw an exception when unknown value passed"() {

        when:
        Rating.of("abc")

        then:
        thrown(IllegalArgumentException)
    }
}
