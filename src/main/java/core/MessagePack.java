package core;

import core.comminter.MessageInterface;
import io.netty.buffer.ByteBuf;

public class MessagePack implements MessageInterface {
    private byte command;
    private byte[] messages;

    public MessagePack(ByteBuf byteBuf) {
        load(byteBuf);
    }


    private void load(ByteBuf byteBuf) {
        command = byteBuf.readByte();
        messages = new byte[DefaultSettings.LEN_COMM];
        byteBuf.readBytes(messages);
    }

    @Override
    public byte getCommand() {
        return command;
    }

    @Override
    public byte[] getCommandData() {
        return messages;
    }
}
