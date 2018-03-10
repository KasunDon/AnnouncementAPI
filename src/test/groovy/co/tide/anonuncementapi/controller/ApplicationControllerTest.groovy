package co.tide.announcementapi.controller

import co.tide.announcementapi.domain.analyzer.AnnouncementAnalyzer
import co.tide.announcementapi.domain.entity.Announcement
import co.tide.announcementapi.domain.entity.AnnouncementFromParameterFactory
import co.tide.announcementapi.domain.entity.Rating
import co.tide.announcementapi.domain.persistence.AnnouncementRatingFetcher
import spock.lang.Specification

class ApplicationControllerTest extends Specification {

    def announcementAnalyzer = Mock(AnnouncementAnalyzer)
    def announcementFromRequestParameterFactory = Mock(AnnouncementFromParameterFactory)
    def announcementRatingFetcher = Mock(AnnouncementRatingFetcher)

    def applicationController = new ApplicationController(
        announcementAnalyzer,
        announcementFromRequestParameterFactory,
        announcementRatingFetcher
    )

    def "it should be able to handle rating action where counts like/dislike"() {
        setup:
        def parameters = ["announcementId": "1", "rating": "like"] as Map

        when:
        def output = applicationController.performRateAction(parameters)

        then:
        1 * announcementFromRequestParameterFactory.create(parameters)
        1 * announcementAnalyzer.analyze(_)

        output.containsKey("success") == true
        output.get("success") == true
    }


    def "it should be able to fetch rating counters per announcement id"() {
        setup:
        def parameters = ["announcementId": "1", "rating": "like"] as Map
        def fetchedCounterMap = [(Rating.LIKE): 10, (Rating.DISLIKE): 1000] as Map

        when:
        def output = applicationController.fetchRateAction(parameters)

        then:
        1 * announcementFromRequestParameterFactory.create(parameters) >> Mock(Announcement) {
            getId() >> 1
        }
        1 * announcementRatingFetcher.fetch(_) >> Optional.of(fetchedCounterMap)

        output.get("LIKE") == "10"
        output.get("DISLIKE") == "1000"
    }
}
