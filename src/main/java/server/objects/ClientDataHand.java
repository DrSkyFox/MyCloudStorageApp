package server.objects;

import core.CommandEnum;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

public class ClientDataHand {

    private CommandEnum commandEnum;
    private ChannelHandlerContext channelHandlerContext;
    private State state;
    private ByteBuf byteBuf;


    public ClientDataHand() {
    }

    public void handle() {

    }

    void authSuccess() throws IOException {

    }

    void downloadFinish() {
        state = State.WAITING;
    }

    void setWaitingThreadState(boolean status) {
        if (status) state = State.WAITING;
        else state = State.WAITING;
    }

    public ByteBuf getByteBuf() {
        return byteBuf;
    }
}
