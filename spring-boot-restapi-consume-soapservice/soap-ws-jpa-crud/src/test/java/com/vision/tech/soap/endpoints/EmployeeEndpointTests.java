package com.vision.tech.soap.endpoints;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.webservices.server.WebServiceServerTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;
import com.vision.tech.soap.entities.Employee;
import com.vision.tech.soap.repo.EmployeeRepository;
import com.vision.tech.soap.testutils.EmployeeTestUtils;
import javax.xml.transform.Source;
import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.payload;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.springframework.ws.test.server.ResponseMatchers.validPayload;
import static org.springframework.ws.test.server.ResponseMatchers.xpath;

import static org.mockito.Mockito.when;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;

@WebServiceServerTest
@ComponentScan("com.vision.tech.soap")
public class EmployeeEndpointTests {

	@Autowired
    private MockWebServiceClient client;

    @MockBean
    private EmployeeRepository employeeRepository;
    
    /*
	private static final Map<String, String> NAMESPACE_REQUEST_MAPPING = createDefaultMapping();

	private static Map<String, String> createDefaultMapping() {
		Map<String, String> mapping = new HashMap<>();
		mapping.put("emp", "http://www.tech.vision.com/soap/employees");
		return mapping;
	}*/

	private static final Map<String, String> NAMESPACE_RESPONSE_MAPPING = createNs2Mapping();

	private static Map<String, String> createNs2Mapping() {
		Map<String, String> mapping = new HashMap<>();
		mapping.put("ns2", "http://www.tech.vision.com/soap/employees");
		return mapping;
	}
					
	private Source getRequestPayloadByOperation(String oprationName, String name, long id, String department, String phone, String address) {
		Source requestPayload = new StringSource(
			      "<emp:"+oprationName
			      + " xmlns:emp=\"http://www.tech.vision.com/soap/employees\">"
		         + "<emp:employeeInfo>"
		         + "<emp:employeeId>"+id
		         + "</emp:employeeId>"
		         + "<emp:name>"+name
		         + "</emp:name>"
		         + "<emp:department>"+department
		         + "</emp:department>"
		         + "<emp:phone>"+phone
		         + "</emp:phone>"
		         + "<emp:address>"+address
		         + "</emp:address>"
		         + "</emp:employeeInfo>"
		         + "</emp:"+oprationName
		         + ">");		
		 return requestPayload;
	}
	
	private Source getSingleSuccessResponseByOprationPayload (String oprationName, String status, String message) {
		 Source responsePayload = new StringSource(
		           "<ns2:"+ oprationName
		           + " xmlns:ns2=\"http://www.tech.vision.com/soap/employees\">"
		         + "<ns2:serviceStatus>"
		         + "<ns2:status>"+status
		         + "</ns2:status>"
		         + "<ns2:message>"+message
		         + "</ns2:message>"
		         + "</ns2:serviceStatus>"
		         + "</ns2:"+oprationName
		         + " >");
		 return responsePayload;
	}
	
	
	@Test
	public void test_1_add_employee() {
		// Given
	 	long employeeId = 1;
    	String name = "James";
    	String department ="QA";
    	String phone = "647-256-1111";
    	String address = "399 FFF";
		String requestOperation = "addEmployeeRequest";
		Source requestPayload = getRequestPayloadByOperation(requestOperation, name, employeeId, department, phone, address);
		
		String responseOperation = "addEmployeeResponse";
		Source responsePayload = getSingleSuccessResponseByOprationPayload(responseOperation, "SUCCESS", "Content Added Successfully");

		Employee employee = EmployeeTestUtils.createEmployee(name, employeeId, department, phone, address);
		// When

		when(employeeRepository.save(employee)).thenReturn(employee);

		// Then
		client.sendRequest(withPayload(requestPayload)).andExpect(noFault());

		client.sendRequest(withPayload(requestPayload)).andExpect(payload(responsePayload));
	}
	
    @Test
	public void test_2_get_employee_by_id() throws IOException {
    	// Given
    	long employeeId = 2;
    	String name = "James";
    	String department ="QA";
    	String phone = "647-256-1111";
    	String address = "399 FFF";
    	String requestOperation = "addEmployeeRequest";
		Source requestPayload = getRequestPayloadByOperation(requestOperation, name, employeeId, department, phone, address);
		Employee employee = EmployeeTestUtils.createEmployee(name, employeeId, department, phone, address);
		// When
		when(employeeRepository.save(employee)).thenReturn(employee);
		//Then
		client.sendRequest(withPayload(requestPayload)).andExpect(noFault());
		// When
		when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(employee);
		
		//Given
    	String requestSearchOperation = "getEmployeeByIdRequest";
		Source requestSearchPayload = new StringSource(
			      "<emp:"+requestSearchOperation
			       + " xmlns:emp=\"http://www.tech.vision.com/soap/employees\">"
			       + " <emp:employeeId>"+ employeeId
			       + "</emp:employeeId>"
		           + "</emp:"+requestSearchOperation
		           + ">");		
		
		//Then	
		client.sendRequest(withPayload(requestSearchPayload))
        .andExpect(noFault())
        .andExpect(validPayload(new ClassPathResource("/employee.xsd")))
        .andExpect(xpath("/ns2:getEmployeeResponse/ns2:employeeInfo/ns2:name", NAMESPACE_RESPONSE_MAPPING).evaluatesTo(employee.getName()))
        .andExpect(xpath("/ns2:getEmployeeResponse/ns2:employeeInfo/ns2:employeeId", NAMESPACE_RESPONSE_MAPPING).evaluatesTo(employeeId));
	}

    @Test
    public void test_3_get_all_employees() throws IOException {
    	// Given
		 Source requestPayload = new StringSource(
		 "<emp:GetAllEmployeeRequest xmlns:emp=\"http://www.tech.vision.com/soap/employees\" />");		
		 long employeeId_1 = 3;
    	 String name_1 = "James";
    	 String department_1 ="QA";
    	 String phone_1 = "647-256-1111";
    	 String address_1 = "399 Main S";
    	 
    	 long employeeId_2 = 4;
    	 String name_2 = "ABC";
    	 String department_2 ="DEV";
    	 String phone_2 = "647-256-111";
    	 String address_2 = "1256 Young st";
		 Source responsePayload = new StringSource(
		           "<ns2:GetAllEmployeeResponse xmlns:ns2=\"http://www.tech.vision.com/soap/employees\" >"
		           + "<ns2:employeeInfo>"
		           + "<ns2:employeeId>"+employeeId_1
		           + "</ns2:employeeId>"
		           + "<ns2:name>"+name_1
		           + "</ns2:name>"
		           + "<ns2:department>"+department_1
		           + "</ns2:department>"
		           + "<ns2:phone>"+phone_1
		           + "</ns2:phone>"
		           + "<ns2:address>"+address_1
		           + "</ns2:address>"
		           + "</ns2:employeeInfo>"
		           + "<ns2:employeeInfo>"
		           + "<ns2:employeeId>"+employeeId_2
		           + "</ns2:employeeId>"
		           + "<ns2:name>"+name_2
		           + "</ns2:name>"
		           + "<ns2:department>"+department_2
		           + "</ns2:department>"
		           + "<ns2:phone>"+phone_2
		           + "</ns2:phone>"
		           + "<ns2:address>"+address_2
		           + "</ns2:address>"
		           + "</ns2:employeeInfo>"
		           + "</ns2:GetAllEmployeeResponse>");
		 
		  Employee employee  = EmployeeTestUtils.createEmployee(name_1, employeeId_1, department_1, phone_1, address_1);
		  
	      Employee employee2 = EmployeeTestUtils.createEmployee(name_2, employeeId_2, department_2, phone_2, address_2);
	      List<Employee> employees = new ArrayList<Employee>();
		  employees.add(employee);
		  employees.add(employee2);
		  
		  when(employeeRepository.findAll()).thenReturn(employees);

    	 //Then	 
		 client.sendRequest(withPayload(requestPayload))
         .andExpect(noFault())
         .andExpect(validPayload(new ClassPathResource("/employee.xsd")))
         .andExpect(xpath("/ns2:GetAllEmployeeResponse/ns2:employeeInfo[1]/ns2:name", NAMESPACE_RESPONSE_MAPPING)
        		 .evaluatesTo(employee.getName()))
         .andExpect(xpath("/ns2:GetAllEmployeeResponse/ns2:employeeInfo[2]/ns2:employeeId", NAMESPACE_RESPONSE_MAPPING)
        		 .evaluatesTo(employee2.getEmployeeId()))
         .andExpect(payload(responsePayload)
         );
    }
    
        
    @Test
	public void test_4_uployee_employee() throws IOException {
    	//Given
    	long employeeId = 5;
     	String name = "James";
     	String department ="QA";
     	String phone = "647-256-1111";
     	String address = "399 FFF";
    	Employee employee = EmployeeTestUtils.createEmployee(name, employeeId, department, phone, address);
    	
		// When
		when(employeeRepository.save(employee)).thenReturn(employee);
		//Then 
		String requestOperation = "addEmployeeRequest";
		Source requestPayload = getRequestPayloadByOperation(requestOperation, name, employeeId, department, phone, address);
		client.sendRequest(withPayload(requestPayload)).andExpect(noFault());
		
		
		String updated_name = "Test";
     	String updated_department ="Test";
 		Employee updatedEmplyee = EmployeeTestUtils.updateEmployee(updated_name, employeeId, updated_department, phone, address);
		
		// Given
    	String updatedOperation = "updateEmployeeRequest";
    	Source updateRequestPayload = getRequestPayloadByOperation(updatedOperation,updated_name, employeeId, updated_department,  phone, address);
		String oprationMessage = "Content Updated Successfully";
		String upateSucessOperation = "updateEmployeeResponse";
		Source updateResponsePayload = getSingleSuccessResponseByOprationPayload(upateSucessOperation, "SUCCESS", oprationMessage);
    	
    	// When
    	when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(updatedEmplyee);
    	
		// Then
    	client.sendRequest(withPayload(updateRequestPayload)).andExpect(noFault());
    	
    	client.sendRequest(withPayload(updateRequestPayload)).andExpect(payload(updateResponsePayload));
		
	}
    
    @Test
   	public void test_5_delete_employee() throws IOException {
       	//Given
       	long employeeId =6;
    	String name = "James";
    	String department ="QA";
    	String phone = "647-256-1111";
    	String address = "399 FFF";
       	Employee employee = EmployeeTestUtils.createEmployee(name, employeeId, department, phone, address);
       	
   		// When
   		when(employeeRepository.save(employee)).thenReturn(employee);
   		//Then 
   		String requestOperation = "addEmployeeRequest";
   		Source requestPayload = getRequestPayloadByOperation(requestOperation, name, employeeId, department, phone, address);
   		client.sendRequest(withPayload(requestPayload)).andExpect(noFault());
   		 
   		org.junit.jupiter.api.Assertions.assertAll(() -> employeeRepository.deleteById(employeeId));
   		
     	//Given
    	String requestDeleteOperation = "deleteEmployeeRequest";
		Source requestDeletePayload = new StringSource(
			      "<emp:"+requestDeleteOperation
			       + " xmlns:emp=\"http://www.tech.vision.com/soap/employees\">"
			       + " <emp:employeeId>"+ employeeId
			       + "</emp:employeeId>"
		           + "</emp:"+requestDeleteOperation
		           + ">");		
		//String oprationMessage = "Content Deleted Successfully";
		//String upateSucessOperation = "deleteEmployeeResponse";
		//Source deleteSuccessResponsePayload = getSingleSuccessResponseByOprationPayload(upateSucessOperation, "SUCCESS", oprationMessage);
		//lient.sendRequest(withPayload(requestDeletePayload)).andExpect(payload(deleteSuccessResponsePayload));
		
		
		 Source deleFaultteResponsePayload = new StringSource(
		           "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">"
		           + "<faultcode xmlns:ns0=\"http://www.tech.vision.com/soap/employees\">ns0:001_EMPLYEE_NOT_FOUND</faultcode>"
		           + "<faultstring xml:lang=\"en\">Invalid employee Id: "+employeeId
		           + "</faultstring>"
		           + "</SOAP-ENV:Fault>");
		
		client.sendRequest(withPayload(requestDeletePayload)).andExpect(payload(deleFaultteResponsePayload));
   	}
	

}
