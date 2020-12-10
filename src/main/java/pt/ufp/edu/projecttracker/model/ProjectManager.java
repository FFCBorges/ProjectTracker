package pt.ufp.edu.projecttracker.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name="managers_table")
public class ProjectManager extends User{

    @OneToMany(mappedBy = "projectManager")
    private List<Project> projects = new ArrayList<Project>();

    public ProjectManager(String nome, String email, String password) {
        super(nome, email, password);
    }



    public Project addProject(Project p){
        p.setProjectManager(this);
        getProjects().add(p);
        return p;
    }


    public Project getProject(String projectName){
        for (Project p: projects) {
            if(p.getName().equals(projectName)) return p;
        }
        return null;
    }

    public void removeProject(Project p){
        this.projects.remove(p);
        p.setProjectManager(null);
    }







}
