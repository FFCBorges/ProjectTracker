package pt.ufp.edu.projecttracker.api.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ufp.edu.projecttracker.model.ProjectState;


/*
* Information to be added:
* Project Name x
* Total Budget x
* Total expenditure so far x
* % of execution x
* Project State x
* assessment if the project is on budget or not
* */


@Data
@NoArgsConstructor
public class ProjectValueDTOResponse {




        private String projectName;
        private Long plannedBudget;
        private Long expenditure;
        private Double executionRate;
        private ProjectState projectState;
        private String onTime;




}
