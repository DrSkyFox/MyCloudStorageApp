package core.comminter;

import io.netty.buffer.ByteBuf;

public interface MessageInterface {
    byte getCommand();
    byte[] getCommandData();
}
