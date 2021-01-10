package pt.ufp.edu.projecttracker.api.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ufp.edu.projecttracker.model.Role;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskAsPlannedDTO {

    @NotNull
    private String title;
    private String description;
    @NotNull
    private Integer estimatedHours;
    @NotNull
    private Role employeeType;
    @NotNull
    private Long projectID;
    private Long employeeID;
    @NotNull
    private LocalDate plannedStartDate;
    @NotNull
    private LocalDate plannedDueDate;

}
