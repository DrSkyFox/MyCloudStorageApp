package core.resources;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileInformation implements Serializable {
    private String name;
    private String length;
    private String date;



    public FileInformation(Path path) {
        this.name = path.getFileName().toString();
        try {
            this.length = getStringLength(Files.size(path));
            this.date = Files.getLastModifiedTime(path).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileInformation(String name) {
        this.name = name;
    }

    private String getStringLength(long length) {
        if (length < 1024) return length + "b";
        else if (length < 1024 * 1024) return String.format("%.2fKb", length / 1024.);
        else if (length < 1024 * 1024 * 1024) return String.format("%.2fMb", length / (1024. * 1024));
        else return String.format("%.2fGb", length / (1024. * 1024 * 1024));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "FileInformation{" +
                "name='" + name + '\'' +
                ", length='" + length + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
