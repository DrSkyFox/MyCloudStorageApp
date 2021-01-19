package server.objects;

import io.netty.channel.ChannelHandlerContext;
import server.interfaces.ClientHandlerService;

public class FileHandler {
    private ChannelHandlerContext  context;
    private ClientHandlerService  clientHandlerService;

    public FileHandler(ChannelHandlerContext context, ClientHandlerService clientHandlerService) {
        this.context = context;
        this.clientHandlerService = clientHandlerService;
    }




}
