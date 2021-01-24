package server.objects;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import server.interfaces.ServerInterface;

import java.util.logging.Logger;


public class ChannelClientHandler extends ChannelInboundHandlerAdapter  {

    private Logger logger;
    private ServerInterface serverInterface;

    private String clientInfo;
    private ChannelId id;

    public ChannelClientHandler(ServerInterface serverInterface) {
        this.serverInterface = serverInterface;
        logger = serverInterface.getLogger().getLoggerServ();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        serverInterface.getChannelGroup().add(ctx.channel());
        clientInfo  = ctx.channel().remoteAddress().toString();
        id = ctx.channel().id();
        logger.info(String.format("Client connect: %s ", ctx.channel().remoteAddress().toString());

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        for (Channel channel : serverInterface.getChannelGroup()) {
            if(channel != ctx.channel()) {
                channel.writeAndFlush("Client close");
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)  {
        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)  {
        logger.warning(cause.toString());
        cause.printStackTrace();
    }


    @Override
    public String toString() {
        return "ChannelClientHandler{" +
                "clientInfo='" + clientInfo + '\'' +
                ", id=" + id.asLongText() +
                '}';
    }
}
