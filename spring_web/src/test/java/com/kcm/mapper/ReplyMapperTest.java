package com.kcm.mapper;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.*;

import com.kcm.dto.Reply;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations= {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class ReplyMapperTest {

	private static final Logger log = LoggerFactory.getLogger("ReplyMapperTest.class");
	
	@Autowired
	private ReplyMapper mapper;
	
	@Test
	public void test() {

		Reply r = new Reply();
		r.setComment("안녕하세요");
		r.setBoardNo("50");
		r.setId("joy");
		
		mapper.insert(r);
		
	}

}








