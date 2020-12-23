package server.objects;

public class Message {
    private String message;
    private Integer checkSum;

    public Message(String message, Integer checkSum) {
        this.message = message;
        this.checkSum = checkSum;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
