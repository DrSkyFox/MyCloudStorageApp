package core.interfaces;

import java.util.HashMap;
import java.util.Map;

public interface CommandInterface<T> {


    String getDescription();

    byte getSignalByte();

    byte getErrorByte();

    int getArgumentsCount();

    String getNameCommand();

    T getCommand();
//
//    T findCommand(byte receivedByte);
//
//    Map<Byte, T> getMapCommand();
}
