package com.kcm.controller.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kcm.dto.Criteria;
import com.kcm.dto.Reply;
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
				 produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> create(@RequestBody Reply reply){
		log.info("ReplyController create() called" + reply);
		int rs = service.register(reply);
		return rs == 1 ? new ResponseEntity<>("성공", HttpStatus.OK)
					: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
				
	@GetMapping(value="/list/{bno}/{page}",
				produces = {MediaType.APPLICATION_ATOM_XML_VALUE,
							MediaType.APPLICATION_JSON_UTF8_VALUE
				})
	public ResponseEntity<List<ReplyVo>> getList(
									@PathVariable("bno") Long bno,
									@PathVariable("page") int page){
		log.info("getList.....");
		Criteria cri = new Criteria(page, 5);
		
		service.getList(cri, bno);
		
		return null;
					
	}
	
}









