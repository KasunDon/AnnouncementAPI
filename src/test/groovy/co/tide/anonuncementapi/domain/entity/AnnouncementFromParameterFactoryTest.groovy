package co.tide.announcementapi.domain.entity

import spock.lang.Specification

class AnnouncementFromParameterFactoryTest extends Specification {

    def announcementFromRequestParameterFactory = new AnnouncementFromParameterFactory()

    def "it should create announcement from request parameters"() {

        setup:
        def parameters = ["announcementId": "1", "rating": "like"] as Map

        when:
        def announcement = announcementFromRequestParameterFactory.create(parameters)

        then:
        announcement instanceof Announcement

        announcement.id == 1
        announcement.rating == Optional.of(Rating.LIKE)
    }

    def "it should not throw an exception given rating value null"() {
        setup:
        def parameters = ["announcementId": "1"] as Map

        when:
        def announcement = announcementFromRequestParameterFactory.create(parameters)

        then:
        announcement.id == 1
        announcement.rating == Optional.empty()
    }

    def "it should throw an exception given announcementId value null or empty"() {
        setup:
        def parameters = ["rating": "like"] as Map

        when:
        announcementFromRequestParameterFactory.create(parameters)

        then:
        thrown(IllegalArgumentException)
    }

    def "it should throw an exception when announcement id isnt numeric"() {

        setup:
        def parameters = ["announcementId": "1a", "rating": "like"] as Map

        when:
        announcementFromRequestParameterFactory.create(parameters)

        then:
        thrown(IllegalArgumentException)
    }

    def "it should throw an exception when rating doesnt contains allowed values"() {

        setup:
        def parameters = ["announcementId": "1a", "rating": "like1"] as Map

        when:
        announcementFromRequestParameterFactory.create(parameters)

        then:
        thrown(IllegalArgumentException)
    }
}
