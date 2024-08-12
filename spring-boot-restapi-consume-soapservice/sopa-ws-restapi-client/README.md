# Spring Boot Rest API consume SOAP service
- Two Spring Boot services (1) SOAP api with JPA and (2) RESP API comsume the SOPA api in this demo.
- Project: Maven
- Application Framework: Spring Boot (2.7.1)
- Language: Java (8)
- Packaging: Jar
- Dependencies (Spring Web, Lombok, Spring Data JPA, Spring Devtools, jaxb2, MySQL db.
- NOTE: Recommand to use mvn command (mvn clean compile package) instead of IDE as Jaxb2 creates issue for loading.

## (1) Spring Boot SOAP API (soap-ws-jpa-crud)
- (1). Configure application.properties with db authentication.
- (2). Run the application (it is assign to port 2026)
## Load the WSDL
- Go to Chrome browser: http://localhost:2026/visionService/employees.wsdl (and save it /main/java/resources/wsdl).

## (2) Spring Boot REST API (soap-ws-respapi-client)
- NOTE: Make sure soap-ws-jpa-crud microservice is running. Added Postman collect, test following api
- POST: http://localhost:9090/api/employees (to add emplyee by json payload).
- GET: http://localhost:9090/api/employees (to get employee by employeeid with json payload).
- PUT: http://localhost:9090/api/employees (to update employee by json payload).
- DELETE: http://localhost:9090/api/employees (to delete employee by employeeid with json payload).