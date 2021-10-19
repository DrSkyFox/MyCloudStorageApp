package core.interfaces;

public interface MessageInterface<T> {
    byte getCommandByte();
    Object getDataObject();
    T getCommand();
}
