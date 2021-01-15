package pt.ufp.edu.projecttracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ufp.edu.projecttracker.api.request.EmployeeDTO;
import pt.ufp.edu.projecttracker.api.response.EmployeeDTOResponse;
import pt.ufp.edu.projecttracker.model.Employee;
import pt.ufp.edu.projecttracker.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("")
    public void createEmployee(@RequestBody EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setRole(employeeDTO.getRole());
        employee.setPassword(employeeDTO.getPassword());
        employeeService.createEmployee(employee);
    }

    @GetMapping("/{id}")
    public EmployeeDTOResponse getClientByID(@PathVariable("id") Long id){
        Employee employee = employeeService.getEmployeeByID(id);
        EmployeeDTOResponse response = new EmployeeDTOResponse();
        response.setName(employee.getName());
        response.setEmail(employee.getEmail());
        response.setRole(employee.getRole());
        response.setNumberOfTasks(employee.getTasks().size());
        response.setNumberOfOngoingTasks(employee.getNumberOfOngoingTasks());
        return response;

    }

    @GetMapping
    public List<EmployeeDTOResponse> getAllEmployees(){
        Iterable<Employee> employees= employeeService.getAllEmployees();
        List<EmployeeDTOResponse> employeeDTOList = new ArrayList<>();
        for(Employee e:employees){
            employeeDTOList.add(new EmployeeDTOResponse(e.getName(),e.getEmail(),e.getRole(),e.getTasks().size(),e.getNumberOfOngoingTasks()));
        }

        return employeeDTOList;
    }

}
