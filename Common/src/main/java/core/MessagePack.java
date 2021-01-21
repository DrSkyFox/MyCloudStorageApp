package core;

import core.common.MessageInterface;
import io.netty.buffer.ByteBuf;

public class MessagePack implements MessageInterface {
    private byte command;
    private byte[] data;

    public MessagePack(ByteBuf byteBuf) {
        load(byteBuf);
    }


    private void load(ByteBuf byteBuf) {
        command = byteBuf.readByte();
        data = new byte[DefaultSettings.LEN_COMM];
        byteBuf.readBytes(data);
    }

    @Override
    public byte getCommand() {
        return command;
    }

    @Override
    public byte[] getCommandData() {
        return data;
    }
}
