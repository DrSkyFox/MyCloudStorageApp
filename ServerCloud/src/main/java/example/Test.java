package org.example;

import server.dao.UserDAO;
import server.entites.Users;
import server.objects.EntityFactoryPSQL;

import javax.persistence.EntityManager;


public class Test {


    private static EntityManager entityManager;

    public static void main(String[] args) {
//        UserDAO userDAO = new UserDAO(EntityFactoryPSQL.getEntityManager());
//
//        UserDAO userDAO1 = new UserDAO(EntityFactoryPSQL.getEntityManager());
//
//
////        try {
////            userDAO1.create(new Users("test", "test"));
////        } catch (UserAlreadyExistsException e) {
////            e.printStackTrace();
////        } catch (LoginSmallException e) {
////            e.printStackTrace();
////        } catch (SomeThingWrongException e) {
////            e.printStackTrace();
////        }
//            Users user = userDAO.getFindByParam("test", "test");
//        System.out.println(user.toString());
//

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
