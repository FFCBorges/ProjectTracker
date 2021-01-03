package pt.ufp.edu.projecttracker.api.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
public class ProjectTimeDTOResponse {

        private String projectName;
        private LocalDate startDate;
        private LocalDate dueDate;
        private Integer manHours;

}
