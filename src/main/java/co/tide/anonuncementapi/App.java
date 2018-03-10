package co.tide.announcementapi;

import co.tide.announcementapi.infrastructure.spring.ClassPathXmlApplicationContextFactory;
import co.tide.announcementapi.library.RoutingManager;
import org.springframework.context.ApplicationContext;

public class App {

    public static void main(String[] args) {
        ApplicationContext applicationContext = ClassPathXmlApplicationContextFactory.getOrCreate();
        RoutingManager routingManager = applicationContext.getBean(RoutingManager.class);

        routingManager.route();
    }
}
