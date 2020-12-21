package pt.ufp.edu.projecttracker.api.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ufp.edu.projecttracker.model.ProjectState;

@Data
@NoArgsConstructor
public class ProjectDTOResponse {

    private String name;
    private Long projectManagerID;
    private Long clientID;
    private String projectDescription;
    private ProjectState state;
    private Integer numberOfTasks;
}
