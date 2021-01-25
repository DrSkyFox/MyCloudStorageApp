package server.objects;


import core.MessagePack;
import core.exceptions.*;
import core.interfaces.MessageInterface;
import core.resources.CommandAuthorization;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import server.dao.DirectoriesDAO;
import server.dao.UserDAO;
import server.entites.Users;
import server.interfaces.ServerInterface;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;

public class AuthHandler extends ChannelInboundHandlerAdapter  {

    private Logger logger;
    private ServerInterface serverInterface;
    private State state;
    private Users user;



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)  {
        MessageInterface messagePack = (MessageInterface) msg;
        CommandAuthorization commandAuthorization = CommandAuthorization.findCommands(messagePack.getCommandByte());
        if(Objects.isNull(commandAuthorization)) {
            ctx.fireChannelRead(msg);
        }
        switch (state) {
            case NONE_AUTH:
                nonAuth(ctx, messagePack, commandAuthorization);
                break;
            case AUTH_OK:
                if(commandAuthorization.equals(CommandAuthorization.EXIT)) {
                    state = State.NONE_AUTH;
                } else {
                    ctx.fireChannelRead(msg);
                }
                break;
        }
    }

    private void nonAuth(ChannelHandlerContext ctx, MessageInterface messagePack, CommandAuthorization commandAuthorization) {
        if(commandAuthorization.equals(CommandAuthorization.REGUSER)) {
            if(reg(messagePack)) {
                ctx.writeAndFlush(new MessagePack(CommandAuthorization.REGUSER, "Registration success"));
            } else {
                ctx.writeAndFlush(new MessagePack(CommandAuthorization.REGUSER, "Registration failed"));
            }
        } else if (commandAuthorization.equals(CommandAuthorization.AUTUSER)) {
            Users user_tmp = Objects.requireNonNull(auth(messagePack));
            if(Objects.nonNull(user)) {
                user = user_tmp;
                state = State.AUTH_OK;
                ctx.channel().pipeline().addLast(new FileHandler(serverInterface, getUserDirectory(user.getId())));
            } else {
                ctx.writeAndFlush(new MessagePack(CommandAuthorization.AUTUSER, "Authorization failed"));
                state = State.NONE_AUTH;
            }
        } else if (commandAuthorization.equals(CommandAuthorization.EXIT)) {
            ctx.writeAndFlush(new MessagePack(CommandAuthorization.EXIT, "Nothing to exit"));
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    public AuthHandler(ServerInterface serverInterface) {
        this.serverInterface = serverInterface;
        logger = serverInterface.getLogger().getLoggerAuth();
        state = State.NONE_AUTH;
    }



    private boolean reg(MessageInterface messagePack) {
        logger.info("Registration start: ");
        UserDAO userDAO = new UserDAO(EntityFactoryPSQL.getEntityManager());
        AuthData authData = getData(messagePack);
        try {
            logInfo("Start Create Account: " + authData.login);
            userDAO.create(new Users(authData.login, authData.pass));
            logInfo("Account created success");
            return true;
        } catch (UserAlreadyExistsException e) {
            logWarning(e.getMessage());
        } catch (LoginSmallException e) {
            logWarning(e.getMessage());
        } catch (SomeThingWrongException e) {
            logWarning(e.getMessage());
            logWarning(Arrays.toString(e.getStackTrace()));
        }
        return false;
    }


    private Users auth(MessageInterface messagePack) {
        state = State.AUTHORIZATION;
        logInfo("Authorizations start: ");
        UserDAO userDAO = new UserDAO(EntityFactoryPSQL.getEntityManager());
        AuthData authData = getData(messagePack);
        try {
            return userDAO.getFindByParam(authData.login, authData.pass);
        } catch (NullLoginOrPassException e) {
            String msg = String.format("Error Auth: " + e.getMessage());
            logWarning("Error Auth: " + e.getMessage());
            sendErrorReg(msg);
        } catch (UserNotFoundException e) {
            String msg = String.format(" User not found:  User - %s, pass - %s", authData.login, authData.pass);
            logWarning(msg);
            sendErrorReg(msg);
        }
        return null;
    }




    private void sendErrorReg(String message) {
        logInfo("Error Register ");
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(("Error register. " + message).getBytes());


    }

    private AuthData getData(MessageInterface messagePack) {
        String[] strings =((String) messagePack.getDataObject()).split(" ");
        logInfo("getData:" + Arrays.toString(strings));
        if(strings.length == 2) {
            return new AuthData(strings[0], strings[1]);
        } else return null;
    }




    private void logWarning(String msg) {
        getLogger().warning(msg);
    }

    private void logInfo(String msg) {
        getLogger().info(msg);
    }

    private Logger getLogger() {
        return Objects.requireNonNull(logger);
    }


    public Path getUserDirectory(int id) {
        return Path.of(new DirectoriesDAO(EntityFactoryPSQL.getEntityManager()).getById(id).getCatalog());
    }

    private class AuthData {
        public String login;
        public String pass;

        public AuthData(String login, String pass) {
            this.login = login;
            this.pass = pass;
        }
    }

    private enum  State {
        AUTHORIZATION,
        REGISTRATION,
        AUTH_OK,
        AUTH_FAIL,
        NONE_AUTH;

        State() {
        }
    }

}
