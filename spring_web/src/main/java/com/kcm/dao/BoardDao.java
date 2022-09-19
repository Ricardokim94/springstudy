package com.kcm.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.kcm.dto.AttachFile;
import com.kcm.dto.Board;
import com.kcm.dto.Criteria;

public interface BoardDao {

	public List<Board> boardList(Criteria cri);
	
	Board boardDetail(String seqno);
	
	public String insert(Board board, AttachFile attach);
	
	void insertThumbNail(String attach_no, AttachFile attachFile);
	
	String insertAttachFile(String seqno, AttachFile attachFile);
	
	public void update(Board board, AttachFile attachFile);
	
	public int getTotalRec(Criteria cri);
	
	public Map<String, String> deleteByNo(String seqno);
}
