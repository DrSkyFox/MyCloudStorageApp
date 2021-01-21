package server.objects;

import core.MessagePack;
import core.resources.CommandMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import server.dao.UserDAO;
import server.interfaces.ClientHandlerService;
import server.interfaces.CloudStorageService;
import server.interfaces.FileHandlerService;
import server.interfaces.LoggerHandlerService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FileHandler implements FileHandlerService {
    private ChannelHandlerContext  context;
    private ClientHandlerService  clientHandlerService;
    private Path rooPath;
    private Logger logger;
    private PooledByteBufAllocator bufAllocator;
    private CloudStorageService storageService;


    public FileHandler(ChannelHandlerContext context, ClientHandlerService clientHandlerService, CloudStorageService storageService) {
        this.context = context;
        this.clientHandlerService = clientHandlerService;
        this.logger = storageService.getLogger().getLoggerServ();
        this.bufAllocator = PooledByteBufAllocator.DEFAULT;
        this.storageService =  storageService;
    }


    @Override
    public void listOfFiles() {
        logger.info("send list of files in  directory" + rooPath.getFileName());
        try {
            List<Path> fileList= Files.list(rooPath).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void sendList(List<Path> paths) throws IOException {
        ByteBuf byteBuf = bufAllocator.directBuffer();
        byteBuf.writeByte(CommandMessage.LISTREMOTE.getSignalByte());

        for (Path path: paths
             ) {
            byteBuf.writeBytes(path.getFileName().toString().getBytes(StandardCharsets.UTF_8));
            byteBuf.writeLong(Files.size(path));
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

    @Override
    public void setRootDirectory(Path path) {
        rooPath = path;
        setUserDirectory();
    }
}
