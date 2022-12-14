package com.kcm.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kcm.dto.Criteria;
import com.kcm.dto.Reply;
import com.kcm.dto.ReplyPageDTO;
import com.kcm.dto.ReplyVo;
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

	@Override
	public List<ReplyVo> getList(Criteria cri, Long bno) {
		
		return mapper.getList(cri, bno);
	}

	@Override
	public ReplyVo get(Long rno) {
		return mapper.read(rno);
	}

	@Override
	public int modify(ReplyVo vo) {
		return mapper.update(vo);
	}

	@Override
	public int remove(Long rno) {
		return mapper.delete(rno);
	}

	@Override
	public ReplyPageDTO getListPage(Criteria cri, Long bno) {
		return new ReplyPageDTO(
								mapper.getCountByBno(bno),
								mapper.getList(cri, bno));
	}

}
