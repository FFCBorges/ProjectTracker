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
    private String nome;

    @Column(length = 1023)
    private String desc;

    @Column(nullable = false)
    private Double percentagemDeExec=0d;

    @Column(nullable = false)
    private Integer horasEstimadas;

    @Column(nullable = false)
    private Integer horasConsumidas=0;

    //private Double custoPrevisto;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Employee employee;

    @OneToOne
    private TaskInExecution taskInExecution;

    public TaskAsPlanned(Long id, String nome, String desc, Integer horasEstimadas, Double custoPrevisto, Project project) {
        this.id = id;
        this.nome=nome;
        this.desc=desc;
        this.horasEstimadas = horasEstimadas;
        //this.custoPrevisto = custoPrevisto;
        this.project = project;
        project.addTask(this);

    }

    public TaskAsPlanned(String nome, String desc, Integer horasEstimadas, Double custoPrevisto) {
        this.nome=nome;
        this.desc=desc;
        this.horasEstimadas = horasEstimadas;
        //this.custoPrevisto = custoPrevisto;

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
