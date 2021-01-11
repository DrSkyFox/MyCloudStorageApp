package server.services;

import server.interfaces.FileInterface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FileServices implements FileInterface {

    Logger logger = Logger.getLogger(FileServices.class.getName());

    public Stream<Path> getFilesInPath(String path) {
        try {
            return Files.list(Path.of(path));
        } catch (IOException e) {
            logger.info(String.format("Some gonna wrong in %s : %s ", getClass().getName(), e.getMessage()));
        }
        return null;
    }

    @Override
    public List<Files> getDir() {
        return null;
    }

    @Override
    public void deleteFile() {

    }
}
