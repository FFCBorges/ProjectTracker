package pt.ufp.edu.projecttracker;


import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pt.ufp.edu.projecttracker.model.Client;
import pt.ufp.edu.projecttracker.model.Employee;
import pt.ufp.edu.projecttracker.model.Project;
import pt.ufp.edu.projecttracker.model.ProjectManager;
import pt.ufp.edu.projecttracker.model.Role;
import pt.ufp.edu.projecttracker.model.TaskAsPlanned;
import pt.ufp.edu.projecttracker.model.TaskInExecution;
import pt.ufp.edu.projecttracker.repositories.ClientRepository;
import pt.ufp.edu.projecttracker.repositories.EmployeeRepository;
import pt.ufp.edu.projecttracker.repositories.ProjectManagerRepository;
import pt.ufp.edu.projecttracker.repositories.ProjectRepository;
import pt.ufp.edu.projecttracker.repositories.TaskAsPlannedRepository;
import pt.ufp.edu.projecttracker.repositories.TaskInExecutionRepository;

import java.time.LocalDate;

@Component
public class Init implements ApplicationListener<ContextRefreshedEvent> {

    Logger logger= LoggerFactory.getLogger(this.getClass());


    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectManagerRepository projectManagerRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private TaskAsPlannedRepository taskAsPlannedRepository;
    @Autowired
    private TaskInExecutionRepository taskInExecutionRepository;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent){

        logger.info("\n\n\nInicializou\n\n\n");

        ProjectManager manager1=new ProjectManager("Antonio Manager", "antonio@gmail.com","passdoantonio");
        projectManagerRepository.save(manager1);


        ProjectManager manager2=new ProjectManager("Jose Manager", "jose@gmail.com","passdojose");
        projectManagerRepository.save(manager2);

        Client client1 = new Client("Sr Bernardinho", "Bernardino@gmail.com", "passdobernardino");
        clientRepository.save(client1);

        Client client2 = new Client("Sr Lopes da Silva", "lopesdasilva@gmail.com", "passdolopes");
        clientRepository.save(client2);

        Employee emp1= new Employee("Johny", "Johny@gmail.com","passdojohny", Role.JUNIOR_DEVELOPER);
        employeeRepository.save(emp1);
        Employee emp2= new Employee("Armando","armando@gmail.com","passdoarmando",Role.JUNIOR_ANALYST);
        employeeRepository.save(emp2);
        Employee emp3= new Employee("Cabral","cabral@gmail.com","passdocabral",Role.SENIOR_DEVELOPER);
        employeeRepository.save(emp3);
        Employee emp4= new Employee("Teresa","teresa@gmail.com","passdateresa",Role.SENIOR_ANALYST);
        employeeRepository.save(emp4);


        Project project1=new Project("Projecto 1" , manager1);
        project1.setProjectDesc("descrição do projecto 1");
        project1.setClient(client1);
        projectRepository.save(project1);

        Project project2=new Project("Projecto 2", manager2);
        //project2.setClient(client2);
        project2.setProjectDesc("descrição do projecto 2");
        projectRepository.save(project2);

        //Tarefas Planeadas para Projecto 1
        //ToDo averiguar restrições de datas. e.g. posso criar tarefas cuja data ja tenha terminado (sim para fisn de povoar a BD) posso criar tarefas cuja data de inicio seja depois da data de fim?
        TaskAsPlanned tAP11= new TaskAsPlanned();
        tAP11.setTitle("tarefa 1 do projecto 1");
        tAP11.setProject(project1);
        tAP11.setPlannedDueDate(LocalDate.of(2020,12,31));
        tAP11.setPlannedStartDate(LocalDate.of(2020, 12, 1));
        tAP11.setEmployeeType(Role.JUNIOR_DEVELOPER);
        tAP11.setEstimatedHours(20);
        taskAsPlannedRepository.save(tAP11);

        TaskAsPlanned tAP12= new TaskAsPlanned();
        tAP12.setTitle("tarefa 2 do projecto 1");
        tAP12.setProject(project1);
        tAP12.setPlannedDueDate(LocalDate.of(2021,1,2));
        tAP12.setPlannedStartDate(LocalDate.of(2021, 2, 1));
        tAP12.setEmployeeType(Role.SENIOR_DEVELOPER);
        tAP12.setEstimatedHours(5);
        taskAsPlannedRepository.save(tAP12);

        TaskAsPlanned tAP13= new TaskAsPlanned();
        tAP13.setTitle("tarefa 3 do projecto 1");
        tAP13.setProject(project1);
        tAP13.setPlannedDueDate(LocalDate.of(2021, 2, 1));
        tAP13.setPlannedStartDate(LocalDate.of(2021, 1, 20));
        tAP13.setEmployeeType(Role.JUNIOR_ANALYST);
        tAP13.setEstimatedHours(15);
        taskAsPlannedRepository.save(tAP13);

        TaskInExecution tIE11 = new TaskInExecution();
        tIE11.setPlannedTask(tAP11);
        taskInExecutionRepository.save(tIE11);

        TaskInExecution tIE12 = new TaskInExecution();
        tIE12.setPlannedTask(tAP12);
        taskInExecutionRepository.save(tIE12);

        TaskInExecution tIE13 = new TaskInExecution();
        tIE13.setPlannedTask(tAP13);
        taskInExecutionRepository.save(tIE13);







    }




}
