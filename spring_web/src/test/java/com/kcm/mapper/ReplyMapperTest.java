package com.kcm.mapper;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.*;

import com.kcm.dto.Criteria;
import com.kcm.dto.Reply;
import com.kcm.dto.ReplyVo;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations= {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class ReplyMapperTest {

	private static final Logger log = LoggerFactory.getLogger("ReplyMapperTest.class");
	
	@Autowired
	private ReplyMapper mapper;
	
//	@Test
//	public void test() {
//
//		Reply r = new Reply();
//		r.setComment("안녕하세요");
//		r.setBoardNo("50");
//		r.setId("joy");
//		
//		mapper.insert(r);
//		
//	}
	
//	@Test
//	public void testList() {
//		Criteria cri = new Criteria(1, 5);
//		List<ReplyVo> list = mapper.getList(cri, 51L);
//		for(ReplyVo r : list) {
//			log.info("댓글내용 : " + r.getContent());
//		}
//	}
//	
	@Test
	public void testupdate() {
		
		ReplyVo vo = new ReplyVo(); //생성자 호출하고
		vo.setSeqno(45L); //아무거나 댓글 seqno있는것
		vo.setContent("댓글 수정합니다");
		int count = mapper.update(vo);
		log.info("update count : " + count);
	}
	
	@Test
	public void testdelete() {
		
		Long rno = 45L;
		int count = mapper.delete(rno);
		log.info("delete : " + count);
	}
	
}








