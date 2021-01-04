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

    public ProjectDTOResponse(String name, Long projectManagerID, Long clientID, String projectDescription, ProjectState state, Integer numberOfTasks) {
        this.name = name;
        this.projectManagerID = projectManagerID;
        this.clientID = clientID;
        this.projectDescription = projectDescription;
        this.state = state;
        this.numberOfTasks = numberOfTasks;
    }
}
