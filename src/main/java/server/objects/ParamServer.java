package server.objects;

import server.interfaces.SettingServer;

public class  ParamServer implements SettingServer {
    private int port;
    private String nameService;
    private int bufferMax = 8192;
    private int bufferMin = 1024;
    private String rootDirectory;
    private String fileLogs;
    private String fileLogsAuth;


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
    public void setLoggerFile(String fileNameLogs) {
        this.fileLogs = fileNameLogs;
    }

    @Override
    public String getLoggerFile() {
        return fileLogs;
    }


    @Override
    public void setLogAuthFile(String fileNameLogsAuth) {
        this.fileLogsAuth = fileNameLogsAuth;
    }

    @Override
    public String getLogAuthFile() {
        return fileLogsAuth;
    }

    @Override
    public String toString() {
        return "ParamServer{" +
                "port=" + port +
                ", nameService='" + nameService + '\'' +
                ", bufferMax=" + bufferMax +
                ", bufferMin=" + bufferMin +
                ", rootDirectory='" + rootDirectory + '\'' +
                ", fileLogs='" + fileLogs + '\'' +
                '}';
    }
}
