package pt.ufp.edu.projecttracker.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
public class Employee extends User {


    @Enumerated
    @Column(nullable = false, columnDefinition = "smallint")
    private Role role;

    @OneToMany(mappedBy = "employee")
    private List<TaskAsPlanned> tasks = new ArrayList<>();


    public Employee(String name, String email, String password, Role role) {
        super(name, email, password);
        this.role=role;
    }


    public int getRoleRate(){
        if(role.equals(Role.JUNIOR_DEVELOPER)){
            return 10;
        }
        if(role.equals(Role.JUNIOR_ANALYST)){
            return 20;
        }
        if(role.equals(Role.SENIOR_DEVELOPER)){
            return 40;
        }

            return 80;


    }


    public TaskAsPlanned getTask(Long taskID){

        for (TaskAsPlanned task:tasks) {
            if (task.getId().equals(taskID)) return task;
        }
        return null;
    }


    public void addTask(TaskAsPlanned task){
        this.tasks.add(task);
    }

    public Integer getNumberOfOngoingTasks(){
        Integer ongoingTasks=0;
        for (TaskAsPlanned t:getTasks()) {
            if (t.getTaskExecutionRate() != 1d && !t.getProject().getProjectState().equals(ProjectState.DROPPED)) {
                ongoingTasks++;
            }
        }
        return ongoingTasks;
    }

}
