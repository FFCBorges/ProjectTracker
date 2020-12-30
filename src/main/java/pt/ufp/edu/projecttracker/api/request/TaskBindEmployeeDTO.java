package pt.ufp.edu.projecttracker.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
public class TaskBindEmployeeDTO {
    @NotNull
    private long employeeID;

}


