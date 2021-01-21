package server.interfaces;

import io.netty.buffer.ByteBuf;
import server.entites.Users;

import java.nio.file.Path;

public interface AuthHandlerService {
    void reg(ByteBuf byteBuf);
    void sendErrorReg(String message);
    Users auth(ByteBuf byteBuf);
    Path getUserDirectory(int id);
}
