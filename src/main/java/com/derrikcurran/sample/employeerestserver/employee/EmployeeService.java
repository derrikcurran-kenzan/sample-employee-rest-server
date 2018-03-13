package com.derrikcurran.sample.employeerestserver.employee;

import com.derrikcurran.sample.employeerestserver.common.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;


    public Iterable<Employee> findAllActive() {
        return repository.findAllByStatus(EmployeeStatus.ACTIVE);
    }

    public Optional<Employee> findActiveById(long id) {
        return repository.findByStatusAndId(EmployeeStatus.ACTIVE, id);
    }

    public void deactivateById(long id) {
        Optional<Employee> employee = repository.findById(id);
        if (employee.isPresent()) {
            employee.get().setStatus(EmployeeStatus.INACTIVE);
            repository.save(employee.get());
        } else {
            throw new ResourceNotFoundException();
        }
    }

    public Employee save(Employee employee) {
        return repository.save(employee);
    }
}
