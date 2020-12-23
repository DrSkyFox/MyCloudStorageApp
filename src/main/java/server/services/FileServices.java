package server.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FileServices {

    Logger logger = Logger.getLogger(FileServices.class.getName());

    public Stream<Path> getFilesInPath(String path) {
        try {
            return Files.list(Path.of(path));
        } catch (IOException e) {
            logger.info(String.format("Some gonna wrong in %s : %s ", getClass().getName(), e.getMessage()));
        }
        return null;
    }


}
