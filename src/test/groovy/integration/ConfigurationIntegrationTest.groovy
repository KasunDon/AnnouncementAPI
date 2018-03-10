package integration

import co.tide.announcementapi.domain.analyzer.AnnouncementRatingAnalyzer
import co.tide.announcementapi.library.RoutingManager
import integration.helper.ClassPathXmlApplicationContextFactory
import spock.lang.Specification

class ConfigurationIntegrationTest extends Specification {

    def setup() {
        System.setProperty("spring.profiles.active", "test")
    }

    def "The DI configuration loads correctly"() {
        setup:
        def applicationContext = ClassPathXmlApplicationContextFactory.create()

        when:
        applicationContext.getBean(RoutingManager.class)
        applicationContext.getBean(AnnouncementRatingAnalyzer.class)

        then:
        noExceptionThrown()
    }
}
