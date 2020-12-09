package pt.ufp.edu.projecttracker.exceptions;

public class ProjectChangeStateToOngoingExecution extends RuntimeException{

    public ProjectChangeStateToOngoingExecution(String message) {
        super(message);
    }
}
