package pt.ufp.edu.projecttracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ufp.edu.projecttracker.api.request.EmployeeDTO;
import pt.ufp.edu.projecttracker.api.response.EmployeeDTOResponse;
import pt.ufp.edu.projecttracker.service.EmployeeService;

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
        employeeService.createEmployee(employeeDTO);
    }

    @GetMapping("/{id}")
    public EmployeeDTOResponse getClientByID(@PathVariable("id") Long id){
        return employeeService.getEmployeeByID(id);

    }

    @GetMapping
    public List<EmployeeDTOResponse> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

}
