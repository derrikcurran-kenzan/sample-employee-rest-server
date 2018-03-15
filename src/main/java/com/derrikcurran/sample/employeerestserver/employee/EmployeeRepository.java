package com.derrikcurran.sample.employeerestserver.employee;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Optional<Employee> findByStatusAndId(EmployeeStatus status, long id);
    Iterable<Employee> findAllByStatus(EmployeeStatus status);
}