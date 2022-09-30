package com.kcm.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration( locations= {"file:src/main/webapp/WEB-INF/spring/root-context.xml",
								   "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class MemberControllerTest {

	private static final Logger log = LoggerFactory.getLogger("MemberControllerTest.class");
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;  //스프링 서블릿에 대한 요청,응답을 처리
	
	@Before //junit 어노텐션임
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build(); 
		log.info("mokMvc setup..!");
	}
	
	@Test
	public void test() {
		try {
			String rs = mockMvc.perform(MockMvcRequestBuilders.post("/login")
					.param("id", "joy").param("pw", "1111")
					).andReturn().getModelAndView().getViewName();
			
			log.info(rs);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}








