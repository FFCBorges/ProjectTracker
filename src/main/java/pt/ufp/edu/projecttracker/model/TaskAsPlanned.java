package pt.ufp.edu.projecttracker.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

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
    private Integer horasEstimadas;



    @Enumerated
    @Column(nullable = false, columnDefinition = "smallint")
    private Role employeeType;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Employee employee;



    @OneToOne(mappedBy = "plannedTask", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private TaskInExecution taskInExecution;

    public TaskAsPlanned(Long id, String title, String desc, Integer horasEstimadas, Project project, Role employeeType) {
        this.id = id;
        this.title = title;
        this.description=desc;
        this.horasEstimadas = horasEstimadas;
        this.project = project;
        this.employeeType=employeeType;
        project.addTask(this);

    }

    public TaskAsPlanned(String title, String desc, Integer horasEstimadas, Double custoPrevisto) {
        this.title = title;
        this.description=desc;
        this.horasEstimadas = horasEstimadas;

    }

    public Integer getEstimatedTaskCost(){
        return this.horasEstimadas*getRoleRate(this.employeeType);

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
