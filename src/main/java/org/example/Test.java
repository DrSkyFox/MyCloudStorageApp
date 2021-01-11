package org.example;

import core.exceptions.LoginSmallException;
import core.exceptions.SomeThingWrongException;
import core.exceptions.UserAlreadyExistsException;
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

        UserDAO userDAO1 = new UserDAO(EntityFactoryPSQL.getEntityManager());


//        try {
//            userDAO1.create(new Users("test", "test"));
//        } catch (UserAlreadyExistsException e) {
//            e.printStackTrace();
//        } catch (LoginSmallException e) {
//            e.printStackTrace();
//        } catch (SomeThingWrongException e) {
//            e.printStackTrace();
//        }
            Users user = userDAO.getFindByParam("test", "test");
        System.out.println(user.toString());


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
