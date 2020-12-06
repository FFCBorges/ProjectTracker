package pt.ufp.edu.projecttracker.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@ToString()
@Entity
@Table(name = "Tasks_In_Execution")

public class TaskInExecution {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private Integer hoursUsed;

    private Double executionRate;



}
