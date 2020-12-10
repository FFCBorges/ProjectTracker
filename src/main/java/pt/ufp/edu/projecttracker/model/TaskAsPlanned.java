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

    public TaskAsPlanned(Long id, String title, String desc, Integer estimatedHours, Project project, Role employeeType) {
        this.id = id;
        this.title = title;
        this.description=desc;
        this.estimatedHours = estimatedHours;
        this.project = project;
        this.employeeType=employeeType;
        this.taskInExecution = new TaskInExecution(this.id,this);
        project.addTask(this);


    }

    public TaskAsPlanned(String title, String desc, Integer estimatedHours, Double custoPrevisto) {
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
        LocalDate now = LocalDate.now();

        if(now.isAfter(this.plannedStartDate)) {
            long elapsedDays = ChronoUnit.DAYS.between(this.plannedStartDate, now);
            long daysToDoTheTask = ChronoUnit.DAYS.between(this.plannedStartDate, this.plannedDueDate);
            if((this.taskInExecution.getExecutionRate()/100)>=(elapsedDays/daysToDoTheTask)){
                return true;
            }
            return false;
        }

        return true;
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
