package integration

import co.tide.announcementapi.library.RoutingManager
import groovyx.net.http.HTTPBuilder
import integration.helper.ClassPathXmlApplicationContextFactory
import spark.Spark
import spock.lang.Specification

import static groovyx.net.http.ContentType.URLENC
import static groovyx.net.http.Method.GET
import static groovyx.net.http.Method.POST

class ApiEndpointsIntegrationTest extends Specification {

    def setup() {

        System.setProperty("spring.profiles.active", "test")

        def applicationContext = ClassPathXmlApplicationContextFactory.create()

        applicationContext
            .getBean(RoutingManager.class)
            .route()
    }

    def "Testing Announcement Api endpoints"() {

        when:
        def http = new HTTPBuilder("http://localhost:4576/")
        http.request(GET) {
            uri.path = '/announcements/v1/1001/rate'
            response.success = { resp, content ->
                assert resp.statusLine.statusCode == 200
                assert content.message == "No like/dislike counters found for announcementId - 1001"
            }
        }

        Thread thread1 = new Thread({
            def httpLocal = new HTTPBuilder("http://localhost:4576/")

            for (int i = 0; i < 10000; i++) {
                httpLocal.request(POST) {
                    uri.path = '/announcements/v1/1001/rate'
                    requestContentType = URLENC
                    body = [rating: 'like']

                    response.success = { resp, content ->
                        assert resp.statusLine.statusCode == 200
                        assert content.success == true
                    }
                }
            }
        })

        Thread thread2 = new Thread({
            def httpLocal = new HTTPBuilder("http://localhost:4576/")

            for (int i = 0; i < 14000; i++) {
                httpLocal.request(POST) {
                    uri.path = '/announcements/v1/1001/rate'
                    requestContentType = URLENC
                    body = [rating: 'like']

                    response.success = { resp, content ->
                        assert resp.statusLine.statusCode == 200
                        assert content.success == true
                    }
                }
            }
        })

        Thread thread3 = new Thread({
            def httpLocal = new HTTPBuilder("http://localhost:4576/")

            for (int i = 0; i < 14000; i++) {
                httpLocal.request(POST) {
                    uri.path = '/announcements/v1/1001/rate'
                    requestContentType = URLENC
                    body = [rating: 'like']

                    response.success = { resp, content ->
                        assert resp.statusLine.statusCode == 200
                        assert content.success == true
                    }
                }
            }
        })

        Thread thread4 = new Thread({
            def httpLocal = new HTTPBuilder("http://localhost:4576/")

            for (int i = 0; i < 14000; i++) {
                httpLocal.request(POST) {
                    uri.path = '/announcements/v1/1002/rate'
                    requestContentType = URLENC
                    body = [rating: 'dislike']

                    response.success = { resp, content ->
                        assert resp.statusLine.statusCode == 200
                        assert content.success == true
                    }
                }
            }
        })


        [thread1, thread2, thread3, thread4]*.start()
        [thread1, thread2, thread3, thread4]*.join()

        /**
         * Evaluating final results
         */

        Thread resultThread1 = new Thread({
            def httpLocal = new HTTPBuilder("http://localhost:4576/")
            httpLocal.request(GET) {
                uri.path = '/announcements/v1/1001/rate'
                response.success = { resp, content ->
                    assert resp.statusLine.statusCode == 200
                    assert content.DISLIKE == "0"
                }
            }
        })

        Thread resultThread2 = new Thread({
            def httpLocal = new HTTPBuilder("http://localhost:4576/")
            httpLocal.request(GET) {
                uri.path = '/announcements/v1/1002/rate'
                response.success = { resp, content ->
                    assert resp.statusLine.statusCode == 200
                    assert content.LIKE == "0"
                }
            }
        })


        [resultThread1, resultThread2]*.start()
        [resultThread1, resultThread2]*.join()

        then:

        1 == 1
    }

    def cleanup() {
        Spark.stop()
    }

}
