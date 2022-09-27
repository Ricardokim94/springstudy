package com.kcm.controller.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kcm.dto.Criteria;
import com.kcm.dto.Reply;
import com.kcm.dto.ReplyPageDTO;
import com.kcm.dto.ReplyVo;
import com.kcm.service.ReplyService;

@RestController
@RequestMapping("/reply/")
public class ReplyController {

	private static final Logger log = LoggerFactory.getLogger(ReplyController.class);
	
	@Autowired
	ReplyService service;
	
	
	@PostMapping(value = "add",
				 consumes = "application/json",
				 produces = "text/plain; charset=utf-8")
	public ResponseEntity<String> create(@RequestBody Reply reply){
		log.info("ReplyController create() called" + reply);
		int rs = service.register(reply);
		return rs == 1 ? new ResponseEntity<>("댓글등록이 완료되었습니다", HttpStatus.OK)
					: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
				
	@GetMapping(value="/list/{bno}/{page}",
				produces = {MediaType.APPLICATION_ATOM_XML_VALUE,
							MediaType.APPLICATION_JSON_UTF8_VALUE
				})
	public ResponseEntity<ReplyPageDTO> getList(
									@PathVariable("bno") Long bno,
									@PathVariable("page") int page){
		log.info("getList.....");
		Criteria cri = new Criteria(page, 5);
		
		service.getList(cri, bno);
		
		return new ResponseEntity<>(service.getListPage(cri, bno),HttpStatus.OK);
					
	}
	
	
	@GetMapping(value="{rno}", 
				produces = {MediaType.APPLICATION_XML_VALUE,
							MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<ReplyVo> get(@PathVariable("rno") Long rno){
		log.info("get_reply : " + rno);
		return new ResponseEntity<>(service.get(rno), HttpStatus.OK);
	}
	
	
	@RequestMapping(method= {RequestMethod.PUT, RequestMethod.PATCH},
					value = "{rno}",
					produces = "text/plain; charset=utf-8")
	public ResponseEntity<String> modify(@PathVariable("rno") Long rno,
										 @RequestBody ReplyVo vo){
//		log.info("----------@@@@@@@@@@@@@@@@@@@@@@------------------------");
//		log.info("rno : " + vo.getSeqno());
//		log.info("content : " + vo.getContent());
		return service.modify(vo) == 1 ? new ResponseEntity<>("댓글 수정완료",HttpStatus.OK) :
										 new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping(value="{rno}", produces ="text/plain; charset=utf-8")
	public ResponseEntity<String> remove(@PathVariable("rno") Long rno){
		log.info("delete rno : " + rno);
		return service.remove(rno) == 1 ? new ResponseEntity<>("댓글 삭제 완료", HttpStatus.OK) :
										  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	

}







