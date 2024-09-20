package edu.uptc.swii.employeeapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.uptc.swii.employeeapp.model.Employee;
import edu.uptc.swii.employeeapp.service.EmployeeDAOImpl;
import edu.uptc.swii.employeeapp.service.IEmployeeDAO;

@RestController
public class AddEmployeeController {
    private IEmployeeDAO employeeDAO = new EmployeeDAOImpl();

    @PostMapping("/save")
    public void addEmployee(Employee employee) {
        this.employeeDAO.save(employee);
    }

    @RequestMapping("/findbyid")
    public Employee findByID(String id) {
        return this.employeeDAO.findById(id);
    }

    @RequestMapping("/findall")
    public List<Employee> findAll() {
        return this.employeeDAO.findAll();
    }

    @RequestMapping("/deletebyid")
    public void deleteEmployee(String id) {
        this.employeeDAO.deleteEmployeeById(id);
    }

}