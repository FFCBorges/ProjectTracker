package pt.ufp.edu.projecttracker.model;

import lombok.*;

import javax.persistence.*;


@NoArgsConstructor
@Data
@EqualsAndHashCode( onlyExplicitlyIncluded = true)
@MappedSuperclass

public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userID;

    @EqualsAndHashCode.Include
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }



}
