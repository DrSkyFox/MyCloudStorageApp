package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;
import server.interfaces.ServerInterface;
import server.interfaces.LoggerHandlerService;
import server.interfaces.SettingServer;
import server.objects.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.logging.Logger;

public class CloudDriveServer implements ServerInterface {

    private LoggerHandlerService logHandler;
    private Logger serverLog;
    private SettingServer settingServer;

    private ChannelFuture channelFuture;
    private ServerInterface storageService;
    private String nameServer;

    private final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);




    public CloudDriveServer(SettingServer settingServer, LoggerHandlerService logHandler) {
        this.logHandler = logHandler;
        this.settingServer = settingServer;
        storageService = this;
        serverLog = logHandler.getLoggerServ();
    }

    private void checkRootRepository() {
        if(Files.notExists(Path.of(settingServer.getRootDirectory()))) {
            try {
                Files.createDirectories(Path.of(settingServer.getRootDirectory()));
            } catch (IOException e) {
                serverLog.info(String.format("Cant create root directories for cloud server: %s", e.getMessage()));
                serverLog.warning(Arrays.toString(e.getStackTrace()));
            }
        }
    }

    @Override
    public SettingServer getSettings() {
        return settingServer;
    }

    public void start() {
        nameServer =settingServer.getNameService();
        checkRootRepository();
        try {
            logHandler.getLoggerServ().info("Start Server with Param:");
            logHandler.getLoggerServ().info(settingServer.toString());
            storageService = CloudDriveServer.this;
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
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                            ch.pipeline().addLast(new ChannelClientHandler(storageService));
                            ch.pipeline().addLast(new AuthHandler(storageService));
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            channelFuture = b.bind(settingServer.getPort()).sync();
            channelFuture.channel().closeFuture().sync();
        }
        catch (InterruptedException e) {
            logHandler.getLoggerServ().warning(e.getMessage());
        }
        finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            channelGroup.close();
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
    public ChannelGroup getChannelGroup() {
        return channelGroup;
    }

    @Override
    public LoggerHandlerService getLogger() {
        return logHandler;
    }



}
