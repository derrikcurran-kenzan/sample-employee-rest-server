package com.derrikcurran.sample.employeerestserver.employee;

import com.derrikcurran.sample.employeerestserver.common.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public Iterable<Employee> findAllActive() {
        return employeeRepository.findAllByStatus(EmployeeStatus.ACTIVE);
    }

    public Optional<Employee> findActiveById(long id) {
        return employeeRepository.findByStatusAndId(EmployeeStatus.ACTIVE, id);
    }

    public void deactivateById(long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            employee.get().setStatus(EmployeeStatus.INACTIVE);
            employeeRepository.save(employee.get());
        } else {
            throw new ResourceNotFoundException();
        }
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
}
