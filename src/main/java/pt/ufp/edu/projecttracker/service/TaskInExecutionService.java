package pt.ufp.edu.projecttracker.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pt.ufp.edu.projecttracker.api.request.TaskInExecutionDTO;
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
    public void createTaskInExecution(TaskInExecutionDTO taskInExecutionDTO){
        TaskInExecution taskInExecution = new TaskInExecution();
        if(taskInExecutionDTO.getExecutionRate()!=null){
            taskInExecution.setExecutionRate(taskInExecutionDTO.getExecutionRate());
        }
        if(taskInExecutionDTO.getHoursUsed()!=null){
            taskInExecution.setHoursUsed(taskInExecutionDTO.getHoursUsed());
        }
        if(taskInExecutionDTO.getFinishedBy()!=null){
            taskInExecution.setFinishedBy(taskInExecutionDTO.getFinishedBy());
        }
        taskInExecution.setPlannedTask(extractTaskAsPlannedByID(taskInExecutionDTO));
        taskInExecutionRepository.save(taskInExecution);

    }


    private TaskAsPlanned extractTaskAsPlannedByID(TaskInExecutionDTO taskInExecutionDTO){
        Optional<TaskAsPlanned> optionalTaskAsPlanned = taskAsPlannedRepository.findById(taskInExecutionDTO.getTaskAsPlannedID());
        if(optionalTaskAsPlanned.isPresent()){
            return optionalTaskAsPlanned.get();
        }
        throw new EntityNotFoundOnDB("Planned Task not Found");

    }

}
