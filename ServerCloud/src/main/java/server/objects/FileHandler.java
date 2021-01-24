package server.objects;

import core.MessagePack;
import core.resources.CommandFileControl;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import server.interfaces.ServerInterface;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FileHandler extends ChannelInboundHandlerAdapter  {

    private Path rooPath;
    private Logger logger;
    private PooledByteBufAllocator bufAllocator;
    private ServerInterface serverInterface;
    private ChannelHandlerContext context;




    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        serverInterface.getLogger().getLoggerServ().info("FileHandler Added to user " + context.channel().remoteAddress().toString());
        ctx.writeAndFlush(new MessagePack<CommandFileControl>(CommandFileControl.MESSAGE, "Authorization success. FileControl success initialized"));
        super.handlerAdded(ctx);
        context = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        MessagePack<CommandFileControl> messagePack = (MessagePack<CommandFileControl>) msg;
        CommandFileControl fileControl = CommandFileControl.findCommand(messagePack.getCommandByte());

    }

    public FileHandler(ServerInterface serverInterface, Path path) {
        this.serverInterface = serverInterface;
        this.rooPath = path;
        this.logger = serverInterface.getLogger().getLoggerFile();
        bufAllocator = PooledByteBufAllocator.DEFAULT;
        setUserDirectory();
    }



    public void listOfFiles() {
        logger.info("send list of files in  directory" + rooPath.getFileName());
        try {
            List<Path> fileList = Files.list(rooPath).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
            context.writeAndFlush(new MessagePack<CommandFileControl>(CommandFileControl.LISTREMOTE, fileList));
        } catch (IOException e) {
            logger.warning(String.format("%s get error on list command: %s",
                    context.channel().remoteAddress().toString(),
                    e.getMessage()));
        }
    }



    private void setUserDirectory() {
        if(Files.notExists(rooPath)) {
            try {
                Files.createDirectories(rooPath);
            } catch (IOException e) {
                logger.info("Cant create directory"  + e.getMessage());
            }
        }
    }


}
