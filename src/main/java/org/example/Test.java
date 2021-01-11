package org.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import server.dao.UserDAO;
import server.entites.Users;
import server.interfaces.LoggerHandlerService;
import server.objects.LoggerHandler;
import server.services.EntityFactoryPSQL;

import javax.persistence.EntityManager;
import java.nio.charset.Charset;


public class Test {


    private static EntityManager entityManager;

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO(EntityFactoryPSQL.getEntityManager());
        userDAO.create(new Users("test1", "test1"));
//        String str= "user user";
//
//        ByteBuf byteBuf = Unpooled.buffer();
//        byteBuf.writeBytes(str.getBytes());
//        byte[] bytes = str.getBytes();
//
//
//        System.out.println(new String(bytes, Charset.defaultCharset()));

//        LoggerHandlerService loggerHandlerService = new LoggerHandler();
//        loggerHandlerService.getLoggerServ().info("test");
////        System.out.println(Files.isDirectory(Path.of("d:\\games")));


    }

}
