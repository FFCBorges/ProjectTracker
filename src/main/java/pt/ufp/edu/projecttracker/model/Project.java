package pt.ufp.edu.projecttracker.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
//Define a classe como uma classe mapeavel para tabela
@Entity
@Table(name = "projects_table")
public class Project {

    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ToString.Include
    @Column (nullable = false)
    private String name;


    @ToString.Include
    @ManyToOne
    @JoinColumn(name="manager",nullable = false)
    private ProjectManager projectManager;

    @ToString.Include
    @Enumerated
    @Column(nullable = false, columnDefinition = "smallint")
    ProjectState projectState = ProjectState.ONGOING_PLANNING;


    @ToString.Include
    @ManyToOne
    @JoinColumn(name="client")
    private Client client;

    @OneToMany(mappedBy = "project")
    private List<TaskAsPlanned> taskAsPlanneds = new ArrayList<TaskAsPlanned>();


    public Project(String name, ProjectManager projectManager, Client client){

        this.name = name;
        this.projectManager=projectManager;
        this.client = client;
        projectManager.getProjects().add(this);
        client.getProjects().add(this);

    }

    public Project(String name, ProjectManager projectManager){

        this.name = name;
        this.projectManager=projectManager;
        //substituir por metodo adicionar projecto
        projectManager.getProjects().add(this);

    }

    public void addCliente(Client client){
            client.getProjects().add(this);
            setClient(client);
    }

    public void  addTask(TaskAsPlanned taskAsPlanned){
        getTaskAsPlanneds().add(taskAsPlanned);
        taskAsPlanned.setProject(this);

    }

    public void removeClient(){
        if(client!=null){
            client.removeProject(this);
            client=null;
        }

    }


}
