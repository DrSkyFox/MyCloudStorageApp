package server.objects;

import server.interfaces.LoggerHandlerService;
import server.interfaces.SettingServer;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerHandler implements LoggerHandlerService {
    private static Logger serverLog = Logger.getLogger("CloudServer");
    private static Logger authLog = Logger.getLogger("Auth");
    private FileHandler fileHandlerServer;
    private FileHandler fileHandlerAuth;

    public LoggerHandler(SettingServer settingServer) {

        if (settingServer.getLoggerFile() != null) {
            try {
                fileHandlerServer = new FileHandler(settingServer.getLoggerFile(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (settingServer.getLogAuthFile() != null) {
            try {
                fileHandlerAuth = new FileHandler(settingServer.getLogAuthFile(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        serverLog.addHandler(fileHandlerServer);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandlerServer.setFormatter(formatter);
        serverLog.info("Starting Log");


    }

    public LoggerHandler() {

    }



    @Override
    public Logger getLoggerAuth() {
        return authLog;
    }

    @Override
    public Logger getLoggerServ() {
        return serverLog;
    }
}
