package org.example;

import server.dao.UserDAO;
import server.entites.Users;
import server.objects.EntityFactoryPSQL;

import javax.persistence.EntityManager;


public class Test {


    private static EntityManager entityManager;

    public static void main(String[] args) {
        String[] strings = new String[] {"test", "test2"};
        System.out.println(strings.length);


    }

}
