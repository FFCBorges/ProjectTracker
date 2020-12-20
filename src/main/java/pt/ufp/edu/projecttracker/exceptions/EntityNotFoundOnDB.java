package pt.ufp.edu.projecttracker.exceptions;

public class EntityNotFoundOnDB extends RuntimeException{

    public EntityNotFoundOnDB(String message) {
        super(message);
    }
}
