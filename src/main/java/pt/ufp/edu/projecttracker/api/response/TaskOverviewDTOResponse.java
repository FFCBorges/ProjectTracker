package pt.ufp.edu.projecttracker.api.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ufp.edu.projecttracker.model.Role;
import pt.ufp.edu.projecttracker.model.TaskAsPlanned;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TaskOverviewDTOResponse {

    private Long id;
    private String title;
    private Long projectID;
    private String projectManager;
    private String employee;
    private Role employeeRole;
    private LocalDate plannedStartDate;
    private LocalDate plannedDueDate;
    private Integer estimatedHours;
    private Integer estimatedCost;
    private Integer hoursUsed;
    private Integer currentCost;
    private Double executionRate;
    private String timeStatus;
    private String budgetStatus;
    private Double budgetDeviation;


    public TaskOverviewDTOResponse(TaskAsPlanned task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.projectID = task.getProject().getId();
        this.projectManager = task.getProject().getProjectManager().getName();
        if (task.getEmployee() != null) {
            this.employee = task.getEmployee().getName();
        }
        this.employeeRole = task.getEmployeeType();
        this.plannedStartDate = task.getPlannedStartDate();
        this.plannedDueDate = task.getPlannedDueDate();
        this.estimatedHours = task.getEstimatedHours();
        this.estimatedCost = task.getEstimatedTaskCost();
        this.hoursUsed = task.getTaskInExecution().getHoursUsed();
        this.currentCost = task.getCurrentTaskCost();
        this.executionRate = task.getTaskExecutionRate();

        if (task.onTime()) {
            this.timeStatus = "On Schedule";
        } else {
            this.timeStatus = "Delayed";
        }
        String budget = "";
        double budgetDeviation = -(this.estimatedCost * this.executionRate) - (this.currentCost);
        if (budgetDeviation == 0) {

            this.budgetStatus = "On Budget";
        } else if (budgetDeviation > 0) {

            this.budgetStatus = "Under Budget by " + budgetDeviation;
        } else {
            this.budgetStatus = "Over Budget by " + budgetDeviation;
        }
    }
}


