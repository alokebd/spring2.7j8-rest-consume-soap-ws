package com.vision.tech.soap.service;

import java.util.List;

import com.vision.tech.soap.entities.Employee;

public interface IEmployeeService {
	void addEmployee(Employee employee);

	Employee getEmployeeById(long employeeId);

	void updateEmployee(Employee employee);

	void deleteEmployee(long employeeId);
	List<Employee> getAllEmployee();
}
