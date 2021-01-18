package server.interfaces;

import io.netty.buffer.ByteBuf;

public interface AuthHandlerService {
    void reg(ByteBuf byteBuf);
    void auth(ByteBuf byteBuf);
    void sendErrorReg(String message);
}
