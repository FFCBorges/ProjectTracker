package pt.ufp.edu.projecttracker.api.request;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class TaskInExecutionDTO {

    private Double executionRate;

    private Integer hoursUsed;

    @NotNull
    private Long taskAsPlannedID;

    private LocalDate finishedBy;



}
