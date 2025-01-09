package gr.aueb.cf.mutu.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static String environment = "development"; // default

    public static void setTestEnvironment() {
        environment = "test";
        sessionFactory = null;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                if ("test".equals(environment)) {
                    configuration.configure("hibernate-test.cfg.xml");
                } else {
                    configuration.configure("hibernate.cfg.xml");
                }

                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize Hibernate: " + e.getMessage(), e);
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

}