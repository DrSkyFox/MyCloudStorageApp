package server.objects;


import core.MessagePack;
import core.comminter.MessageInterface;
import io.netty.channel.ChannelHandlerContext;
import server.dao.UserDAO;
import server.entites.Users;
import server.interfaces.LoggerHandlerService;
import server.services.EntityFactoryPSQL;

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
        userDAO.create(new Users(authData.login, authData.pass));
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
