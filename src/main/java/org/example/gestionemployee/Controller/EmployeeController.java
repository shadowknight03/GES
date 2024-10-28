package org.example.gestionemployee.Controller;

import org.example.gestionemployee.Entity.Employee;
import org.example.gestionemployee.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
        Locale locale = LocaleContextHolder.getLocale();
        String result = employeeService.addEmployee(employee, locale);
        if (result.equals(employeeService.messageSource.getMessage("employee.add.success", null, locale))) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } else if (result.equals(employeeService.messageSource.getMessage("employee.email.exists", null, locale))) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    // READ: Get an employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployee(@PathVariable Long id) {
        Locale locale = LocaleContextHolder.getLocale();
        Optional<Employee> employee = employeeService.findEmployee(id);

        // If employee is found, return it; otherwise, return a localized "not found" message
        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        } else {
            String errorMessage = employeeService.messageSource.getMessage("employee.not.found", null, locale);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateEmployee(@RequestBody Employee employee) {
        Locale locale = LocaleContextHolder.getLocale();
        String result = employeeService.updateEmployee(employee, locale);
        if (result.equals(employeeService.messageSource.getMessage("employee.update.success", null, locale))) {
            return ResponseEntity.ok(result);
        } else if (result.equals(employeeService.messageSource.getMessage("employee.not.found", null, locale))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else if (result.equals(employeeService.messageSource.getMessage("employee.email.exists", null, locale))) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        Locale locale = LocaleContextHolder.getLocale();
        String result = employeeService.deleteEmployee(id, locale);
        if (result.equals(employeeService.messageSource.getMessage("employee.delete.success", null, locale))) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }
}
