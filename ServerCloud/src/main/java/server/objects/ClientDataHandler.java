package server.objects;


import core.MessagePack;
import core.exceptions.IncorrectCommandException;
import core.resources.CommandMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import server.interfaces.*;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public class ClientDataHandler implements ClientHandlerService {

    private MessagePack messagePack;
    private ChannelHandlerContext channelHandlerContext;

    private ByteBuf byteBuf;

    private State state;

    private FileHandlerService fileHandler;
    private AuthHandlerService authorization;
    private CloudStorageService storageService;
    private Logger logger;

    private boolean successLogIn;

    private Integer userID;

    private String name;


    public ClientDataHandler() {
    }

    public ClientDataHandler(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, CloudStorageService storageService) {
        this.channelHandlerContext = channelHandlerContext;
        this.byteBuf = byteBuf;
        this.storageService = storageService;
        this.messagePack = new MessagePack(byteBuf);
        this.authorization = new AuthHandler(channelHandlerContext, storageService.getLogger());
        successLogIn = false;
        logger = storageService.getLogger().getLoggerServ();
        state = State.IDLE;
    }


    void authSuccess() throws IOException {

    }

    @Override
    public void handle() {
        if(Objects.equals(state,State.WAITING)) return;

        while (byteBuf.readableBytes()>0) {
            try {
                stateCheck();
            } catch (IncorrectCommandException e) {
                e.printStackTrace();
            }
        }



    }

    private void stateCheck() throws IncorrectCommandException {
        if (state == State.IDLE) checkLogIn();
    }

    private void checkLogIn() throws IncorrectCommandException {
        if(successLogIn) {
            selectCommand();
        } else  {
            auth();
        }

    }

    private void selectCommand() throws IncorrectCommandException {
        logger.info(String.format("User %s select command: ", channelHandlerContext.channel().remoteAddress()));
        CommandMessage commandMessage = CommandMessage.getCommandMessageSignalByte(byteBuf.readByte());
        state = State.COMMAND_WAIT;
        switch (commandMessage) {
            case UPLOADFILE:

                break;
            case DOWNLOADFILE:

                break;
            case REMOVEFILEREM:

                break;
            case RENAMEREMOTEFILE:

                break;

            case LISTREMOTE:

                break;
            case PACKAGE:
                break;
        }
    }



    private void auth() throws IncorrectCommandException {
        logger.info("auth(): getCommandMessageSignalByte");
        CommandMessage commandMessage = CommandMessage.getCommandMessageSignalByte(byteBuf.readByte());
        if(Objects.equals(CommandMessage.AUTUSER, commandMessage)) {
            logger.info("State Auth");
            state = State.AUTH;
            successLogIn = getAuthorization();
            state = State.IDLE;
        } else if (Objects.equals(CommandMessage.REGUSER, commandMessage)) {
            state = State.REG;
            logger.info("State Reg");
            regUser();
            state = State.IDLE;
        } else state = State.IDLE;
    }


    void downloadFinish() {
        state = State.WAITING;
    }



    private boolean getAuthorization() {
        userID = authorization.auth(byteBuf).getId();
        return true;
    }

    private boolean regUser() {
        authorization.reg(byteBuf);
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ByteBuf getByteBuf() {
        return null;
    }

    @Override
    public String getLogging() {
        return null;
    }

    @Override
    public void closeChannel() {

    }
}
