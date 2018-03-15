package com.derrikcurran.sample.employeerestserver.employee;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.derrikcurran.sample.employeerestserver.employee.EmployeeTestUtils.makeEmployee;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTests {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    public void deactivateById_setsEmployeeStatusToInactive() {
        // given
        Employee activeEmployee = makeEmployee("John", EmployeeStatus.ACTIVE);
        activeEmployee.setId(1L);
        when(employeeRepository.findById(activeEmployee.getId()))
            .thenReturn(Optional.of(activeEmployee));

        // when
        employeeService.deactivateById(activeEmployee.getId());
        Employee deactivatedEmployee = employeeRepository.findById(activeEmployee.getId()).get();

        // then
        assertThat(deactivatedEmployee.getStatus()).isEqualTo(EmployeeStatus.INACTIVE);
    }
}
