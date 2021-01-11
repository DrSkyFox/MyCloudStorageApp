package core;

public enum  ListOrError {
    USERALREADYLOGIN("USER ALREADY LOG IN");



    private String logMessage;

    ListOrError(String logMessage) {
        this.logMessage = logMessage;
    }


}
