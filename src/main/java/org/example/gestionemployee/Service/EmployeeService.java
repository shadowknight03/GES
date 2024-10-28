package org.example.gestionemployee.Service;

import org.example.gestionemployee.Entity.Employee;
import org.example.gestionemployee.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeerepo;
    public final MessageSource messageSource;
    @Autowired
    public EmployeeService(EmployeeRepository employeerepo, MessageSource messageSource) {
        this.employeerepo = employeerepo;
        this.messageSource = messageSource;
    }

    // Add Employee
    public String addEmployee(Employee employee, Locale locale) {
        if (employeerepo.findById(employee.getId()).isPresent()) {
            return messageSource.getMessage("employee.add.exists", null, locale);
        } else if (employeerepo.findByEmail(employee.getEmail()).isPresent()) {
            return messageSource.getMessage("employee.email.exists", null, locale);
        }
        try {
            employeerepo.save(employee);
            return messageSource.getMessage("employee.add.success", null, locale);
        } catch (Exception e) {
            e.printStackTrace();
            return messageSource.getMessage("employee.add.fail", null, locale);
        }
    }

    public String updateEmployee(Employee employee, Locale locale) {
        Optional<Employee> existingEmployeeOpt = employeerepo.findById(employee.getId());
        if (!existingEmployeeOpt.isPresent()) {
            return messageSource.getMessage("employee.not.found", null, locale);
        } else if (employeerepo.findByEmail(employee.getEmail()).isPresent()) {
            return messageSource.getMessage("employee.email.exists", null, locale);
        }
        try {
            Employee existingEmployee = existingEmployeeOpt.get();
            existingEmployee.setNom(employee.getNom());
            existingEmployee.setPrenom(employee.getPrenom());
            existingEmployee.setIsworking(employee.getIsworking());
            existingEmployee.setHasleft(employee.getHasleft());
            existingEmployee.setEmail(employee.getEmail());

            employeerepo.save(existingEmployee);
            return messageSource.getMessage("employee.update.success", null, locale);
        } catch (Exception e) {
            e.printStackTrace();
            return messageSource.getMessage("employee.update.fail", null, locale);
        }
    }

    // Find Employee by ID
    public Optional<Employee> findEmployee(Long id) {
        try {
            return employeerepo.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    // Delete Employee by ID
    public String deleteEmployee(Long id, Locale locale) {
        Optional<Employee> employeeOpt = employeerepo.findById(id);
        if (!employeeOpt.isPresent()) {
            return "Employee not found";
        }
        try {
            employeerepo.deleteById(id);
            return "Employee deleted successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to delete employee";
        }
    }
}
