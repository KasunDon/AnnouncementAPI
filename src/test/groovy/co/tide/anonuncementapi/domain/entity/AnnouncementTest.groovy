package co.tide.announcementapi.domain.entity

import spock.lang.Specification

class AnnouncementTest extends Specification {

    def "it should hold constructed values"() {
        when:
        def announcement = new Announcement(1, Optional.of(Rating.LIKE))

        then:
        announcement.id == 1
        announcement.rating == Optional.of(Rating.LIKE)

        //------------------------------------------------------

        when:
        def announcement2 = new Announcement(2, Optional.empty())

        then:
        announcement2.id == 2
        announcement2.rating == Optional.empty()
    }

    def "it should throw an exception when null passes as Rating"() {

        when:
        new Announcement(0, null)

        then:
        thrown(IllegalArgumentException)
    }
}
