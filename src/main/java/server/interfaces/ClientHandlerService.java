package server.interfaces;

import io.netty.buffer.ByteBuf;

public interface ClientHandlerService {
    String getLogging();
    void closeChannel();
    ByteBuf getByteBuf();
}
