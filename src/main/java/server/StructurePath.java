package server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class StructurePath {
    Logger logger = Logger.getLogger(StructurePath.class.getName());
    private String path;

    public StructurePath(String path) {
        this.path = path;
    }

    public Stream<Path> getFilesInPath() {
        try {
            return Files.list(Path.of(path));
        } catch (IOException e) {
            logger.info(String.format("Some gonna wrong in %s : %s ", getClass().getName(), e.getMessage()));
        }
        return null;
    }



}
