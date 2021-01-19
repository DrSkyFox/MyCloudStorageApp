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
    private FileHandler fileHandlerServer;
    private FileHandler fileHandlerAuth;




    public LoggerHandler(SettingServer settingServer, boolean useFileHandler) {

        try {
            fileHandlerServer = new FileHandler(Objects.requireNonNull(settingServer.getLoggerFile()), true);
        } catch (NullPointerException e) {
            e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileHandlerAuth = new FileHandler(Objects.requireNonNull(settingServer.getLogAuthFile()), true);
        } catch (NullPointerException e) {
            e.getMessage();
        }catch (IOException e) {
            e.printStackTrace();
        }

        serverLog.addHandler(fileHandlerServer);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandlerServer.setFormatter(formatter);
        serverLog.info("Starting Log");
    }

    public LoggerHandler() {
    }

    public LoggerHandler(SettingServer settingServer) {
        this(settingServer, true);
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
