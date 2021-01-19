package server.interfaces;

import io.netty.buffer.ByteBuf;
import server.entites.Users;

public interface AuthHandlerService {
    void reg(ByteBuf byteBuf);
    void sendErrorReg(String message);
    Users auth(ByteBuf byteBuf);
}
