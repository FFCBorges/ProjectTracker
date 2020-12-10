package pt.ufp.edu.projecttracker.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

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

    public TaskInExecution(Long id, TaskAsPlanned plannedTask) {
        this.id = id;
        this.plannedTask = plannedTask;
    }

    public Integer getCurrentTaskCost(){
        return this.hoursUsed*this.plannedTask.getRoleRate(this.plannedTask.getEmployeeType());

    }


//    public float taskBudgetDeviation(){
//        float estimateHours = (float)this.getPlannedTask().getEstimatedHours();
//
//
//    }





}
