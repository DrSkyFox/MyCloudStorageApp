package core.resources;

import core.exceptions.ArgumentException;

import java.util.Arrays;
import java.util.List;

public enum CommandMessage {


    REGUSER("reg", "Registration new user. Format: reg username password", 2, (byte) 1, (byte) 2),
    AUTUSER("aut", "Authorization user. Format: aut username password", 2, (byte)3, (byte)4),
    UPLOADFILE("upload", "Upload file to cloud storage. File must without space. Format: upload fileName", 1, (byte) 5, (byte) -1),
    DOWNLOADFILE("upload", "Download file from cloud storage. File must without space. Format: download fileName", 1, (byte) 6, (byte) -1),
    LISTLOCAL("listloc", "Lists files in the local storage", 1, (byte) 7, (byte) -1),
    LISTREMOTE("listrem", "Lists files in the local storage", 1, (byte) 8, (byte) -1),
    RENAMELOCALFILE("renameloc", "Rename local file. Format: renameloc file_name new_filename", 2, (byte) 9, (byte) -1),
    RENAMEREMOTEFILE("renamerem", "Rename local file. Format: renamerem file_name new_filename", 2, (byte) 9, (byte) -1),
    REMOVEFILELOC("removeloc", "Remove local file. Format: removeloc file_name", 1, (byte) 11, (byte) -1),
    REMOVEFILEREM("removeloc", "Remove remote file. Format: removeloc file_name", 1, (byte) 12, (byte) -1),
    EXIT("exit", "exit from server", 0, (byte) 13, (byte) -1),
    PACKAGE(null, "PACKAGE", 0 , (byte) 14, (byte) -1),
    COMMANDWAITING(null, "Command waiting", 0,(byte) 15, (byte) -1);


    private final String name;
    private final String description;
    private final int argumentsCount;
    private final byte signalByte;
    private final byte errorByte;

    public String getDescription() {
        return description;
    }

    public byte getSignalByte() {
        return signalByte;
    }

    public byte getErrorByte() {
        return errorByte;
    }

    CommandMessage(String name, String description, int argumentsCount, byte signalByte, byte errorByte) {
        this.name = name;
        this.description = description;
        this.argumentsCount = argumentsCount;
        this.signalByte = signalByte;
        this.errorByte = errorByte;
    }

    public boolean checkArguments(List<String> args) throws ArgumentException {
        if(args.size() != argumentsCount) {
            throw new ArgumentException(String.format("Command %s error. Wrong number arguments. Must be - %s. Info %s",name, argumentsCount, description));
        }
        return true;
    }

    public static CommandMessage getCommandMessageSignalByte(byte signalByte) throws Exception {
        return Arrays.stream(CommandMessage.values())
                .filter(commandMessage -> commandMessage.signalByte == signalByte)
                .findFirst().orElseThrow(() ->
            new Exception(String.format("Error in findCommand. Args %s",signalByte))
        );
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
}
