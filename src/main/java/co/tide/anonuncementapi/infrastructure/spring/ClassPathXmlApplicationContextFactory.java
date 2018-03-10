package co.tide.announcementapi.infrastructure.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class ClassPathXmlApplicationContextFactory {

    private static final String COMMON_DI_CONFIGURATION = "/di.common.xml";
    private static final String DEVELOPMENT_DI_CONFIGURATION = "/di.development.xml";
    private static final String STAGING_DI_CONFIGURATION = "/di.staging.xml";
    private static final String PRODUCTION_DI_CONFIGURATION = "/di.production.xml";

    private static ClassPathXmlApplicationContext applicationContext = null;

    private ClassPathXmlApplicationContextFactory() {
    }

    public static synchronized ClassPathXmlApplicationContext getOrCreate() {

        if (applicationContext == null) {
            applicationContext = create();
        }

        return applicationContext;
    }

    private static ClassPathXmlApplicationContext create() {

        return new ClassPathXmlApplicationContext(
            COMMON_DI_CONFIGURATION,
            DEVELOPMENT_DI_CONFIGURATION,
            STAGING_DI_CONFIGURATION,
            PRODUCTION_DI_CONFIGURATION
        );
    }
}
