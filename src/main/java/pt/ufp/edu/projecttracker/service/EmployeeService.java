package pt.ufp.edu.projecttracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.EntityNotFoundException404;
import pt.ufp.edu.projecttracker.model.Employee;
import pt.ufp.edu.projecttracker.repositories.EmployeeRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class EmployeeService {


    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void createEmployee(Employee employee){

        employeeRepository.save(employee);
    }

    public Employee getEmployeeByID(Long id){
        return extractEmployeeByID(id);

    }

    public Employee extractEmployeeByID(Long id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()) return employee.get();
        throw new EntityNotFoundException404("Employee With That ID Was Not Found");

    }

    public Iterable<Employee> getAllEmployees(){
        return employeeRepository.findAll();


    }
}
