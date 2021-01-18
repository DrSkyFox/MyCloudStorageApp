package core.transfer;

import core.resources.CommandMessage;
import core.common.CommandTransferService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;

public class CommandTransfer implements CommandTransferService{

    private static PooledByteBufAllocator bufAllocator = PooledByteBufAllocator.DEFAULT;


    public CommandTransfer() {
    }

    @Override
    public void sendData(ChannelHandlerContext context, byte[] bytes) {
        ByteBuf byteBuf = bufAllocator.directBuffer();
        byteBuf.writeBytes(bytes);
        context.writeAndFlush(byteBuf);
    }

    @Override
    public void sendCommand(ChannelHandlerContext context, CommandMessage commandMessage) {
        sendCommand(context, commandMessage,  (byte)0);
    }

    @Override
    public void sendCommand(ChannelHandlerContext context, CommandMessage commandMessage, byte... bytes) {
        ByteBuf byteBuf = getCommandMessage(commandMessage);
        byteBuf.writeBytes(bytes);
        context.writeAndFlush(byteBuf);
    }

    private ByteBuf getCommandMessage(CommandMessage commandMessage) {
        ByteBuf byteBuf = bufAllocator.directBuffer();
        byteBuf.writeByte(commandMessage.getSignalByte());
        return byteBuf;
    }



}
