package pt.ufp.edu.projecttracker.exceptions;

public class ProjectChangeStateToFinished extends RuntimeException{

    public ProjectChangeStateToFinished(String message) {
        super(message);
    }
}
