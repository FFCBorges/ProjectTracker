package pt.ufp.edu.projecttracker.api.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ufp.edu.projecttracker.model.ProjectState;





@Data
@NoArgsConstructor
public class ProjectValueDTOResponse {

        private String projectName;
        private Integer plannedBudget;
        private Integer expenditure;
        private Double executionRate;
        private ProjectState projectState;
        private String onTime;


}
