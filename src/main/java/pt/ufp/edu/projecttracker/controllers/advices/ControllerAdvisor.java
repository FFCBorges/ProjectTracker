package pt.ufp.edu.projecttracker.controllers.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.BadRequestException400;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.EntityNotFoundException404;
import pt.ufp.edu.projecttracker.controllers.advices.reponses.ErrorMessage;

@ControllerAdvice
public class ControllerAdvisor {


    public ControllerAdvisor (){
    }

    @ExceptionHandler({BadRequestException400.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage RequestError(BadRequestException400 exception){
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), exception.getCause());
        return errorMessage;
    }

    @ExceptionHandler(value={EntityNotFoundException404.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessage RequestError(EntityNotFoundException404 exception){
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),exception.getCause());
        return errorMessage;
    }


    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage RequestError(Exception exception){
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), exception.getCause());
        return errorMessage;
    }


}
