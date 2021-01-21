package server.interfaces;

import java.nio.file.Path;

public interface FileHandlerService {
    void setRootDirectory(Path path);
    void listOfFiles();
}
