package pt.ufp.edu.projecttracker.api.response;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectManagerDTOResponse {

    private String name;
    private String email;
    private Integer totalNumberOfProjects;
    private Integer numberOfProjectsInExecution;

    public ProjectManagerDTOResponse (String name, String email, Integer totalNumberOfProjects, Integer numberOfProjectsInExecution){
        this.name=name;
        this.email=email;
        this.totalNumberOfProjects=totalNumberOfProjects;
        this.numberOfProjectsInExecution=numberOfProjectsInExecution;

    }
}
