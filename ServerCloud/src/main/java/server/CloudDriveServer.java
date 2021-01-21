package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import server.interfaces.ClientHandlerService;
import server.interfaces.CloudStorageService;
import server.interfaces.LoggerHandlerService;
import server.interfaces.SettingServer;
import server.objects.ClientList;
import server.objects.LoggerHandler;
import server.objects.ServerCloudHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.logging.Logger;

public class CloudDriveServer implements CloudStorageService {

    private LoggerHandlerService logHandler;

    private Integer port = 9090;
    private String nameService = "Cloud Storage";
    private String rootRepositoryDirectory = "defaultRepo";

    private SettingServer settingServer;
    private ClientList clientList;
    private ChannelFuture channelFuture;
    private CloudStorageService storageService;

    public CloudDriveServer() {
        logHandler = new LoggerHandler();
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

    private void checkRootRepository() {
        if(Files.notExists(Path.of(rootRepositoryDirectory))) {
            try {
                Files.createDirectories(Path.of(rootRepositoryDirectory));
            } catch (IOException e) {

            }
        }
    }

    @Override
    public SettingServer getSettings() {
        return settingServer;
    }

    public void start() {
        if(settingServer !=null) {
            port = settingServer.getPort();
            nameService  = settingServer.getNameService();
            rootRepositoryDirectory = settingServer.getRootDirectory();
        }

        try {
            logHandler.getLoggerServ().info("Start Server with Param:");
            if(settingServer != null) {
                logHandler.getLoggerServ().info(settingServer.toString());
            } else {
                logHandler.getLoggerServ().info(toString());
            }
            storageService = CloudDriveServer.this;
            clientList = new ClientList(storageService,logHandler);
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
                            ch.pipeline().addLast(new ServerCloudHandler(storageService, settingServer, logHandler));
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            channelFuture = b.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        }
        catch (InterruptedException e) {
            logHandler.getLoggerServ().warning(e.getMessage());
        }
        finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            closeChannel();
            logHandler.getLoggerServ().info("Server shutdown");
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
    public LoggerHandlerService getLogger() {
        return logHandler;
    }

    @Override
    public ClientHandlerService addClient(ChannelHandlerContext context, ByteBuf byteBuf) {
        return clientList.addClient(context, byteBuf);
    }

    @Override
    public void deleteClient(ChannelHandlerContext context, ClientHandlerService clientHandlerService) {
        clientList.deleteClient(context, clientHandlerService);
    }

    @Override
    public HashSet<ClientHandlerService> getClients() {
        return clientList.getListClients();
    }

    @Override
    public boolean isOnline(ClientHandlerService clientHandlerService) {
        return clientList.isOnline(clientHandlerService);
    }

    @Override
    public void closeChannel() {
        clientList.closeAllClients();
        channelFuture.channel().close();
    }

    @Override
    public String toString() {
        return "CloudDriveServer{" +
                "port=" + port +
                ", nameService='" + nameService + '\'' +
                '}';
    }
}
