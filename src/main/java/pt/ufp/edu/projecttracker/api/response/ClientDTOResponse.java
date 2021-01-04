package pt.ufp.edu.projecttracker.api.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientDTOResponse {

    private String name;
    private String email;
    private Integer numberOfProjects;
    private Integer numberOfOngoingProjects;

    public ClientDTOResponse(String name, String email, Integer numberOfProjects, Integer numberOfOngoingProjects) {
        this.name = name;
        this.email = email;
        this.numberOfProjects = numberOfProjects;
        this.numberOfOngoingProjects = numberOfOngoingProjects;
    }
}
