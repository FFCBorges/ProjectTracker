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
@ToString(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="Role", discriminatorType = DiscriminatorType.STRING)
public class Employee extends User {


    @Enumerated
    @Column(nullable = false, columnDefinition = "smallint")
    private Role role;

    @OneToMany(mappedBy = "employee")
    private List<TaskAsPlanned> plannedTasks = new ArrayList<>();


    public Employee(String name, String email, String password, Role role) {
        super(name, email, password);
        this.role=role;
    }

    public int getRoleRate(){
        if(role.equals(Role.JUNIOR_DEVELOPER)){
            return 10;
        }
        if(role.equals(Role.JUNIOR_ANALYST)){
            return 20;
        }
        if(role.equals(Role.SENIOR_DEVELOPER)){
            return 40;
        }
        if(role.equals(Role.SENIOR_ANALYST)){
            return 80;
        }
        return -1;
    }


    public static void main(String[] args) {
        Employee emp = new Employee("Joao","asdad@gmail.com","WWWWW",Role.JUNIOR_ANALYST);

        System.out.println(emp);


    }






}
