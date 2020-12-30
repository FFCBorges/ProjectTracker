package pt.ufp.edu.projecttracker.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pt.ufp.edu.projecttracker.exceptions.*;

import javax.persistence.*;
import java.time.LocalDate;
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

    /**
     * This method alters the state of a given project
     * @param newState the state to put a given Project on
     *
     * n.b. some state changes are not allowed depending
     *      on the current project state
     */

    public void setProjectState(ProjectState newState) {
        if(newState.equals(this.projectState)) return;
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

        else if(newState.equals(ProjectState.FINISHED) && this.getProjectExecutionRate()!=1d){
            throw new ProjectChangeStateToFinishedWithUnfinishedTasks( "Tasks need to be completed in order to classify the Project as Finished");
        }

        else if(newState.equals(ProjectState.DROPPED) && this.projectState.equals(ProjectState.FINISHED)){
            throw new ProjectChangeStateToDroppedFromFinished("Finished Projects can't be dropped");
        }

        this.projectState = newState;
    }


    /**
     * This method scans all it's planned tasks
     * and returns the earliest start date
     * it assumes the project will starts as
     * soon as the first Planned Tasks is due to start
     * @return earliest Planned Task start date
     */
    public LocalDate getStartingDate(){
        LocalDate starting=LocalDate.MAX;
        for(TaskAsPlanned t:this.plannedTasks) {
            if(t.getPlannedStartDate().isBefore(starting)){
                starting=t.getPlannedStartDate();
            }
        }
        return starting;
    }

    /**
     * This method scans all it's planned tasks
     * and returns the latest due date
     * it assumes the project is due when
     * the latest Planned Tasks is due
     * @return latest Planned Task due date
     */
    public LocalDate getDueDate(){
        LocalDate due=LocalDate.MIN;
        for(TaskAsPlanned t:this.plannedTasks) {
            if(t.getPlannedDueDate().isAfter(due)){
               due=t.getPlannedDueDate();
            }
        }
        return due;
    }


    /**
     * This method evaluates if the execution is keeping up with
     * the time frame of the project
     * @return true if all tasks are being executed according
     * to planning (time wise) false otherwise
     *
     */


    public boolean onTime(LocalDate date){
        return onTimeLogic(date);
    }

    public boolean onTime(){
        return onTimeLogic(LocalDate.now());
    }

    private boolean onTimeLogic(LocalDate date) {
        if(this.projectState.equals(ProjectState.PLANNED)||this.projectState.equals(ProjectState.ONGOING_PLANNING)) return true;
        else if(this.projectState.equals(ProjectState.ONGOING_EXECUTION)) {
            for (TaskAsPlanned t : this.plannedTasks) {
                if (!t.onTime(date)) return false;
            }
            return true;
        }
        else if(this.projectState.equals(ProjectState.FINISHED)){
            LocalDate projectDueDate = this.getDueDate();
            for (TaskAsPlanned t : this.plannedTasks) {
                if(t.getTaskInExecution().getFinishedBy().isAfter(projectDueDate)) return false;

            }
            return true;
        }

        throw new IllegalStateException("There is no such evaluation for Dropped Projects");
    }


    public boolean onBudget(){

        if(this.getProjectState().equals(ProjectState.ONGOING_EXECUTION)){
            double executionRate = this.getProjectExecutionRate();
            int currentCost = this.getCurrentProjectCost();
            int estimatedCost = this.getEstimatedProjectCost();
            double shareOfSpentBudget = ((double)currentCost)/estimatedCost;
            if(executionRate>=shareOfSpentBudget){
                return true;
            }
            else{
                return false;
            }
        }
        else if(this.getProjectState().equals(ProjectState.FINISHED)){
            if(this.getEstimatedProjectCost()>=this.getCurrentProjectCost()) return true;
            else return false;
        }

        throw new ProjectStateNotInExecutionOrFinished("Cannot check Budget of projects not undergoing Execution or already Finished ");

    }

    public void setProjectToPlanned() {
        this.setProjectState(ProjectState.PLANNED);
    }


    public Integer numberOfTasks(){

        return this.plannedTasks.size();
    }


    public void setProjectManager(ProjectManager p){
        this.projectManager=p;
    }

}
