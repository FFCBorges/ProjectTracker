package pt.ufp.edu.projecttracker.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ProjectDTO {

    @NotBlank(message = "Project name cannot be blank")
    private String name;
    @NotNull
    //tipos primitivos de dado nao lidam con null
    private Long projectManagerID;
    private Long clientID;
    private String projectDesc;

}
