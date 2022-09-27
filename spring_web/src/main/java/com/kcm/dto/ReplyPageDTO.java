package com.kcm.dto;

import java.util.List;

public class ReplyPageDTO {
	private int replyCnt;
	private List<ReplyVo> list;
	
	public ReplyPageDTO(int replyCnt, List<ReplyVo> list) {
		this.replyCnt = replyCnt;
		this.list = list;
	}

	public int getReplyCnt() {
		return replyCnt;
	}

	public void setReplyCnt(int replyCnt) {
		this.replyCnt = replyCnt;
	}

	public List<ReplyVo> getList() {
		return list;
	}

	public void setList(List<ReplyVo> list) {
		this.list = list;
	}
	
	
	
}
