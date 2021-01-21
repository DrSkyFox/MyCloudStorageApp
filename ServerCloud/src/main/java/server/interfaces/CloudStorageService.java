package server.interfaces;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashSet;
import java.util.logging.Logger;

public interface CloudStorageService {
    ClientHandlerService addClient(ChannelHandlerContext context, ByteBuf byteBuf);
    void deleteClient(ChannelHandlerContext context, ClientHandlerService clientHandlerService);
    HashSet<ClientHandlerService> getClients();
    boolean isOnline(ClientHandlerService  clientHandlerService);
    void closeChannel();
    LoggerHandlerService getLogger();
    SettingServer getSettings();


}
