package com.vision.tech.rest.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vision.tech.rest.api.clinet.SoapClinet;
import com.vision.tech.soap.employees.AddEmployeeRequest;
import com.vision.tech.soap.employees.AddEmployeeResponse;
import com.vision.tech.soap.employees.DeleteEmployeeRequest;
import com.vision.tech.soap.employees.DeleteEmployeeResponse;
import com.vision.tech.soap.employees.GetEmployeeByIdRequest;
import com.vision.tech.soap.employees.GetEmployeeResponse;
import com.vision.tech.soap.employees.UpdateEmployeeRequest;
import com.vision.tech.soap.employees.UpdateEmployeeResponse;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	private SoapClinet clinet;

	@PostMapping("/employees")
	public ResponseEntity<AddEmployeeResponse> placeOrder(@RequestBody AddEmployeeRequest request) {
		AddEmployeeResponse resposne = clinet.addEmployee(request);
		return new ResponseEntity<AddEmployeeResponse>(resposne, HttpStatus.OK);
	}

	@GetMapping("/employees")
	public ResponseEntity<GetEmployeeResponse> getEmployee(@RequestBody GetEmployeeByIdRequest request) {
		GetEmployeeResponse resposne = clinet.getEmployee(request);
		return new ResponseEntity<GetEmployeeResponse>(resposne, HttpStatus.OK);
	}

	@PutMapping("/employees")
	public ResponseEntity<UpdateEmployeeResponse> updateEmployee(@RequestBody UpdateEmployeeRequest request) {
		UpdateEmployeeResponse resposne = clinet.updateEmployee(request);
		return new ResponseEntity<UpdateEmployeeResponse>(resposne, HttpStatus.OK);
	}

	@DeleteMapping("/employees")
	public ResponseEntity<DeleteEmployeeResponse> deleteEmployee(@RequestBody DeleteEmployeeRequest request) {
		DeleteEmployeeResponse resposne = clinet.deleteEmployee(request);
		return new ResponseEntity<DeleteEmployeeResponse>(resposne, HttpStatus.OK);
	}

}
