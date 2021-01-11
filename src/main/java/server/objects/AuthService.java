package server.objects;


import core.MessagePack;
import core.comminter.MessageInterface;
import core.exceptions.LoginSmallException;
import core.exceptions.SomeThingWrongException;
import core.exceptions.UserAlreadyExistsException;
import io.netty.channel.ChannelHandlerContext;
import server.dao.UserDAO;
import server.entites.Users;
import server.interfaces.LoggerHandlerService;
import server.services.EntityFactoryPSQL;

import java.util.Arrays;

public class AuthService {

    private ChannelHandlerContext context;
    private LoggerHandlerService logAuth;
    private FileHandler fileHandler;

    public AuthService(ChannelHandlerContext context) {
        this.context = context;
    }

    public AuthService(ChannelHandlerContext context, LoggerHandlerService logAuth) {
        this.context = context;
        this.logAuth = logAuth;
    }

    public void reg(ClientDataHand clientDataHand) {
        logAuth.getLoggerAuth().info("Authorizations start: " + context.channel().remoteAddress().toString());
        UserDAO userDAO = new UserDAO(EntityFactoryPSQL.getEntityManager());
        AuthData authData = getData(clientDataHand);
        try {
            logAuth.getLoggerAuth().info("Start Create Account: " + authData.login);
            userDAO.create(new Users(authData.login, authData.pass));
            logAuth.getLoggerAuth().info("Account created success");
        } catch (UserAlreadyExistsException e) {
            logAuth.getLoggerAuth().warning(e.getMessage());
        } catch (LoginSmallException e) {
            logAuth.getLoggerAuth().warning(e.getMessage());
        } catch (SomeThingWrongException e) {
            logAuth.getLoggerAuth().warning(e.getMessage());
            logAuth.getLoggerAuth().warning(Arrays.toString(e.getStackTrace()));
        }
    }

    private void sendErrorReg(ClientDataHand clientDataHand) {
        logAuth.getLoggerAuth().warning("Error Register " + context.channel().remoteAddress().toString());

    }

    private AuthData getData(ClientDataHand clientDataHand) {
        MessageInterface messageInterface = new MessagePack(clientDataHand.getByteBuf());
        String[] data = new String(messageInterface.getCommandData()).split(" ");
        return new AuthData(data[0], data[1]);
    }

    private class AuthData {
        public String login;
        public String pass;

        public AuthData(String login, String pass) {
            this.login = login;
            this.pass = pass;
        }
    }





}
