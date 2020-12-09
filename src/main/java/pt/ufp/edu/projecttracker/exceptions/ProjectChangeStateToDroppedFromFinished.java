package pt.ufp.edu.projecttracker.exceptions;

public class ProjectChangeStateToDroppedFromFinished extends RuntimeException{

    public ProjectChangeStateToDroppedFromFinished(String message) {
        super(message);
    }
}
