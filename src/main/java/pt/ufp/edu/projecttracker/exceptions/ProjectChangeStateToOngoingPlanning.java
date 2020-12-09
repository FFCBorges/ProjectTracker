package pt.ufp.edu.projecttracker.exceptions;

public class ProjectChangeStateToOngoingPlanning extends RuntimeException{

    public ProjectChangeStateToOngoingPlanning(String message) {
        super(message);
    }
}
