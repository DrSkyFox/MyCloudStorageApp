package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import server.entites.Users;
import server.services.EntityFactoryPSQL;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;

import java.util.List;


public class Test {


    private static EntityManager entityManager;

    public static void main(String[] args) {
//        if(sessionFactory == null) {
//            try {
//                Configuration configuration = new Configuration().configure();
//                configuration.addAnnotatedClass(Users.class);
//                StandardServiceRegistryBuilder builder  = new StandardServiceRegistryBuilder().
//                        applySettings(configuration.getProperties());
//                sessionFactory = configuration.buildSessionFactory(builder.build());
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }


//        EntityManager entityManager =  EntityFactoryPSQL.getEntityManager();
        List<Users> users = entityManager.createQuery("from Users ").getResultList();
        System.out.println(users);

    }

}
