package server.objects;

import core.MessagePack;
import core.interfaces.MessageInterface;
import core.resources.CommandFileControl;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import server.interfaces.ServerInterface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FileHandler extends ChannelInboundHandlerAdapter  {

    private Path rooPath;
    private Logger logger;
    private PooledByteBufAllocator bufAllocator;
    private ServerInterface serverInterface;
    private ChannelHandlerContext context;
    private State state;



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
        state = State.IDLE;
    }



    private void listOfFiles() {
        logInfo("send list of files in  directory" + rooPath.getFileName());
        try {
            List<Path> fileList = Files.list(rooPath).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
            context.writeAndFlush(new MessagePack<CommandFileControl>(CommandFileControl.LISTREMOTE, fileList));
        } catch (IOException e) {
            logWarning(String.format("%s get error on list command: %s",
                    context.channel().remoteAddress().toString(),
                    e.getMessage()));
        }
    }

    private void deleteFile(MessageInterface messageInterface) {
        String str = (String)messageInterface.getDataObject();
        logInfo("Start delete file" + str);
        if(Files.exists(Path.of(str))) {
            try {
                Files.delete(Path.of(str));
                context.writeAndFlush(new MessagePack<CommandFileControl>(CommandFileControl.REMOVEFILEREM,
                        "File success deleted"));
            } catch (IOException e) {
                context.writeAndFlush(new MessagePack<CommandFileControl>(CommandFileControl.REMOVEFILEREM,
                        "File success deleted: "+ e.getMessage()));
                logWarning(String.format("File cant deleted because of : %s", e.getMessage()));
            }
        } else {
            context.writeAndFlush(new MessagePack<CommandFileControl>(CommandFileControl.REMOVEFILEREM, "Files not found"));
        }
    }

    private void renameFile(MessageInterface messageInterface) {
        String[] strings = ((String) messageInterface.getDataObject()).split(" ");
        if(strings.length == 2) {
            Path path = Path.of(strings[0]);
            logInfo(String.format("Rename file : %s", strings[0]));
            if(Files.exists(path)) {
                try {
                    Files.move(path,path.resolveSibling(strings[1]));
                } catch (IOException e) {
                    logWarning(String.format("cant rename file %s", path.getFileName()));
                    context.writeAndFlush(new MessagePack<CommandFileControl>(CommandFileControl.RENAMEREMOTEFILE,
                            "Cant rename file cause " + e.getMessage()));
                }
            }
            else {
                context.writeAndFlush(new MessagePack<CommandFileControl>(CommandFileControl.RENAMEREMOTEFILE, "Files not found"));
            }
        }
        else {
            context.writeAndFlush(new MessagePack<CommandFileControl>(CommandFileControl.RENAMEREMOTEFILE, "Incorrect format data"));
        }
    }



    private void logInfo(String str) {
        if(Objects.nonNull(logger)) {
            logger.info(String.format("Client %s :" + str, context.channel().remoteAddress().toString()));
        }
    }
    private void logWarning(String str) {
        if(Objects.nonNull(logger)) {
            logger.warning(String.format("Client %s :" + str, context.channel().remoteAddress().toString()));
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

    private enum  State {
        IDLE,
        UPLOAD,
        DOWNLOAD
    }

}
