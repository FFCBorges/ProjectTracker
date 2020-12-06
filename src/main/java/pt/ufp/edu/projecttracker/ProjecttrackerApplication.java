package pt.ufp.edu.projecttracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjecttrackerApplication implements CommandLineRunner {



    public static void main(String[] args) {
        SpringApplication.run(ProjecttrackerApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

    }
}
