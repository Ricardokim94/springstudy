package com.kcm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kcm.dto.Criteria;
import com.kcm.dto.Reply;
import com.kcm.dto.ReplyVo;

public interface ReplyMapper {
	//인터페이스!
	public int insert(Reply reply);

	public List<ReplyVo> getList(
			@Param("cri")Criteria cri, 
			@Param("bno") Long bno);

	public ReplyVo read(Long rno);

	public int update(ReplyVo vo);
}
