package server.objects;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import server.interfaces.LoggerHandlerService;
import server.interfaces.SettingServer;


public class ServerCloudHandler extends ChannelInboundHandlerAdapter  {

    private SettingServer settingServer;
    private ByteBuf byteBuf;
    private int buffMax;
    private int buffMin;
    private LoggerHandlerService logger;

    private ClientDataHandler clientDataHandler;

    public ServerCloudHandler(SettingServer settingServer) {
        this.settingServer = settingServer;
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
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }
}
