package example;

import core.MessagePack;
import core.resources.CommandAuthorization;
import core.resources.CommandFileControl;
import core.resources.FileInformation;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.*;
import java.util.Arrays;


/**
 * Hello world!
 */
public class NettyObjectMessageServer {
    public static void main(String[] args) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            new ServerBootstrap().group(group)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel socketChannel) throws FileNotFoundException {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast("ClientHandler", new ChannelInboundHandlerAdapter() {

                                private final File file = new File("test.txt");
                                private final FileOutputStream outputStream = new FileOutputStream(file, true);

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    super.channelActive(ctx);
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg)  {
                                    ByteBuf byteBuf = (ByteBuf) msg;

                                    byte[] bytes = byteBuf.array();
                                    System.out.println(bytes.length);
                                    try {
                                        for (int i = 0; i < bytes.length; i++) {
                                            outputStream.write(bytes[i]);
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } finally {
                                        try {
                                            outputStream.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    ctx.writeAndFlush("file write bytes " + byteBuf.array().length);
                                }
                            });
                            pipeline.addLast("FileWrite", new ChunkedWriteHandler());
                        }
                    })
                    .bind("localhost", 1234)
                    .sync()
                    .channel()
                    .closeFuture()
                    .syncUninterruptibly();

        } finally {
            group.shutdownGracefully();
        }
    }
}


//new ChannelInboundHandlerAdapter() {
//
//

//
//    }