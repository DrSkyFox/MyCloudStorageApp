package server.objects;

import server.interfaces.LoggerHandlerService;
import server.interfaces.SettingServer;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerHandler implements LoggerHandlerService {

    private static Logger serverLog = Logger.getLogger("CloudServer");
    private static Logger authLog = Logger.getLogger("Auth");
    private static Logger fileTransferLog = Logger.getLogger("FileTransfer");



    public LoggerHandler(SettingServer settingServer) {


        serverLog.addHandler(settingServer.getLoggerConsoleHandlerForServerInformation());
        serverLog.addHandler(settingServer.getLoggerFileHandlerForServerInformation());
        serverLog.info("Starting Log");

        authLog.addHandler(settingServer.getLoggerConsoleHandlerForAuthorizationInformation());
        authLog.addHandler(settingServer.getLoggerFileHandlerForAuthorizationInformation());
        authLog.info("Starting Log");

        fileTransferLog.addHandler(settingServer.getLoggerConsoleHandlerForFileTransferInformation());
        fileTransferLog.addHandler(settingServer.getLoggerFileHandlerForFileTransferInformation());
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

    @Override
    public Logger getLoggerFile() {
        return fileTransferLog;
    }
}
