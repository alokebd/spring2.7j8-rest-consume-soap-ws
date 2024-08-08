package com.vision.tech.soap.testutils;

import org.springframework.beans.BeanUtils;

import com.vision.tech.soap.employees.AddEmployeeRequest;
import com.vision.tech.soap.employees.EmployeeInfo;
import com.vision.tech.soap.employees.UpdateEmployeeRequest;
import com.vision.tech.soap.entities.Employee;

public class EmployeeTestUtils {
	public static  EmployeeInfo createEmployeeInfo(String name, long id, String department, String phone, String address) {
		EmployeeInfo info = new EmployeeInfo();
		info.setName(name);
		info.setEmployeeId(id);
		info.setDepartment(department);
		info.setPhone(phone);
		info.setAddress(address);
		return info;
	}
	
	public static Employee createEmployee(String name, long id, String department, String phone, String address) {
		Employee employee = new Employee();
		AddEmployeeRequest request = new AddEmployeeRequest();
		EmployeeInfo info = createEmployeeInfo(name, id, department, phone, address);
		request.setEmployeeInfo(info);
		BeanUtils.copyProperties(request.getEmployeeInfo(), employee);
		return employee;
	}
	
	public static Employee updateEmployee(String name, long id, String department, String phone, String address) {
		Employee employee = new Employee();
		UpdateEmployeeRequest request = new UpdateEmployeeRequest();
		EmployeeInfo info = EmployeeTestUtils.createEmployeeInfo(name, id, department, phone, address);
		request.setEmployeeInfo(info);
		BeanUtils.copyProperties(request.getEmployeeInfo(), employee);
		return employee;
	}
}
