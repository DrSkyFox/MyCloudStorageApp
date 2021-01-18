package core.common;

public interface MessageInterface {
    byte getCommand();
    byte[] getCommandData();
}
