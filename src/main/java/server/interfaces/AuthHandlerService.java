package server.interfaces;

public interface AuthHandlerService {
    void reg(ClientHandlerService client);
    void auth(ClientHandlerService client);
    void sendErrorReg(ClientHandlerService client);
}
