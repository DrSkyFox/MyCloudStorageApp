package server.objects;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import server.interfaces.ClientHandlerService;
import server.interfaces.CloudStorageService;
import server.interfaces.LoggerHandlerService;
import server.interfaces.SettingServer;


public class ServerCloudHandler extends ChannelInboundHandlerAdapter  {

    private SettingServer settingServer;
    private ByteBuf byteBuf;
    private int buffMax;
    private int buffMin;
    private LoggerHandlerService logger;
    private CloudStorageService storageService;

    private ClientHandlerService clientHandlerService;

    public ServerCloudHandler(CloudStorageService storageService, SettingServer settingServer, LoggerHandlerService logger) {
        this.settingServer = settingServer;
        this.storageService = storageService;
    }

    public ServerCloudHandler(SettingServer settingServer, LoggerHandlerService logger) {
        this.settingServer = settingServer;
        this.logger = logger;
    }

    public void setLogger(LoggerHandlerService logger) {
        this.logger = logger;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byteBuf = ByteBufAllocator.DEFAULT.directBuffer(settingServer.getBufferMin(),settingServer.getBufferMax());
        clientHandlerService = storageService.addClient(ctx,byteBuf);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        byteBuf.release();
        storageService.deleteClient(ctx,clientHandlerService);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byteBuf.writeBytes((ByteBuf) msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.getLoggerServ().warning(cause.toString());
        cause.printStackTrace();
    }
}
