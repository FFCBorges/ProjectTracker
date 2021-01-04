package pt.ufp.edu.projecttracker.api.response;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ProjectValueDTOResponse {

        private String projectName;
        private Integer plannedBudget;


}
