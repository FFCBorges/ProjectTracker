package pt.ufp.edu.projecttracker.exceptions;

public class ProjectStateNotInExecutionOrFinished extends RuntimeException{

    public ProjectStateNotInExecutionOrFinished(String message) {
        super(message);
    }
}
