package server.objects;


import core.MessagePack;
import core.common.MessageInterface;
import core.exceptions.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import server.dao.DirectoriesDAO;
import server.dao.UserDAO;
import server.entites.Users;
import server.interfaces.AuthHandlerService;
import server.interfaces.ClientHandlerService;
import server.interfaces.LoggerHandlerService;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

public class AuthHandler implements AuthHandlerService {

    private ChannelHandlerContext context;
    private LoggerHandlerService logAuth;
    private FileHandler fileHandler;


    public AuthHandler(ChannelHandlerContext context) {
        this.context = context;
    }



    public AuthHandler(ChannelHandlerContext context,  LoggerHandlerService logAuth) {
        this.context = context;
        this.logAuth = logAuth;
    }

    public void reg(ByteBuf byteBuf) {
        logWrite("Authorizations start: " + context.channel().remoteAddress().toString());
        UserDAO userDAO = new UserDAO(EntityFactoryPSQL.getEntityManager());
        AuthData authData = getData(byteBuf);
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
    public Users auth(ByteBuf byteBuf) {
        logAuth.getLoggerAuth().info("Authorizations start: " + context.channel().remoteAddress().toString());
        UserDAO userDAO = new UserDAO(EntityFactoryPSQL.getEntityManager());
        AuthData authData = getData(byteBuf);
        try {
            return userDAO.getFindByParam(authData.login, authData.pass);
        } catch (NullLoginOrPassException e) {
            String msg = String.format("Error Auth: " + e.getMessage());
            logAuth.getLoggerAuth().warning("Error Auth: " + e.getMessage());
            sendErrorReg(msg);
        } catch (UserNotFoundException e) {
            String msg = String.format(" User not found:  User - %s, pass - %s", authData.login, authData.pass);
            logAuth.getLoggerAuth().warning(msg);
            sendErrorReg(msg);
        }
        return null;
    }





    @Override
    public void sendErrorReg(String message) {
        logAuth.getLoggerAuth().warning("Error Register " + context.channel().remoteAddress().toString());
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(("Error register. " + message).getBytes());
        context.writeAndFlush(byteBuf);

    }

    private AuthData getData(ByteBuf byteBuf) {
        logAuth.getLoggerAuth().info("getData:");
        MessageInterface messageInterface = new MessagePack(byteBuf);
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

    @Override
    public Path getUserDirectory(int id) {
        return Path.of(new DirectoriesDAO(EntityFactoryPSQL.getEntityManager()).getById(id).getCatalog());
    }
}
