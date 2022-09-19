package com.kcm.service;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.kcm.dto.Board;
import com.kcm.dto.Criteria;

public interface BoardService {

	public List<Board> list(Criteria cri); //dto에 있는거 import하면 된다.
	
	public Board searchBoard(String seqno);
	
	public String insertBoard(HttpServletRequest req, HttpServletResponse resp);
	
	public String insertBoard(Board board, MultipartFile files);		//register 쓰려고
	
	public String update(HttpServletRequest req, HttpServletResponse resp);

	public int getTotalRec(Criteria criteria);

	public void delete(String seqno);
	
}
