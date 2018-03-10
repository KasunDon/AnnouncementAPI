package integration.helper;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class ClassPathXmlApplicationContextFactory {

    private static final String COMMON_DI_CONFIGURATION = "/di.common.xml";
    private static final String TEST_DI_CONFIGURATION = "/di.test.xml";

    private static ClassPathXmlApplicationContext applicationContext = null;

    private ClassPathXmlApplicationContextFactory() {
    }

    public static synchronized ClassPathXmlApplicationContext getOrCreate() {

        if (applicationContext == null) {
            create();
        }

        return applicationContext;
    }

    public static synchronized ClassPathXmlApplicationContext create() {

        applicationContext = new ClassPathXmlApplicationContext(
            COMMON_DI_CONFIGURATION,
            TEST_DI_CONFIGURATION
        );

        return applicationContext;
    }
}
