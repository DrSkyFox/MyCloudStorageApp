package server;

import server.interfaces.LoggerHandlerService;
import server.interfaces.SettingServer;
import server.objects.LoggerHandler;
import server.objects.ParamServer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class Main {

    public static void main(String[] args)  {

        LoggerHandlerService loggerServer = new LoggerHandler();
        SettingServer settingServer = startParam(args);
        CloudDriveServer cloudDriveServer = new CloudDriveServer(settingServer, loggerServer);
        cloudDriveServer.setSettingServer(settingServer);


        cloudDriveServer.start();


    }

    private static SettingServer startParam(String[] args) {

        Iterator<String> paramStart = Arrays.stream(args).iterator();
        SettingServer settingServer = null;
        try {
            settingServer = new ParamServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (paramStart.hasNext()) {

            if (paramStart.equals("-port")) {
                settingServer.setPort(Integer.valueOf(paramStart.next()));
            }
            if (paramStart.equals("-nameService")) {
                settingServer.setNameService(paramStart.next());
            }
            if (paramStart.equals("-buffMin")) {
                settingServer.setBufferMin(Integer.valueOf(paramStart.next()));
            }
            if (paramStart.equals("-buffMax")) {
                settingServer.setBufferMax(Integer.valueOf(paramStart.next()));
            }
            if(paramStart.equals("-logFileServer")) {
                settingServer.setLogFileForServer(paramStart.next());
            }
            if(paramStart.equals("-logAuthFile")) {
                settingServer.setLogFileForAuthorization(paramStart.next());
            }
            if(paramStart.equals("-logFileOperation")) {
                settingServer.setLogFileForFileTransfer(paramStart.next());
            }

        }
        return settingServer;
    }
}
