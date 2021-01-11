package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import server.interfaces.LoggerHandlerService;
import server.interfaces.SettingServer;
import server.objects.ServerCloudHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CloudDriveServer {

    private LoggerHandlerService logHandler;

    private Integer port = 9090;
    private String nameService = "Cloud Storage";

    private SettingServer settingServer;

    public CloudDriveServer() {
    }

    public CloudDriveServer(Integer port) {
        this.port = port;
    }
    public CloudDriveServer(SettingServer settingServer) {
        this.settingServer = settingServer;
    }

    public CloudDriveServer(LoggerHandlerService logHandler) {
        this.logHandler = logHandler;
    }

    public CloudDriveServer(SettingServer settingServer, LoggerHandlerService logHandler) {
        this.logHandler = logHandler;
        this.settingServer = settingServer;
    }

    public CloudDriveServer(LoggerHandlerService logHandler, Integer port) {
        this.logHandler = logHandler;
        this.port = port;
    }

    public CloudDriveServer(LoggerHandlerService logHandler, Integer port, String nameService) {
        this.logHandler = logHandler;
        this.port = port;
        this.nameService = nameService;
    }



    public void start() {
        if(settingServer !=null) {
            port = settingServer.getPort();
            nameService  = settingServer.getNameService();
        }

        try {
            logHandler.getLoggerServ().info("Start Server with Param:");
            if(settingServer != null) {
                logHandler.getLoggerServ().info(settingServer.toString());
            } else {
                logHandler.getLoggerServ().info(toString());
            }

            run();

        } catch (Exception e) {
            logHandler.getLoggerServ().warning("Cant Start Server:");
            logHandler.getLoggerServ().warning(e.getMessage());
        }
    }


    public void run()  {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(new ServerCloudHandler(settingServer));
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        }
        catch (InterruptedException e) {
            logHandler.getLoggerServ().warning(e.getMessage());
        }
        finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }


    private void checkRepositoryExists() {
        logHandler.getLoggerServ().info("Checking Exists Repository");
        if(Files.notExists(Path.of(settingServer.getRootDirectory()))) {
            try {
                logHandler.getLoggerServ().info("Repository Not Found \n Creating Repository... ");
                Files.createDirectories(Path.of(settingServer.getRootDirectory()));
                logHandler.getLoggerServ().info("Repository created " + settingServer.getRootDirectory());
            } catch (IOException e) {
                logHandler.getLoggerServ().warning(e.getMessage());
            }
        }
    }

    public void setSettingServer(SettingServer settingServer) {
        this.settingServer = settingServer;
    }

    public SettingServer getSettingServer() {
        return settingServer;
    }



    @Override
    public String toString() {
        return "CloudDriveServer{" +
                "port=" + port +
                ", nameService='" + nameService + '\'' +
                '}';
    }
}
