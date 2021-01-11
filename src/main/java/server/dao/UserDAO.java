package server.dao;

import core.exceptions.LoginSmallException;
import core.exceptions.SomeThingWrongException;
import core.exceptions.UserAlreadyExistsException;
import org.hibernate.tool.hbm2ddl.SchemaValidator;
import server.entites.Users;
import server.interfaces.LoggerHandlerService;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class UserDAO  {


    private EntityManager entityManager;
    private LoggerHandlerService lhs;

    public UserDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UserDAO(EntityManager entityManager, LoggerHandlerService loggerHandlerService) {
        this.entityManager = entityManager;
        this.lhs = loggerHandlerService;
    }

    public void setLoggerHandlerService(LoggerHandlerService loggerHandlerService) {
        this.lhs = loggerHandlerService;
    }

    public Users getById(int i) {
        return entityManager.find(Users.class, i);
    }


    public Users getFindByParam(String login, String pass) {
        String str = String.format("from users where Users.user = %s and Users .pass = %s", login, pass);
        return (Users) entityManager.createQuery(str).getSingleResult();
    }



    public List<Users> getAll() {
        return entityManager.createQuery("from Users").getResultList();
    }


    public void create(Users users) throws UserAlreadyExistsException, LoginSmallException, SomeThingWrongException {
        lhs.getLoggerAuth().info("Create Account:");
        if(users.getUser().length() <= 3) {
            throw new LoginSmallException("Login must value > 3");
        }
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(users);
            entityManager.getTransaction().commit();
            lhs.getLoggerAuth().info("Account created successful");
        } catch (RollbackException e) {
            throw new UserAlreadyExistsException("User already exists", e);
        } catch (Exception e) {
            lhs.getLoggerAuth().warning(e.getMessage());
            lhs.getLoggerAuth().warning(Arrays.toString(e.getStackTrace()));
            throw new SomeThingWrongException("Something was wrong", e);
        }

    }


    public void remove(Users users) {
        entityManager.remove(users);
    }


    public void edit(Users users) {
        entityManager.merge(users);
    }
}
