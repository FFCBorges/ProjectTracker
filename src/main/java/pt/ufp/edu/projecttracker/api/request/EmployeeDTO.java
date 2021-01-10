package pt.ufp.edu.projecttracker.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ufp.edu.projecttracker.model.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private String name;
    private String email;
    private String password;
    private Role role;
}
