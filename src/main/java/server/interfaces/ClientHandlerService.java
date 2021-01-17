package server.interfaces;

<<<<<<< HEAD
public interface ClientHandlerService {


=======
import io.netty.buffer.ByteBuf;

public interface ClientHandlerService {
    String getLogging();
    void closeChannel();
    ByteBuf getByteBuf();
>>>>>>> Command Edit
}
