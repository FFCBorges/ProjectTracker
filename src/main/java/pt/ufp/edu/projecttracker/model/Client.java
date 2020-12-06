package pt.ufp.edu.projecttracker.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;



@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name="clients")
public class Client extends User{

    @OneToMany(mappedBy = "client")
    private  List<Project> projects = new ArrayList<>();

    public Client(String nome, String email, String password) {
        super(nome, email, password);
    }



    public void removeProject(Project p){
        getProjects().remove(p);
        p.setClient(null);
    }

}
