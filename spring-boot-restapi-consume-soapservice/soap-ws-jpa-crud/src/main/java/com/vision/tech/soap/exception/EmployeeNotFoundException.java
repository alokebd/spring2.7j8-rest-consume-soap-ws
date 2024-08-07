package com.vision.tech.soap.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CUSTOM, customFaultCode = "{http://www.tech.vision.com/soap/employees}001_EMPLYEE_NOT_FOUND")
public class EmployeeNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	public EmployeeNotFoundException(String message) {
		super(message);
	}

}
