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
import io.netty.handler.stream.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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
//                            pipeline.addLast(new ObjectEncoder());
//                            pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast("FileWrite", new ChunkedWriteHandler());
                            pipeline.addLast("ClientHandler", new ChannelInboundHandlerAdapter() {

                                private final File file = new File("test.7z");
                                private final FileOutputStream fos = new FileOutputStream(file, true);


                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    super.channelActive(ctx);
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg)  {


                                    if (!file.exists()) {
                                        try {
                                            file.createNewFile();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    ByteBuf byteBuf = (ByteBuf) msg;
                                    ByteBuffer byteBuffer = byteBuf.nioBuffer();

                                    try {
                                        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                                        FileChannel fileChannel = randomAccessFile.getChannel();
                                        while (byteBuffer.hasRemaining()){;
                                            fileChannel.position(file.length());
                                            fileChannel.write(byteBuffer);
                                        }

                                        byteBuf.release();
                                        fileChannel.close();
                                        randomAccessFile.close();
                                    } catch (IOException e) {

                                    }

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


//new ChannelInboundHandlerAdapter() {
//
//

//
//    }