package server.interfaces;

public interface SettingServer {
     int getPort() ;
     void setPort(int port) ;
     String getNameService();
     void setNameService(String nameService);

     int getBufferMax();
     int getBufferMin();
     void  setBufferMax(int bufferMax);
     void  setBufferMin(int bufferMin);

     String getRootDirectory();
     void setRootDirectory(String str);

     void setLoggerFile(String fileNameLogs);
     String getLoggerFile();

     void setLogAuthFile(String fileNameLogsAuth);
     String getLogAuthFile();

}

