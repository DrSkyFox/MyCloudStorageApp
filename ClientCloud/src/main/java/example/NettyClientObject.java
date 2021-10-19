package example;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.stream.ChunkedNioStream;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;

public class NettyClientObject {

    public static void main(String[] args) throws Exception {

            EventLoopGroup group = new NioEventLoopGroup();
            try {
                new Bootstrap().group(group)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            public void initChannel(SocketChannel socketChannel) {
                                ChannelPipeline pipeline = socketChannel.pipeline();
//                                pipeline.addLast(new ObjectEncoder());
//                                pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                                pipeline.addLast("FileWrite",new ChunkedWriteHandler());
                                pipeline.addLast("ClientHandler",new ChannelInboundHandlerAdapter() {


                                    private final File file = new File("FARCARDS_GK.7z");

                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        super.channelActive(ctx);
                                        System.out.printf("start");
                                        ctx.writeAndFlush(new ChunkedStream(new FileInputStream(file)));
                                    }

                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        System.out.println((String)msg);
                                    }
                                });

                            }
                        })
                        .connect("localhost", 1234)
                        .sync()
                        .channel()
                        .closeFuture()
                        .sync();

            } finally {
                group.shutdownGracefully();
            }
        }


}
