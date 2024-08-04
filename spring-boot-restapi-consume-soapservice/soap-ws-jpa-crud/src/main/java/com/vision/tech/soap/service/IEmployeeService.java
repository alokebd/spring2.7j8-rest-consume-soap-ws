package com.vision.tech.soap.service;

import com.vision.tech.soap.entities.Employee;

public interface IEmployeeService {
	void AddEmployee(Employee employee);

	Employee getEmployeeById(long employeeId);

	void updateEmployee(Employee employee);

	void deleteEmployee(long employeeId);
}
