package pt.ufp.edu.projecttracker.exceptions;

public class ProjectChangeStateToFinishedWithUnfinishedTasks extends RuntimeException{

    public ProjectChangeStateToFinishedWithUnfinishedTasks(String message) {
        super(message);
    }
}
