package pt.ufp.edu.projecttracker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ufp.edu.projecttracker.exceptions.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


class ProjectTest {

    private Project project;
    private TaskAsPlanned task1;
    private TaskAsPlanned task2;
    private TaskAsPlanned task3;
    private Project project2;

    @BeforeEach
    public void setup(){

        //Starting date: 2019/12/15   Due date:2020/12/10 Not Finished
        project=new Project();

        task1=new TaskAsPlanned(10, Role.JUNIOR_DEVELOPER,project,
                LocalDate.of(2020, 11, 1),LocalDate.of(2020, 11, 20));
        task1.getTaskInExecution().setHoursUsed(10);
        task1.getTaskInExecution().setExecutionRate(1d);
        task1.getTaskInExecution().setFinishedBy(LocalDate.of(2020,11,21));
        project.addTask(task1);

        task2=new TaskAsPlanned(5, Role.SENIOR_DEVELOPER,project,
                LocalDate.of(2020, 11, 15),LocalDate.of(2020, 11, 25));
        task2.getTaskInExecution().setHoursUsed(10);
        task2.getTaskInExecution().setExecutionRate(0.5d);
        project.addTask(task2);


        task3 =new TaskAsPlanned(15, Role.JUNIOR_ANALYST,project,
                LocalDate.of(2019, 12, 15),LocalDate.of(2020, 12, 10));
        project.addTask(task3);




    }

    @Test
    void onTime() {
        //true (still in Planning)
        assertTrue(project.onTime(LocalDate.now()));

        project.setProjectState(ProjectState.PLANNED);
        assertTrue(project.onTime(LocalDate.now()));

        project.setProjectState(ProjectState.ONGOING_EXECUTION);
        assertTrue(project.onTime(LocalDate.of(2019, 12, 4)));
        assertFalse(project.onTime(LocalDate.of(2019, 12, 16)));
        assertFalse(project.onTime(LocalDate.of(2022, 12, 16)));

        /*task 1 terminou fora do prazo da tarefa mas
        dentro do prazo do projecto, task2 terminou dentro do prazo
        task 3 (tarefa que acaba mais tarde, terminou dentro do prazo)
        */
        task2.getTaskInExecution().setExecutionRate(1d);
        task2.getTaskInExecution().setFinishedBy(LocalDate.of(2020,11,24));
        task3.getTaskInExecution().setExecutionRate(1d);
        task3.getTaskInExecution().setFinishedBy(LocalDate.of(2020, 12, 9));
        assertTrue(project.onTime(LocalDate.now()));


    }

    @Test
    void getEstimatedProjectCost() {
        assertEquals(600,project.getEstimatedProjectCost());
    }

    @Test
    void getStartingDate() {
        assertEquals(LocalDate.of(2019, 12, 15), project.getStartingDate());
    }

    @Test
    void getDueDate() {
        assertEquals(LocalDate.of(2020, 12, 10),project.getDueDate() );
    }

    @Test
    void getProjectExecutionRate() {
        assertEquals((float)0.5, project.getProjectExecutionRate());
    }

    @Test
    void getCurrentProjectCost() {
        assertEquals(500, project.getCurrentProjectCost());
    }

    @Test
    void getProjectState(){
        assertEquals(ProjectState.ONGOING_PLANNING, project.getProjectState());


   }

    @Test
    void setProjectState() {
        /*The Initial Project State is ONGOING_PLANNING,
        * From this state the Project State can only change
        * to PLANNED or DROPPED first we test the Changes
        * that are not allowed
        * */

        //From Planning to Ongoing Execution
       /* try { project.setProjectState(ProjectState.ONGOING_EXECUTION);}
        catch (ProjectChangeStateToOngoingExecution e){
            assertEquals(ProjectState.ONGOING_PLANNING, project.getProjectState());
        }*/

       assertThrows(ProjectChangeStateToOngoingExecution.class, ()->project.setProjectState(ProjectState.ONGOING_EXECUTION));

        //From Planning to Finished
        try { project.setProjectState(ProjectState.FINISHED);}
        catch (ProjectChangeStateToFinished e){
            assertEquals(ProjectState.ONGOING_PLANNING, project.getProjectState());
        }

        //Change from PLANNED to PLANNED
        project.setProjectState(ProjectState.PLANNED);
        assertEquals(ProjectState.PLANNED, project.getProjectState());

        /*From PLANNED the project state can only be changed to ONGOING_EXECUTION
        * or DROPPED we will test state changes that are not allowed
        * */

        //From Planned to Finished
        try { project.setProjectState(ProjectState.FINISHED);}
        catch (ProjectChangeStateToFinished e){
            assertEquals(ProjectState.PLANNED, project.getProjectState());
        }

        //From Planned to Ongoing Planning
        try { project.setProjectState(ProjectState.ONGOING_PLANNING);}
        catch (ProjectChangeStateToOngoingPlanning e){
            assertEquals(ProjectState.PLANNED, project.getProjectState());
        }

        //Change from a state to itself (same code for all states)
        project.setProjectState(ProjectState.PLANNED);
        assertEquals(ProjectState.PLANNED, project.getProjectState());

        //From Planned to Ongoing Execution
        project.setProjectState(ProjectState.ONGOING_EXECUTION);
        assertEquals(ProjectState.ONGOING_EXECUTION, project.getProjectState());

        /*From Ongoing_Execution the project state can only be changed to Finished
         *(if all tasks are finished (execution rate == 1))
         * or DROPPED we will test state changes that are not allowed
         * */

        //From Ongoing_Execution to Ongoing_Planning
        try { project.setProjectState(ProjectState.ONGOING_PLANNING);}
        catch (ProjectChangeStateToOngoingPlanning e){
            assertEquals(ProjectState.ONGOING_EXECUTION, project.getProjectState());
        }

        //From Ongoing_Execution to Planned
        try { project.setProjectState(ProjectState.PLANNED);}
        catch (ProjectChangeStateToPlanned e){
            assertEquals(ProjectState.ONGOING_EXECUTION, project.getProjectState());
        }

        //From Ongoing_Execution to Finished (without the tasks being concluded)
        try { project.setProjectState(ProjectState.FINISHED);}
        catch (ProjectChangeStateToFinishedWithUnfinishedTasks e){
            assertEquals(ProjectState.ONGOING_EXECUTION, project.getProjectState());
        }

        task2.getTaskInExecution().setExecutionRate(1d);
        task3.getTaskInExecution().setExecutionRate(1d);

        //From Ongoing_Execution to Finished (with the tasks being concluded)
        project.setProjectState(ProjectState.FINISHED);
        assertEquals(ProjectState.FINISHED, project.getProjectState());


        /*From Finished the project state cant be changed*/

        //From Finished to Ongoing Planning
        try { project.setProjectState(ProjectState.ONGOING_PLANNING);}
        catch (ProjectChangeStateToOngoingPlanning e){
            assertEquals(ProjectState.FINISHED, project.getProjectState());
        }


        //From Finished to Planned
        try { project.setProjectState(ProjectState.PLANNED);}
        catch (ProjectChangeStateToPlanned e){
            assertEquals(ProjectState.FINISHED, project.getProjectState());
        }

        //From Finished to Ongoing Execution
        try { project.setProjectState(ProjectState.ONGOING_EXECUTION);}
        catch (ProjectChangeStateToOngoingExecution e){
            assertEquals(ProjectState.FINISHED, project.getProjectState());
        }

        //From Finished To Dropped
        try { project.setProjectState(ProjectState.DROPPED);}
        catch (ProjectChangeStateToDroppedFromFinished e){
            assertEquals(ProjectState.FINISHED, project.getProjectState());
        }

        Project p2 = new Project();

        //From Ongoing Planning to Dropped
        p2.setProjectState(ProjectState.DROPPED);
        assertEquals(ProjectState.DROPPED, p2.getProjectState());


    }


    @Test
    /**
     * Tests the onBudget method, this method only applies
     * to Projects undergoing Execution or already Finished
     */
    void onBudget() {
        //If in any other state the sate will be changed to ongoing execution for testing purposes only
        try {project.onBudget();}
        catch (ProjectStateNotInExecutionOrFinished e){
            if(project.getProjectState().equals(ProjectState.ONGOING_PLANNING)){
                project.setProjectState(ProjectState.PLANNED);
                project.setProjectState(ProjectState.ONGOING_EXECUTION);
            }
            else if(project.getProjectState().equals(ProjectState.PLANNED)){
                project.setProjectState(ProjectState.ONGOING_EXECUTION);
            }
        }
        //project.setProjectToPlanned();
        assertFalse(project.onBudget());
    }


}