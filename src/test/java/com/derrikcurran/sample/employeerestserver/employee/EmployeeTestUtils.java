package com.derrikcurran.sample.employeerestserver.employee;

public class EmployeeTestUtils {

    static Employee makeEmployee(String firstName, EmployeeStatus status) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setStatus(status);
        return employee;
    }
}
