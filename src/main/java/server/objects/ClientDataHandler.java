package server.objects;

import core.MessagePack;
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
        if(state.equals(State.IDLE)) return;
        try {
            while (byteBuf.readableBytes() > 0) {

            }
        } catch (Exception e) {

        }
    }

    private void stateExecute() {
        if(state.equals(State.IDLE)) {

        }
    }

    void downloadFinish() {
        state = State.WAITING;
    }


    private void getPackage() {
        byte b;
        while (byteBuf.readableBytes() > 0) {
            b = byteBuf.readByte();
            try {
                if(successLogIn && Objects.equals(CommandMessage.getCommandMessageSignalByte(b), CommandMessage.PACKAGE)) {
                    state = State.DOWNLOAD;
                    break;
                } else if (Objects.equals(CommandMessage.getCommandMessageSignalByte(b),CommandMessage.COMMANDWAITING)) {
                    state = State.COMMAND_WAIT;
                    break;
                }
            } catch (Exception e) {
                logger.info(String.format("Exception in getPackage: %s, byte data: %s", e.getMessage(), b));
            }
        }
    }

    private void selectCommand() {
        Objects.requireNonNull(byteBuf);
        if(checkCommand(byteBuf,CommandMessage.AUTUSER)) {
            successLogIn = getAuthorization();
        } else if(checkCommand(byteBuf, CommandMessage.REGUSER )) {
            regUser();
        } else if(successLogIn) {
            selectLoggedCommand();
        } else state = State.IDLE;
    }

    private void selectLoggedCommand() {

    }

    private boolean checkCommand(ByteBuf byteBuf, CommandMessage commandMessage) {
        try {
            return Objects.equals(CommandMessage.getCommandMessageSignalByte(byteBuf.getByte(0)),commandMessage);
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return false;
    }

    private boolean getAuthorization() {
        authorization.auth(byteBuf);
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
