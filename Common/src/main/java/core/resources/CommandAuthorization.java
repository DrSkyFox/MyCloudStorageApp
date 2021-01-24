package core.resources;

import core.exceptions.IncorrectCommandException;
import core.interfaces.CommandInterface;

import java.util.HashMap;
import java.util.Map;

public enum CommandAuthorization implements CommandInterface<CommandAuthorization> {

    REGUSER("-reg", "Registration new user. Format: reg username password", 2, (byte) 1, (byte) 2),
    AUTUSER("-aut", "Authorization user. Format: aut username password", 2, (byte)3, (byte)4),
    EXIT("-exit", "exit from server", 0, (byte) 13, (byte) -1);


    private final String name;
    private final String description;
    private final int argumentsCount;
    private final byte signalByte;
    private final byte errorByte;

    private static Map<Byte, CommandAuthorization> commands = new HashMap<>();
    static {
        for (CommandAuthorization value: CommandAuthorization.values()
             ) {
            commands.put(value.signalByte, value);
        }
    }

    CommandAuthorization(String name, String description, int argumentsCount, byte signalByte, byte errorByte) {
        this.name = name;
        this.description = description;
        this.argumentsCount = argumentsCount;
        this.signalByte = signalByte;
        this.errorByte = errorByte;
    }


    @Override
    public String toString() {
        return "CommandMessageAuthorization{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", argumentsCount=" + argumentsCount +
                ", signalByte=" + signalByte +
                ", errorByte=" + errorByte +
                '}';
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public byte getSignalByte() {
        return signalByte;
    }

    @Override
    public byte getErrorByte() {
        return errorByte;
    }

    @Override
    public int getArgumentsCount() {
        return argumentsCount;
    }

    @Override
    public String getNameCommand() {
        return name;
    }

    @Override
    public CommandAuthorization getCommand() {
        return this;
    }



    public static Map<Byte, CommandAuthorization> getMapCommand() {
        return commands;
    }

    public static CommandAuthorization findCommands(byte receivedByte) {
        CommandAuthorization commandAuthorization = commands.get(receivedByte);
        if(commandAuthorization != null) {
            return commandAuthorization;
        }
        return null;
    }


}
