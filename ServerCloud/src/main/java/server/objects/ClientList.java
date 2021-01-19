package server.objects;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import server.interfaces.ClientHandlerService;
import server.interfaces.CloudStorageService;
import server.interfaces.LoggerHandlerService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class ClientList {
    private HashSet<ClientHandlerService> clients = new HashSet<>();
    private LoggerHandlerService logService;
    private CloudStorageService storageService;

    public ClientList(CloudStorageService storageService,LoggerHandlerService logService) {
        this.logService = logService;
        this.storageService = storageService;
    }

    public ClientList() {
    }

    public ClientList(CloudStorageService storageService) {
        this.storageService = storageService;
    }

    public ClientHandlerService addClient(ChannelHandlerContext context, ByteBuf byteBuf) {
        ClientHandlerService clientHandInterface = new ClientDataHandler();
        clients.add(clientHandInterface);
        logService.getLoggerServ().info(String.format(
                "Client connected. Info: %s . Count Clients",
                context.channel().remoteAddress().toString(),
                countClients()));
        return clientHandInterface;
    }

    public void deleteClient(ChannelHandlerContext context, ClientHandlerService clientHandInterface) {
        clients.remove(clientHandInterface);
        logService.getLoggerServ().info("Client disconnected");
    }

    public HashSet<ClientHandlerService> getListClients() {
        logService.getLoggerServ().info("getClients: " + Arrays.toString(clients.toArray()));
        return clients;
    }

    public boolean isOnline(ClientHandlerService checkingClient) {
        for (ClientHandlerService client :clients) {
            if(Objects.equals(client.getLogging(), checkingClient.getLogging())) {
                return true;
            }
        }
        return false;
    }

    public Integer countClients() {
        return clients.size();
    }

    public void closeAllClients() {
        logService.getLoggerServ().info(String.format("Close all clients... Count active clients %s", countClients().toString()));
        clients.forEach(ClientHandlerService::closeChannel);
    }



}

