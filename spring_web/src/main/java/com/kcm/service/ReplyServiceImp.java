package com.kcm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kcm.dto.Reply;
import com.kcm.mapper.ReplyMapper;

@Service
public class ReplyServiceImp implements ReplyService {
	
	private static final Logger log = LoggerFactory.getLogger(ReplyServiceImp.class);
	
	@Autowired
	private ReplyMapper mapper;
	
	@Override
	public int register(Reply reply) {
		log.info("reply register service calles..!!" + reply);
		return mapper.insert(reply); //int로 받기로 했으니까
	}

}
