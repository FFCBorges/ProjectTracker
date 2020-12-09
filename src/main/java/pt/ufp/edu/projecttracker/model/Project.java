package pt.ufp.edu.projecttracker.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pt.ufp.edu.projecttracker.exceptions.*;


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
    @Column (nullable = false, unique = true)
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
    private List<TaskAsPlanned> plannedTasks = new ArrayList<TaskAsPlanned>();

    @Column(length = 1023)
    private String projectDesc;


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
        projectManager.addProject(this);

    }


    public Integer getEstimatedProjectCost(){
        Integer estimatedCost=0;
        for (TaskAsPlanned t:this.plannedTasks) {
            estimatedCost+=t.getEstimatedTaskCost();
        }

        return estimatedCost;
    }

    public Integer getCurrentProjectCost(){
        Integer currentCost=0;
        for(TaskAsPlanned t:this.plannedTasks){
                currentCost+=t.getCurrentTaskCost();
        }
        return currentCost;
    }

    public Double getProjectExecutionRate() {
        double summedExecutionRate = 0d;
        double projectExecutionRate;

            for (TaskAsPlanned t : this.plannedTasks) {
                summedExecutionRate += t.getTaskExecutionRate();
            }
            try {
                projectExecutionRate = summedExecutionRate / plannedTasks.size();
            }
            catch (ArithmeticException e){
                return 0d;
            }
            return projectExecutionRate;

    }



    public void addCliente(Client client){
            client.getProjects().add(this);
            setClient(client);
    }

    public void  addTask(TaskAsPlanned taskAsPlanned){
        if(this.projectState.equals(ProjectState.ONGOING_PLANNING)){
        getPlannedTasks().add(taskAsPlanned);
        taskAsPlanned.setProject(this);}
        else{
            throw new ProjectPlanningOver("The planning phase of this Project is already over. No more tasks can be added to it");
        }

    }

    public void removeClient(){
        if(client!=null){
            client.removeProject(this);
            client=null;
        }

    }


    /*
    * - Ongoing Planning                                feito
    Finished nao pode mudar de estado
    Planned - Finished
    Ongoing Planning

            */
    public void setProjectState(ProjectState newState) {
        if(newState.equals(ProjectState.ONGOING_PLANNING)){
            throw new ProjectChangeStateToOngoingPlanning("Either the Ongoing Planning state is over or the planning phase has already ended, cant change state to Ongoing Planning");
        }

        else if(newState.equals(ProjectState.PLANNED) && !this.projectState.equals(ProjectState.ONGOING_PLANNING)){
            throw new ProjectChangeStateToPlanned("Can only change Project state to Planned if the Project is in the Ongoing Planning state");
        }

        else if(newState.equals(ProjectState.ONGOING_EXECUTION) && !this.projectState.equals(ProjectState.PLANNED)){
            throw new ProjectChangeStateToOngoingExecution("A Project must in the state Planned in order for it's state to be modified to Ongoing Execution");
        }

        else if(newState.equals(ProjectState.FINISHED) && !this.projectState.equals(ProjectState.ONGOING_EXECUTION)){
            throw new ProjectChangeStateToFinished("A Project needs it's state to be Ongoing Execution in order for it's state to be changed to finished");
        }

        else if(newState.equals(ProjectState.FINISHED) && this.getProjectExecutionRate()!=100d){
            throw new ProjectChangeStateToFinishedWithUnfinishedTasks( "Tasks need to be completed in order to classify the Project as Finished");
        }

        else if(newState.equals(ProjectState.DROPPED) && this.projectState.equals(ProjectState.FINISHED)){
            throw new ProjectChangeStateToDroppedFromFinished("Finished Projects can't be dropped");
        }


        this.projectState = newState;
    }
}
