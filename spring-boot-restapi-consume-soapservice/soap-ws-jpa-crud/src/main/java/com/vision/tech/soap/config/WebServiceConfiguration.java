package com.vision.tech.soap.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfiguration extends WsConfigurerAdapter {
	
	/**
	 * Application DispatcherServlet for application communication. The WSDL URL (http://localhost:2026/visionService/employees.wsdl) and 
	 * REST API base URL (http://localhost:2026/visionService/).
	 * @param applicationContext
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/visionService/*");
	}
	
	@Bean(name = "employees")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema employeeSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("visionServiceSoapHttp");
		wsdl11Definition.setLocationUri("/visionService");
		//wsdl11Definition.setTargetNamespace("interfaces.soap.tech.vision.com");
		wsdl11Definition.setTargetNamespace("http://www.tech.vision.com/soap/employees");
		wsdl11Definition.setSchema(employeeSchema);
		return wsdl11Definition;
	}
	
	
	
	/**
	 * To genarate wsdl form supplied xsd 
	 * @return XsdSchema
	 */
	@Bean
	public XsdSchema employeeSchema() {
		return new SimpleXsdSchema(new ClassPathResource("employee.xsd"));
	}
	
}
