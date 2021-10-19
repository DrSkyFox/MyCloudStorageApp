package core;

import core.interfaces.CommandInterface;
import core.interfaces.MessageInterface;

import java.io.Serializable;

public class MessagePack<T extends CommandInterface> implements Serializable, MessageInterface<T> {
    private CommandInterface<T> commandInterface;
    private Object data;

    public MessagePack(CommandInterface command, Object data) {
        this.commandInterface = command;
        this.data = data;
    }

    @Override
    public byte getCommandByte() {
        return commandInterface.getSignalByte();
    }

    @Override
    public Object getDataObject() {
        return data;
    }

    @Override
    public T getCommand() {
        return commandInterface.getCommand();
    }
}


