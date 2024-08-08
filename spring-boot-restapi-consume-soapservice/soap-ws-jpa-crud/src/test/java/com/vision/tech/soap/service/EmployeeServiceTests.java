package com.vision.tech.soap.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.springframework.boot.test.context.SpringBootTest;
import com.vision.tech.soap.entities.Employee;
import com.vision.tech.soap.exception.EmployeeNotFoundException;
import com.vision.tech.soap.repo.EmployeeRepository;
import com.vision.tech.soap.testutils.EmployeeTestUtils;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

	@Mock
	private EmployeeRepository repository;
	@InjectMocks
	private EmployeeService service;

	private String name = "James";
	private String department = "QA";
	private String phone = "647-256-1111";
	private String address = "399 FFF";

	@Test
	public void test_1_should_add_employee() {
		//Given
		long employeeId = 1;
		Employee employee = EmployeeTestUtils.createEmployee(name, employeeId, department, phone, address);

		// When
		service.addEmployee(employee);
		// Then
		verify(repository, times(1)).save(employee);
	}
	
	@Test
	public void test_2_should_get_employee_by_id() {
		//Given
		long employeeId = 2;
		Employee employee = EmployeeTestUtils.createEmployee(name, employeeId, department, phone, address);
		//When
		when (repository.findByEmployeeId(employeeId)).thenReturn(employee);
		Employee employeeDto = service.getEmployeeById(employeeId);
		//Then
		assertThat(employeeDto).isNotNull();
		assertThat(employeeDto.getName()).isEqualTo(name);
	}
	
	@Test
	public void test_3_should_update_employee() {
		//Given
		long employeeId = 3;
		Employee employee = EmployeeTestUtils.createEmployee(name, employeeId, department, phone, address);
		
		//When
		when (repository.findByEmployeeId(employeeId)).thenReturn(employee);
		employee.setName("Test");
		service.updateEmployee(employee);
		//Then
		assertAll(() -> service.updateEmployee(employee));
		
	}
	
	@Test 
	public void test_4_should_delete_employee (){
		// Given
		long employeeId =6;
    	String name = "James";
    	String department ="QA";
    	String phone = "647-256-1111";
    	String address = "399 FFF";
       	Employee employee = EmployeeTestUtils.createEmployee(name, employeeId, department, phone, address);
		// When
	   	repository.delete(employee);
	   	//Then
		//Mockito.doNothing().when(employee).setEmployeeId(employeeId);
		assertThatThrownBy(() -> service.deleteEmployee(employeeId)).isInstanceOf(EmployeeNotFoundException.class);
	}
	
	@Test 
	public void test_5_get_all_employees() {
		//Given
		long employeeId = 5;
		Employee employee = EmployeeTestUtils.createEmployee(name, employeeId, department, phone, address);
		//When
		when(repository.findAll()).thenReturn(Collections.singletonList(employee));
		List<Employee> serviceListDto = service.getAllEmployee();
		//Then
		assertThat(serviceListDto).isNotNull();
		assertThat(serviceListDto.size()).isEqualTo(1);
	}

}
