package core.comminter;

import core.CommandMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;

public class CommandTransferService {
    private PooledByteBufAllocator bufAllocator = PooledByteBufAllocator.DEFAULT;

    public void sendData(ChannelHandlerContext context, byte[] bytes) {
        ByteBuf byteBuf = bufAllocator.directBuffer();
        byteBuf.writeBytes(bytes);
        context.writeAndFlush(byteBuf);
    }

    public void sendCommand(ChannelHandlerContext context, CommandMessage commandMessage) {
        sendCommand(context, commandMessage,  (byte)0);
    }

    public void sendCommand(ChannelHandlerContext context, CommandMessage commandMessage, byte... bytes) {
    }

    private ByteBuf getCommandMessage(CommandMessage commandMessage) {
        ByteBuf byteBuf = bufAllocator.directBuffer();

        return byteBuf;
    }


}
