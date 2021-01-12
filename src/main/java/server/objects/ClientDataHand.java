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



    public ByteBuf getByteBuf() {
        return byteBuf;
    }
}
