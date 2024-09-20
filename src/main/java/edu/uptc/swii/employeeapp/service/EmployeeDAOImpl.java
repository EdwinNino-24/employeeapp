package edu.uptc.swii.employeeapp.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.uptc.swii.employeeapp.model.Employee;

public class EmployeeDAOImpl implements IEmployeeDAO {

    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/employee?useSSL=false&allowPublicKeyRetrieval=true";

    @Override
    public List<Employee> findAll() {
        return this.getEmployees();
    }

    @Override
    public Employee findById(String id) {
        return this.getEmployee(id);
    }

    @Override
    public boolean save(Employee employee) {
        boolean res = false;
        Employee existingEmployee = this.getEmployee(employee.getId());
        if (existingEmployee.getId() != null) {
            res = this.executeQuery("UPDATE employee SET name='" + employee.getName()
                    + "', email='" + employee.getEmail() + "', phone='" + employee.getPhone() + "' WHERE id='"
                    + employee.getId() + "';");
        } else {
            res = this.executeQuery("INSERT INTO employee (id, name, email, phone) VALUES('"
                    + employee.getId() + "','" + employee.getName() + "','" + employee.getEmail() + "','"
                    + employee.getPhone() + "');");
        }
        return res;
    }

    @Override
    public boolean deleteEmployeeById(String id) {
        boolean res = false;
        if (this.getEmployee(id).getId() != null) {
            res = this.executeQuery("DELETE FROM employee WHERE id='" + id + "';");
        }
        return res;
    }

    private boolean executeQuery(String query) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement stmt = connection.createStatement()) {
            Class.forName(DRIVER);
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
            return false;
        }
    }

    private Employee getEmployee(String id) {
        String query = "SELECT * FROM employee WHERE id='" + id + "';";
        Employee emp = new Employee();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            Class.forName(DRIVER);
            if (rs.next()) {
                emp.setId(rs.getString("id"));
                emp.setName(rs.getString("name"));
                emp.setEmail(rs.getString("email"));
                emp.setPhone(rs.getString("phone"));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        return emp;
    }

    private List<Employee> getEmployees() {
        String query = "SELECT * FROM employee;";
        List<Employee> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            Class.forName(DRIVER);
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getString("id"));
                employee.setName(rs.getString("name"));
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                list.add(employee);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        return list;
    }
}