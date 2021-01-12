package server.objects;

import core.CommandEnum;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import server.interfaces.AuthService;

public class ClientDataHandler {

    private CommandEnum commandEnum;
    private ChannelHandlerContext channelHandlerContext;
    private State state;
    private ByteBuf byteBuf;
    private AuthService authHandlerService;


    public ClientDataHandler(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, AuthService authHandlerService) {
        this.channelHandlerContext = channelHandlerContext;
        this.authHandlerService = authHandlerService;
        this.byteBuf = byteBuf;
    }




    public void closeChannel() {
        channelHandlerContext.channel().close();
    }

    public ByteBuf getByteBuf() {
        return byteBuf;
    }
}
