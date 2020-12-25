package server;

import java.util.Arrays;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) throws Exception {
        Iterator<String> paramStart = Arrays.stream(args).iterator();
        ParamServer paramServer = new ParamServer();
        while (paramStart.hasNext()) {
            if(paramStart.equals("-port")) {
                paramServer.setPort(Integer.valueOf(paramStart.next()));
            }
        }

        new CloudDriveServer(paramServer.getPort()).run();

    }
}
