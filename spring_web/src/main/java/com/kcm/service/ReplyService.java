package com.kcm.service;

import java.util.List;

import com.kcm.dto.Criteria;
import com.kcm.dto.Reply;
import com.kcm.dto.ReplyVo;

public interface ReplyService {

	public int register(Reply reply) ;

	public List<ReplyVo> getList(Criteria cri, Long bno);

	public ReplyVo get(Long rno);

	public int modify(ReplyVo vo);
}
