package server;

import server.interfaces.LoggerHandlerService;
import server.interfaces.SettingServer;
import server.objects.LoggerHandler;
import server.objects.ParamServer;

import java.util.Arrays;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) throws Exception {

        LoggerHandlerService loggerServer = new LoggerHandler();
        CloudDriveServer cloudDriveServer = new CloudDriveServer(loggerServer);

        if(args != null) {
            SettingServer settingServer = startParam(args, loggerServer);
            cloudDriveServer.setSettingServer(settingServer);
        }

        cloudDriveServer.start();


    }

    private static SettingServer startParam(String[] args, LoggerHandlerService loggerHand) {

        Iterator<String> paramStart = Arrays.stream(args).iterator();
        SettingServer settingServer = new ParamServer();

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
            if(paramStart.equals("-logFile")) {
                settingServer.setLoggerFile(paramStart.next());
            }
            if(paramStart.equals("-logAuthFile")) {
                settingServer.setLogAuthFile(paramStart.next());
            }
        }
        return settingServer;
    }
}
