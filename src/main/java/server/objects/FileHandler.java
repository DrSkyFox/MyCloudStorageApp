package server.objects;

import io.netty.channel.ChannelHandlerContext;

public class FileHandler {
    private ChannelHandlerContext  context;

    public FileHandler(ChannelHandlerContext context) {
        this.context = context;
    }


}
