package server.objects;

import server.interfaces.SettingServer;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class  ParamServer implements SettingServer {
    private int port;
    private String nameService;
    private int bufferMax = 8192;
    private int bufferMin = 1024;
    private String rootDirectory;

    private int maxCountLogFile = 5;
    private int maxSizeLogFile = 1024 * 1024 * 30;

    private FileHandler fileLogServer;
    private FileHandler fileLogAuthorization;
    private FileHandler fileLogFileTransfer;
    private ConsoleHandler conLogServer;
    private ConsoleHandler conLogAuthorization;
    private ConsoleHandler conLogFileTransfer;

    public ParamServer() throws IOException {
        init();
    }

    private void init() throws IOException {
        SimpleFormatter formatter = new SimpleFormatter();
        this.fileLogServer = new FileHandler("ServerLog.%u.%g.log", maxSizeLogFile, maxCountLogFile,true);
        this.fileLogFileTransfer = new FileHandler("FileOperationLog.%u.%g.log", maxSizeLogFile, maxCountLogFile, true);
        this.fileLogAuthorization = new FileHandler("AuthLog.%u.%g.log", maxSizeLogFile, maxCountLogFile, true);

        conLogServer = new ConsoleHandler();
        conLogAuthorization = new ConsoleHandler();
        conLogFileTransfer = new ConsoleHandler();


        fileLogServer.setFormatter(formatter);
        fileLogAuthorization.setFormatter(formatter);
        fileLogFileTransfer.setFormatter(formatter);

        conLogFileTransfer.setFormatter(formatter);
        conLogAuthorization.setFormatter(formatter);
        conLogServer.setFormatter(formatter);
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String getNameService() {
        return nameService;
    }

    @Override
    public void setNameService(String nameService) {
        this.nameService = nameService;
    }


    @Override
    public int getBufferMax() {
        return bufferMax;
    }

    @Override
    public int getBufferMin() {
        return bufferMin;
    }

    @Override
    public void setBufferMax(int bufferMax) {
        this.bufferMax = bufferMax;
    }

    @Override
    public void setBufferMin(int bufferMin) {
        this.bufferMin = bufferMin;
    }

    @Override
    public String getRootDirectory() {
        return rootDirectory;
    }

    @Override
    public void setRootDirectory(String str) {
        this.rootDirectory = str;
    }

    @Override
    public FileHandler getLoggerFileHandlerForServerInformation() {
        return fileLogServer;
    }

    @Override
    public FileHandler getLoggerFileHandlerForAuthorizationInformation() {
        return fileLogAuthorization;
    }

    @Override
    public FileHandler getLoggerFileHandlerForFileTransferInformation() {
        return fileLogFileTransfer;
    }

    @Override
    public ConsoleHandler getLoggerConsoleHandlerForServerInformation() {
        return conLogServer;
    }

    @Override
    public ConsoleHandler getLoggerConsoleHandlerForAuthorizationInformation() {
        return conLogAuthorization;
    }

    @Override
    public ConsoleHandler getLoggerConsoleHandlerForFileTransferInformation() {
        return conLogFileTransfer;
    }


    @Override
    public void setLogFileForServer(String str) {
        try {
            fileLogServer = new FileHandler(patterForLogFile(str), maxSizeLogFile, maxCountLogFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setLogFileForAuthorization(String str) {
        try {
            fileLogAuthorization = new FileHandler(patterForLogFile(str), maxSizeLogFile, maxCountLogFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setLogFileForFileTransfer(String str) {
        try {
            fileLogFileTransfer = new FileHandler(patterForLogFile(str), maxSizeLogFile, maxCountLogFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String patterForLogFile(String fileName) {
        return fileName + ".%u.%g.log";
    }


}
