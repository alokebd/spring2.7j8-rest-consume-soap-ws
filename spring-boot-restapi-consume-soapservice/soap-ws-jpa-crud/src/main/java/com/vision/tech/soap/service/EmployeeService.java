package com.vision.tech.soap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vision.tech.soap.entities.Employee;
import com.vision.tech.soap.exception.EmployeeNotFoundException;
import com.vision.tech.soap.repo.EmployeeRepository;

@Service
public class EmployeeService implements IEmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Employee getEmployeeById(long employeeId) {
		Employee employee = employeeRepository.findByEmployeeId(employeeId);
		if (employee == null) {
			throw new EmployeeNotFoundException("Invalid employee Id:" +employeeId);
		}
		return employee;

	}

	@Override
	public void addEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	@Override
	public void updateEmployee(Employee employee) {
		Employee emp = employeeRepository.findByEmployeeId(employee.getEmployeeId());
		if (emp == null) {
			throw new EmployeeNotFoundException("Employee not present");
		}
		employeeRepository.save(employee);

	}

	@Override
	public void deleteEmployee(long employeeId) {
		Employee employee = employeeRepository.findByEmployeeId(employeeId);
		if (employee == null) {
			throw new EmployeeNotFoundException("Invalid employee Id: " +employeeId);
		}
		employeeRepository.deleteById(employeeId);
	}

	@Override
	public List<Employee> getAllEmployee() {
		return employeeRepository.findAll();
	}

}
