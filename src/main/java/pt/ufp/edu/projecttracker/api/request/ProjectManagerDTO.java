package pt.ufp.edu.projecttracker.api.request;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectManagerDTO {

    private String name;
    private String email;
    private String password;

//    public ProjectManagerDTO (String name, String email, String password){
//        this.name=name;
//        this.email=email;
//        this.password=password;
//    }

}
