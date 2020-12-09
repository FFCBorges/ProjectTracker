package pt.ufp.edu.projecttracker.exceptions;

public class ProjectChangeStateToPlanned extends RuntimeException{

    public ProjectChangeStateToPlanned(String message) {
        super(message);
    }
}
