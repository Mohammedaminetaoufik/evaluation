package ma.projet.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();

            // Charger application.properties
            Properties props = new Properties();
            try (InputStream input = HibernateUtil.class.getClassLoader()
                    .getResourceAsStream("application.properties")) {
                if (input == null) {
                    System.err.println("application.properties non trouvé !");
                    throw new RuntimeException("application.properties non trouvé !");
                }
                props.load(input);
            }

            // Mapper les propriétés
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", props.getProperty("spring.datasource.url"));
            configuration.setProperty("hibernate.connection.username", props.getProperty("spring.datasource.username"));
            configuration.setProperty("hibernate.connection.password", props.getProperty("spring.datasource.password"));

            configuration.setProperty("hibernate.dialect", props.getProperty("spring.jpa.properties.hibernate.dialect"));
            configuration.setProperty("hibernate.hbm2ddl.auto", props.getProperty("spring.jpa.hibernate.ddl-auto"));
            configuration.setProperty("hibernate.show_sql", props.getProperty("spring.jpa.show-sql"));

            // Ajouter les entités
            configuration.addAnnotatedClass(ma.projet.classes.Employe.class);
            configuration.addAnnotatedClass(ma.projet.classes.Projet.class);
            configuration.addAnnotatedClass(ma.projet.classes.Tache.class);
            configuration.addAnnotatedClass(ma.projet.classes.EmployeTache.class);

            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            return configuration.buildSessionFactory(serviceRegistry);

        } catch (Exception ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}