package example;

import core.MessagePack;
import core.resources.CommandAuthorization;
import core.resources.FileInformation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

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
                                pipeline.addLast(new ObjectEncoder());
                                pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                                pipeline.addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        ctx.writeAndFlush(
                                                new MessagePack<>(CommandAuthorization.AUTUSER, "user pass")
                                        );
                                    }

                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        FileInformation information = (FileInformation) msg;
                                        System.out.println(information.toString());
                                    }
                                });
                                pipeline.addLast(new ChunkedWriteHandler());
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
