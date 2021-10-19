package server.interfaces;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;

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



     // Settings logger section --------------------------------------------------------------
     void setLogFileForServer(String str);
     void setLogFileForAuthorization(String str);
     void setLogFileForFileTransfer(String str);


     FileHandler getLoggerFileHandlerForServerInformation();
     FileHandler getLoggerFileHandlerForAuthorizationInformation();
     FileHandler getLoggerFileHandlerForFileTransferInformation();

     ConsoleHandler getLoggerConsoleHandlerForServerInformation();
     ConsoleHandler getLoggerConsoleHandlerForAuthorizationInformation();
     ConsoleHandler getLoggerConsoleHandlerForFileTransferInformation();
     // End Settings logger section --------------------------------------------------------------
}

