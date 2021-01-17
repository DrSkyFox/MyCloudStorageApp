package server.objects;

import core.CommandMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import server.interfaces.AuthHandlerService;
import server.interfaces.ClientHandlerService;
import server.interfaces.FileHandlerService;

import java.io.IOException;

public class ClientDataHandler implements ClientHandlerService {

    private CommandMessage commandEnum;
    private ChannelHandlerContext channelHandlerContext;

    private ByteBuf byteBuf;


    private FileHandlerService fileHandler;
    private AuthHandlerService authorization;

    private boolean successLogIn;

    private State state;


    public ClientDataHandler() {
    }

    public void handle() {

    }

    void authSuccess() throws IOException {

    }

    void downloadFinish() {
        state = State.WAITING;
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
