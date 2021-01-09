package pt.ufp.edu.projecttracker.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pt.ufp.edu.projecttracker.exceptions.EntityNotFoundOnDB;
import pt.ufp.edu.projecttracker.model.TaskAsPlanned;
import pt.ufp.edu.projecttracker.model.TaskInExecution;
import pt.ufp.edu.projecttracker.repositories.TaskAsPlannedRepository;
import pt.ufp.edu.projecttracker.repositories.TaskInExecutionRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskInExecutionService {

    private final TaskInExecutionRepository taskInExecutionRepository;
    private final TaskAsPlannedRepository taskAsPlannedRepository;

    @Transactional
    public void createTaskInExecution(TaskInExecution taskInExecution){
        taskInExecutionRepository.save(taskInExecution);

    }


    public TaskAsPlanned extractTaskAsPlannedByID(Long id){
        Optional<TaskAsPlanned> optionalTaskAsPlanned = taskAsPlannedRepository.findById(id);
        if(optionalTaskAsPlanned.isPresent()){
            return optionalTaskAsPlanned.get();
        }
        throw new EntityNotFoundOnDB("Planned Task not Found");

    }

}
