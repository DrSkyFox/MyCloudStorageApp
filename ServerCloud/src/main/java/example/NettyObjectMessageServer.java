package example;

import core.MessagePack;
import core.resources.CommandAuthorization;
import core.resources.CommandFileControl;
import core.resources.FileInformation;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.Arrays;


/**
 * Hello world!
 *
 */
public class NettyObjectMessageServer {
    public static void main(String[] args) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            new ServerBootstrap().group(group)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) {
                                    MessagePack<CommandFileControl> command = (MessagePack<CommandFileControl>) msg;
                                    System.out.println("Command byte " + command.getCommandByte());
                                    String [] strings = ((String) command.getDataObject()).split("");
                                    System.out.println("Command login and pass" + Arrays.toString(strings));
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                                    cause.printStackTrace();
                                    ctx.channel().close();
                                }
                            });

                            pipeline.addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) {
                                    MessagePack<CommandAuthorization> command = (MessagePack<CommandAuthorization>) msg;
                                    System.out.println("Command byte " + command.getCommandByte());
                                    String [] strings = ((String) command.getDataObject()).split("");
                                    System.out.println("Command login and pass" + Arrays.toString(strings));


                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                                    cause.printStackTrace();
                                    ctx.channel().close();
                                }
                            });


                            pipeline.addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) {
                                    System.out.println("hand3");
                                    FileInformation fileInformation = (FileInformation) msg;
                                    System.out.println(fileInformation);
                                    ctx.writeAndFlush(fileInformation);

                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                                    cause.printStackTrace();
                                    ctx.channel().close();
                                }
                            });
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
