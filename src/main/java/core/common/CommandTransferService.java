package core.common;

import core.resources.CommandMessage;
import io.netty.channel.ChannelHandlerContext;

public interface CommandTransferService {

    public void sendCommand(ChannelHandlerContext context, CommandMessage commandMessage);
    public void sendCommand(ChannelHandlerContext context, CommandMessage commandMessage, byte... bytes) ;
    void sendData(ChannelHandlerContext context, byte[] bytes);

}
