package pt.ufp.edu.projecttracker.api.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ufp.edu.projecttracker.model.Role;

@Data
@NoArgsConstructor
public class EmployeeDTOResponse {

    private String name;
    private String email;
    private Role role;
    private Integer numberOfTasks;
    private Integer numberOfOngoingTasks;

    public EmployeeDTOResponse(String name, String email, Role role, Integer numberOfTasks, Integer numberOfOngoingTasks) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.numberOfTasks = numberOfTasks;
        this.numberOfOngoingTasks = numberOfOngoingTasks;
    }
}
