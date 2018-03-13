package com.derrikcurran.sample.employeerestserver.employee;

import com.derrikcurran.sample.employeerestserver.common.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public Iterable<Employee> getAllActiveEmployees() {
        return employeeService.findAllActive();
    }

    @GetMapping("{id}")
    public Employee getActiveEmployee(@PathVariable long id) {
        Optional<Employee> employee = employeeService.findActiveById(id);

        if (!employee.isPresent()) {
            throw new ResourceNotFoundException();
        }

        return employee.get();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateEmployee(@PathVariable long id) {
        try {
            employeeService.deactivateById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException();
        }
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.save(employee);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedEmployee.getId()).toUri();

        return ResponseEntity.created(location).body(savedEmployee);
    }

    @PutMapping("{id}")
    public Employee updateActiveEmployee(@PathVariable long id, @RequestBody Employee employee) {
        Optional<Employee> existingEmployee = employeeService.findActiveById(id);

        if (existingEmployee.isPresent()) {
            employee.setId(existingEmployee.get().getId());
            return employeeService.save(employee);
        } else {
            throw new ResourceNotFoundException();
        }
    }

}
