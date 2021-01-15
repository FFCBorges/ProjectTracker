package pt.ufp.edu.projecttracker.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@ToString()
@Entity
@Table(name = "Tasks_In_Execution")

public class TaskInExecution {


    @Id
    private Long id;

    @Column(nullable = false)
    private Double executionRate =0d;

    @Column(nullable = false)
    private Integer hoursUsed=0;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private TaskAsPlanned plannedTask;

    private LocalDate finishedBy;

    public TaskInExecution(TaskAsPlanned plannedTask) {
        this.plannedTask = plannedTask;
    }

    public Integer getCurrentTaskCost(){
        return this.hoursUsed*this.plannedTask.getRoleRate(this.plannedTask.getEmployeeType());

    }

    public LocalDate getPlannedStartDate(){
        return this.getPlannedTask().getPlannedStartDate();
    }

    public LocalDate getPlannedDueDate(){
        return this.getPlannedTask().getPlannedDueDate();
    }

    public boolean onTime(){
        LocalDate date = LocalDate.now();
        return onTimeLogic(date);

    }

    public boolean onTime(LocalDate date){
       return onTimeLogic(date);
    }


    private boolean onTimeLogic(LocalDate date ){
        if(date.isAfter(getPlannedDueDate())){
            return this.executionRate == 1d;
        }
        //Se a data calha antes do inicio da tarefa retorna verdade
        if(date.isBefore(getPlannedStartDate())){
            return true;
        }

        //caso contrario a data calha a meio das datas da tarefa

        double taskTimeFrame = ChronoUnit.DAYS.between(getPlannedStartDate(),getPlannedDueDate());
        double elapsedTime = ChronoUnit.DAYS.between(getPlannedStartDate(),date);

        return !(this.executionRate < (elapsedTime / taskTimeFrame));

    }

    public void setExecutionRate(Double executionRate) {
        if(executionRate==1d) {
            this.finishedBy=LocalDate.now();
        }
        this.executionRate = executionRate;

    }

//    public void setExecutionRate(Double executionRate, LocalDate finishedBy) {
//        if(executionRate==1d) {
//            this.finishedBy=finishedBy;
//            this.executionRate = executionRate;
//        }
//    }

    public void setPlannedTask(TaskAsPlanned taskAsPlanned){
        this.plannedTask=taskAsPlanned;
        this.id = plannedTask.getId();
    }

    /*ToDo
    * Set FinishedBy in a way you cant set it if the task is not finished
    *
    *
    * */

    //    public float taskBudgetDeviation(){
//        float estimateHours = (float)this.getPlannedTask().getEstimatedHours();
//
//
//    }





}
