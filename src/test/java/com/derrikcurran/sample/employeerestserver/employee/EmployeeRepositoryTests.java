package com.derrikcurran.sample.employeerestserver.employee;

import org.assertj.core.util.IterableUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static com.derrikcurran.sample.employeerestserver.employee.EmployeeTestUtils.makeEmployee;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTests {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void findAllByStatus_returnsOnlyEmployeesWithGivenStatus() {
        // given
        em.persist(makeEmployee("John", EmployeeStatus.ACTIVE));
        em.persist(makeEmployee("Jane", EmployeeStatus.ACTIVE));
        em.persist(makeEmployee("Bob", EmployeeStatus.ACTIVE));
        em.persist(makeEmployee("Susan", EmployeeStatus.INACTIVE));
        em.persist(makeEmployee("Joe", EmployeeStatus.INACTIVE));
        em.flush();

        // when
        Collection<Employee> employees = IterableUtil.toCollection(
            employeeRepository.findAllByStatus(EmployeeStatus.INACTIVE)
        );

        // then
        assertThat(employees.size()).isEqualTo(2);
        for (Employee employee : employees) {
            assertThat(EmployeeStatus.INACTIVE.equals(employee.getStatus()));
        }
    }
}
