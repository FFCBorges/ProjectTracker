package pt.ufp.edu.projecttracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ufp.edu.projecttracker.api.request.EmployeeDTO;
import pt.ufp.edu.projecttracker.api.response.EmployeeDTOResponse;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.EntityNotFoundException404;
import pt.ufp.edu.projecttracker.model.Employee;
import pt.ufp.edu.projecttracker.repositories.EmployeeRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {


    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void createEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setRole(employeeDTO.getRole());
        employee.setPassword(employeeDTO.getPassword());
        employeeRepository.save(employee);
    }

    public EmployeeDTOResponse getEmployeeByID(Long id){
        Employee employee=extractEmployeeByID(id);
        EmployeeDTOResponse response = new EmployeeDTOResponse();
        response.setName(employee.getName());
        response.setEmail(employee.getEmail());
        response.setRole(employee.getRole());
        response.setNumberOfTasks(employee.getTasks().size());
        response.setNumberOfOngoingTasks(employee.getNumberOfOngoingTasks());
        return response;

    }

    public Employee extractEmployeeByID(Long id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()) return employee.get();
        throw new EntityNotFoundException404("Employee With That ID Was Not Found");

    }

    public List<EmployeeDTOResponse> getAllEmployees(){
        Iterable<Employee> employees=employeeRepository.findAll();
        List<EmployeeDTOResponse> employeeDTOList = new ArrayList<>();
        for(Employee e:employees){
            employeeDTOList.add(new EmployeeDTOResponse(e.getName(),e.getEmail(),e.getRole(),e.getTasks().size(),e.getNumberOfOngoingTasks()));
        }

        return employeeDTOList;
    }
}
