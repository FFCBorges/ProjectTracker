package pt.ufp.edu.projecttracker.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ufp.edu.projecttracker.model.Role;

@Data
@NoArgsConstructor
public class EmployeeDTO {

    private String name;
    private String email;
    private String password;
    private Role role;
}
