package pt.ufp.edu.projecttracker.controllers.advices.reponses;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class ErrorMessage {

    private String message = "";
    private String timeStamp;
    private String exceptionCauseMessage;

    public ErrorMessage() {
        this.timeStamp = LocalTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_TIME);
    }

    public ErrorMessage(String message) {
        this.timeStamp = LocalTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_TIME);
        this.message = message;
    }

    public ErrorMessage(String message, Throwable t) {
        this.timeStamp = LocalTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_TIME);
        this.message = message;

        this.exceptionCauseMessage=t==null?"":t.getCause().getMessage();
    }


    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return this.timeStamp;
    }

    public String getExceptionCauseMessage() {
        return this.exceptionCauseMessage;
    }

    public void setExceptionCauseMessage(String exceptionCauseMessage) {
        this.exceptionCauseMessage = exceptionCauseMessage;
    }


}
