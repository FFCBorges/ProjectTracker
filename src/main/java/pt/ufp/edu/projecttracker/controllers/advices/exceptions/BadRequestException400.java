package pt.ufp.edu.projecttracker.controllers.advices.exceptions;


public class BadRequestException400 extends RuntimeException {



    public BadRequestException400(String message) {
        super(message);
    }

//    public BadRequestException400(String message, Throwable cause) {
//        super(message, cause);
//    }
//
//    public BadRequestException400(Throwable cause) {
//        super(cause);
//    }
}
