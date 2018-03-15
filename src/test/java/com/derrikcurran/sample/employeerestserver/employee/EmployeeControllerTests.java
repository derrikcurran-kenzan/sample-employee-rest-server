package com.derrikcurran.sample.employeerestserver.employee;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static com.derrikcurran.sample.employeerestserver.employee.EmployeeTestUtils.makeEmployee;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmployeeController employeeController;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void getAllActiveEmployees_respondsWithEmployees() throws Exception {
        Employee employee1 = makeEmployee("John", EmployeeStatus.ACTIVE);
        Employee employee2 = makeEmployee("Jane", EmployeeStatus.ACTIVE);

        List<Employee> activeEmployees = Arrays.asList(employee1, employee2);

        given(employeeController.getAllActiveEmployees()).willReturn(activeEmployees);

        mvc.perform(get("/employees")
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].FirstName", is(employee1.getFirstName())))
                .andExpect(jsonPath("$[1].FirstName", is(employee2.getFirstName())));
    }

    @Test
    @WithMockUser
    public void deactivateEmployee_callsEmployeeServiceDeactivateById() throws Exception {
        mvc.perform(delete("/employees/123"));
        verify(employeeService, times(1)).deactivateById(123L);
    }
}
