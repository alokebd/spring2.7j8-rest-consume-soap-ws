package com.vision.tech.rest.api.clinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import com.vision.tech.soap.employees.AddEmployeeRequest;
import com.vision.tech.soap.employees.AddEmployeeResponse;
import com.vision.tech.soap.employees.DeleteEmployeeRequest;
import com.vision.tech.soap.employees.DeleteEmployeeResponse;
import com.vision.tech.soap.employees.GetAllEmployeeRequest;
import com.vision.tech.soap.employees.GetAllEmployeeResponse;
import com.vision.tech.soap.employees.GetEmployeeByIdRequest;
import com.vision.tech.soap.employees.GetEmployeeResponse;
import com.vision.tech.soap.employees.UpdateEmployeeRequest;
import com.vision.tech.soap.employees.UpdateEmployeeResponse;

@Service
public class SoapClinet {

	@Autowired
	private Jaxb2Marshaller marshaller;

	private WebServiceTemplate template;

	private String SOAP_URI = "http://localhost:2026/visionService";

	public AddEmployeeResponse addEmployee(AddEmployeeRequest request) {
		template = new WebServiceTemplate(marshaller);
		AddEmployeeResponse response = (AddEmployeeResponse) template.marshalSendAndReceive(SOAP_URI, request);
		return response;
	}

	public GetEmployeeResponse getEmployee(GetEmployeeByIdRequest request) {
		template = new WebServiceTemplate(marshaller);
		GetEmployeeResponse response = (GetEmployeeResponse) template.marshalSendAndReceive(SOAP_URI, request);
		return response;
	}

	public UpdateEmployeeResponse updateEmployee(UpdateEmployeeRequest request) {
		template = new WebServiceTemplate(marshaller);
		UpdateEmployeeResponse response = (UpdateEmployeeResponse) template.marshalSendAndReceive(SOAP_URI, request);
		return response;
	}

	public DeleteEmployeeResponse deleteEmployee(DeleteEmployeeRequest request) {
		template = new WebServiceTemplate(marshaller);
		DeleteEmployeeResponse response = (DeleteEmployeeResponse) template.marshalSendAndReceive(SOAP_URI, request);
		return response;
	}
	
	public GetAllEmployeeResponse getAllEmployees(GetAllEmployeeRequest request) {
		template = new WebServiceTemplate(marshaller);
		GetAllEmployeeResponse response = (GetAllEmployeeResponse) template.marshalSendAndReceive(SOAP_URI, request);
		return response;
	}

}
