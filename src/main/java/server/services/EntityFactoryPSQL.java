package server.services;

import org.hibernate.annotations.common.util.impl.Log;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.logging.Logger;

public class EntityFactoryPSQL {
    private static Logger logger = Logger.getLogger(EntityFactoryPSQL.class.getName());
    private static EntityManagerFactory entityManagerFactory;

    public EntityFactoryPSQL() {
    }

    public static EntityManager getEntityManager() {
        if (entityManagerFactory == null) {
            try {
                entityManagerFactory = Persistence.createEntityManagerFactory("ds");
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return entityManagerFactory.createEntityManager();
    }

    public void closeFactory() {
        entityManagerFactory.close();
    }

}
