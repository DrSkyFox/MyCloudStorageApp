package server.objects;


import core.MessagePack;
import core.comminter.MessageInterface;
import core.exceptions.*;
import io.netty.channel.ChannelHandlerContext;
import server.dao.UserDAO;
import server.entites.Users;
import server.interfaces.AuthHandlerService;
import server.interfaces.ClientHandlerService;
import server.interfaces.LoggerHandlerService;
import server.services.EntityFactoryPSQL;

import java.util.Arrays;
import java.util.Objects;

public class AuthHandler implements AuthHandlerService {

    private ChannelHandlerContext context;
    private LoggerHandlerService logAuth;
    private FileHandler fileHandler;

    public AuthHandler(ChannelHandlerContext context) {
        this.context = context;
    }

    public AuthHandler(ChannelHandlerContext context, LoggerHandlerService logAuth) {
        this.context = context;
        this.logAuth = logAuth;
    }



    public void reg(ClientHandlerService client) {
        logWrite("Authorizations start: " + context.channel().remoteAddress().toString());
        UserDAO userDAO = new UserDAO(EntityFactoryPSQL.getEntityManager());
        AuthData authData = getData(client);
        try {
            logWrite("Start Create Account: " + authData.login);
            userDAO.create(new Users(authData.login, authData.pass));
            logWrite("Account created success");

        } catch (UserAlreadyExistsException e) {
            logWrite(e.getMessage(), true);
        } catch (LoginSmallException e) {
            logWrite(e.getMessage(), true);
        } catch (SomeThingWrongException e) {
            logWrite(e.getMessage(),true);
            logWrite(Arrays.toString(e.getStackTrace()),true);
        }
    }
    @Override
    public void auth(ClientHandlerService client) {
        logAuth.getLoggerAuth().info("Authorizations start: " + context.channel().remoteAddress().toString());
        UserDAO userDAO = new UserDAO(EntityFactoryPSQL.getEntityManager());
        AuthData authData = getData(client);
        try {
            if(userDAO.getFindByParam(authData.login, authData.pass) != null) {

            }
        } catch (NullLoginOrPassException e) {
            logAuth.getLoggerAuth().warning("Error Auth: " + e.getMessage());
            sendErrorReg(client);
        } catch (UserNotFoundException e) {
            logAuth.getLoggerAuth().warning("User not found: " + String.format("User - %s, pass - %s", authData.login, authData.pass));
            sendErrorReg(client);
        }
    }
    @Override
    public void sendErrorReg(ClientHandlerService client) {
        logAuth.getLoggerAuth().warning("Error Register " + context.channel().remoteAddress().toString());

    }

    private AuthData getData(ClientHandlerService client) {
        logAuth.getLoggerAuth().info("getData:");
        MessageInterface messageInterface = new MessagePack(client.getByteBuf());
        String[] data = new String(messageInterface.getCommandData()).split(" ");
        logAuth.getLoggerAuth().info(Arrays.toString(data));
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

    private void logWrite(String message) {
        logWrite(message, false);
    }

    private void logWrite(String message, boolean warning) {
        if(warning) {
            Objects.requireNonNull(logAuth).getLoggerAuth().warning(message);
        } else Objects.requireNonNull(logAuth).getLoggerAuth().info(message);

    }




}
