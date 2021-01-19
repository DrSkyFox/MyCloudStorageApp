package server.interfaces;

import java.nio.file.Files;
import java.util.List;

public interface FileInterface {
    public List<Files> getDir();
    public void deleteFile();

}
