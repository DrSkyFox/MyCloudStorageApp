package core.resources;

import core.interfaces.CommandInterface;

import java.util.HashMap;
import java.util.Map;

public enum CommandFileControl implements CommandInterface<CommandFileControl> {


    UPLOADFILE("-upload", "Upload file to cloud storage. File must without space. Format: upload fileName", 1, (byte) 5, (byte) -1),
    DOWNLOADFILE("-upload", "Download file from cloud storage. File must without space. Format: download fileName", 1, (byte) 6, (byte) -1),
    LISTLOCAL("-listloc", "Lists files in the local storage", 1, (byte) 7, (byte) -1),
    LISTREMOTE("-listrem", "Lists files in the local storage", 1, (byte) 8, (byte) -1),
    RENAMELOCALFILE("-renameloc", "Rename local file. Format: renameloc file_name new_filename", 2, (byte) 9, (byte) -1),
    RENAMEREMOTEFILE("-renamerem", "Rename local file. Format: renamerem file_name new_filename", 2, (byte) 9, (byte) -1),
    REMOVEFILELOC("-removeloc", "Remove local file. Format: removeloc file_name", 1, (byte) 11, (byte) -1),
    REMOVEFILEREM("-removeloc", "Remove remote file. Format: removeloc file_name", 1, (byte) 12, (byte) -1),
    PACKAGE(null, "-PACKAGE", 0 , (byte) 14, (byte) -1),
    MESSAGE(null,"MESSAGE", 0, (byte) 15, (byte) -1 );



    private final String name;
    private final String description;
    private final int argumentsCount;
    private final byte signalByte;
    private final byte errorByte;



    CommandFileControl(String name, String description, int argumentsCount, byte signalByte, byte errorByte) {
        this.name = name;
        this.description = description;
        this.argumentsCount = argumentsCount;
        this.signalByte = signalByte;
        this.errorByte = errorByte;
    }

    private static Map<Byte, CommandFileControl> commands = new HashMap<>();
    static {
        for (CommandFileControl value: CommandFileControl.values()
        ) {
            commands.put(value.signalByte, value);
        }
    }



    @Override
    public String toString() {
        return "CommandMessage{" +
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
    public CommandFileControl getCommand() {
        return this;
    }

    public static CommandFileControl findCommand(byte receivedByte) {
        CommandFileControl commandFileControl = commands.get(receivedByte);
        if(commandFileControl != null) {
            return commandFileControl;
        }
        return null;
    }


    public static Map<Byte, CommandFileControl> getMapCommand() {
        return commands;
    }


}
