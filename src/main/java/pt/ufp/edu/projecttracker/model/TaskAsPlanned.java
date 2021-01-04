package pt.ufp.edu.projecttracker.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@ToString()
@Entity
@Table(name = "Planned_Tasks")

public class TaskAsPlanned {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1023)
    private String description;


    @Column(nullable = false)
    private Integer estimatedHours;

    @Enumerated
    @Column(nullable = false, columnDefinition = "smallint")
    private Role employeeType;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Employee employee;

    private LocalDate plannedStartDate;

    private LocalDate plannedDueDate;



    @OneToOne(mappedBy = "plannedTask", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private TaskInExecution taskInExecution;


    public TaskAsPlanned(Integer estimatedHours, Role employeeType, Project project, LocalDate plannedStartDate, LocalDate plannedDueDate) {
        this.estimatedHours = estimatedHours;
        this.employeeType = employeeType;
        this.project = project;
        this.plannedStartDate = plannedStartDate;
        this.plannedDueDate = plannedDueDate;
        this.taskInExecution= new TaskInExecution(this);
    }

    public TaskAsPlanned(String title, String desc, Integer estimatedHours, Project project, Role employeeType) {
        this.title = title;
        this.description=desc;
        this.estimatedHours = estimatedHours;
        this.project = project;
        this.employeeType=employeeType;
        project.addTask(this);


    }

    public TaskAsPlanned(String title, String desc, Integer estimatedHours) {
        this.title = title;
        this.description=desc;
        this.estimatedHours = estimatedHours;

    }

    public Integer getEstimatedTaskCost(){
        return this.estimatedHours *getRoleRate(this.employeeType);

    }

    public Integer getRoleRate(Role role){

            if(role.equals(Role.JUNIOR_DEVELOPER)){
                return 5+5;
            }
            if(role.equals(Role.JUNIOR_ANALYST)){
                return 15+5;
            }
            if(role.equals(Role.SENIOR_DEVELOPER)){
                return 35+5;
            }
            if(role.equals(Role.SENIOR_ANALYST)){
                return 80;
            }
            return -1;
    }

    public Integer getCurrentTaskCost(){
        if(this.taskInExecution!=null){
            return this.taskInExecution.getCurrentTaskCost();
        }
        return 0;
    }


    public Double getTaskExecutionRate(){
        if(this.taskInExecution!=null){
            return this.taskInExecution.getExecutionRate();
        }
        return 0d;
    }


    /**
     * This method evaluates if a Task is being executed
     * on schedule or not
     * @return true if: %execution >= (elapsed time since start / (due date time - start date time)))
     * else return false
     */
    public boolean onTime(){
        return this.getTaskInExecution().onTime();
    }

    public boolean onTime(LocalDate date){
        return this.getTaskInExecution().onTime(date);
    }


//
//    public void addEmployee(Employee employee){
//        setEmployee(employee);
//        employee.getTaskAsPlanneds().add(this);
//
//    }
//
//    public void removeEmployee(){
//        if (employee!=null){
//            employee.removeTask(this);
//            employee=null;
//        }
//    }
}
