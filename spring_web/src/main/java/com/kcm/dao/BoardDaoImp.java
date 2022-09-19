package com.kcm.dao;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kcm.common.OracleConn;
import com.kcm.dto.AttachFile;
import com.kcm.dto.Board;
import com.kcm.dto.Criteria;
import com.kcm.dto.Reply;
import com.kcm.dto.Thumbnail;

import oracle.jdbc.OracleType;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;


@Repository
public class BoardDaoImp implements BoardDao{  //데이터를 입출력하는 객체다 : Dao
			//DB컨넥션
	
	@Autowired
	private DataSource ds;/////////////////////
	
	private final Connection conn = OracleConn.getInstance().getConn();
	
	public List<Board> boardList(Criteria cri) {
		
		Connection conn = null;///////////////////////
		CallableStatement stmt = null;
		
		List<Board>board = new ArrayList<Board>();
			String search_title = null;
			String search_name = null;
			
			//제목검색
			if(cri.getSearchField() != null && cri.getSearchField().equals("title")) {
				search_title = cri.getSearchText();
			}
			//이름검색
			if(cri.getSearchField() != null && cri.getSearchField().equals("name")) {
				search_name = cri.getSearchText();
			}

		String sql = "call p_getboardlist(?,?,?,?,?)";
		try {
			conn = ds.getConnection();////////////////////
			stmt = conn.prepareCall(sql);
			stmt.setInt(1, cri.getCurrentPage());
			stmt.setInt(2, cri.getRowPerPage());
			stmt.setString(3, search_name);
			stmt.setString(4, search_title);
			stmt.registerOutParameter(5, OracleTypes.CURSOR);
			stmt.executeQuery();
			
			ResultSet rs = (ResultSet)stmt.getObject(5);
			
			while(rs.next()) {
				Board b =new Board(); //객체를 하나 만들어야됨!
				
				b.setNo(rs.getString("rn")); 
				b.setTitle(rs.getString("title"));
				b.setWdate(rs.getString("wdate"));
				b.setName(rs.getString("name"));
				b.setCount(rs.getString("count"));
				b.setSeqno(rs.getString("seqno"));

				board.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {///////////////////
			resourceClose(conn, stmt);
		}		
		return board;
	}

	public Board boardDetail(String seqno) {
		Connection conn = null;///////////////////////
		Board board = new Board();//보드를 try밖에다 해줘야함 안에다 하면 return 할때 모름
		CallableStatement stmt = null;
		try {
			//조회수 카운트
			conn= ds.getConnection();
			String sql = "call p_board_detail(?,?,?,?)";  
			stmt = conn.prepareCall(sql);
				//???? =>1번째 seqno /2번째 board /3번째 reply /4번째 attach
			stmt.setInt(1, Integer.parseInt(seqno));
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.executeQuery();

			ResultSet rs = (ResultSet)stmt.getObject(2);			
	   		rs.next(); 
	   		board.setSeqno(seqno);
	    	board.setTitle(rs.getString("title"));
	   		board.setContent(rs.getString("content"));
	   		board.setId(rs.getString("id"));
	   		board.setWdate(rs.getString("wdate"));
	   		board.setCount(rs.getString("count"));
	   		board.setName(rs.getString("name"));
	   		board.setOpen(rs.getString("open"));
	   		
	   		
	   		List<Reply> re = new ArrayList<Reply>();
	   		
	   		rs = (ResultSet)stmt.getObject(3);
   			while(rs.next()) {
	   			Reply reply = new Reply();
	   			reply.setId(rs.getString("id"));
	   			reply.setComment(rs.getString("content"));
	   			reply.setWdate(rs.getString("wdate"));
	   			reply.setName(rs.getString("name"));
	   			re.add(reply);
   			}
   			board.setReply(re);		//마지막이 re니까 이걸 보드에 담으면 됨
		   
   		//첨부파일 저장
			List<AttachFile> fileList = new ArrayList<AttachFile>();
					
			rs = (ResultSet)stmt.getObject(4);
			while(rs.next()) {
				AttachFile attachfile = new AttachFile();
				attachfile.setNo(rs.getString("no"));
				attachfile.setFileName(rs.getString("filename"));
				attachfile.setSaveFileName(rs.getString("savefilename"));
				attachfile.setFileSize(rs.getString("filesize"));
				attachfile.setFiletype(rs.getString("filetype"));
				attachfile.setFilePath(rs.getNString("filepath"));				
				System.out.println(attachfile.getFileName());
			//썸네일 담아서 attach파일에 set하고 ->	fileList에 담음
				Thumbnail th = new Thumbnail();	
				th.setFileName(rs.getString("thumb_name"));
				th.setFileSize(rs.getString("thumb_size"));
				th.setFilePath(rs.getString("thumb_path"));
				attachfile.setThumbnail(th);
			
				fileList.add(attachfile);
			}
				board.setAttachfile(fileList);
			   			
		} catch (SQLException e) {
				e.printStackTrace();
		}finally {///////////////////
			resourceClose(conn, stmt);
		}		
		return board;
		//이것들을 다 board에 담아서 리턴하고 있음
	}

	
	public String insert(Board board, AttachFile attach) {
		
		Connection conn = null;///////////////////////
		CallableStatement stmt = null;
		String seqno = null; //seqno 넘김
		
		   try {
			   conn = ds.getConnection();
			   String sql = "call p_insert_board(?,?,?)";
			   stmt = conn.prepareCall(sql);
			   
			   StructDescriptor st_board = StructDescriptor.createDescriptor("OBJ_BOARD" ,conn); //타입정의한것,오라클연결
			   Object[] obj_board = {board.getTitle(), board.getContent(), board.getOpen(), board.getId() };
			   STRUCT board_rec = new STRUCT(st_board, conn, obj_board);
			   
			   stmt.setObject(1, board_rec);
			   
			   ArrayDescriptor desc = ArrayDescriptor.createDescriptor("ATTACH_NT", conn);		//중복이라 타입은 위로 올림
			   ARRAY attach_arr = null;															//중복이라 위로 올림
			   
			//첨부파일
			if(attach != null) {
				
				StructDescriptor st_thumb = StructDescriptor.createDescriptor("OBJ_ATTACH_THUMB",conn);
					STRUCT attach_thumb_rec = null;
					Object[] obj_thumb = null;
				
				if(attach.getThumbnail() != null) { //썸네일이 있으면 담아서 189행에 obj_thumb담아라!
				
					obj_thumb = new Object[]{ attach.getThumbnail().getFileName(), 
										      attach.getThumbnail().getFileSize(),
										      attach.getThumbnail().getFilePath()};
					
				}
					attach_thumb_rec = new STRUCT(st_thumb, conn, obj_thumb);
				
				StructDescriptor st_attach = StructDescriptor.createDescriptor("OBJ_ATTACH",conn);
				
				Object[] obj_attach = {attach.getFileName(), attach.getSaveFileName(), 
										attach.getFileSize(), attach.getFiletype(), attach.getFilePath(),
										attach_thumb_rec};
				STRUCT[] attach_rec = new STRUCT[1];
					//여러개가 들어오니까 배열로 해줘야함
				attach_rec[0] = new STRUCT(st_attach, conn, obj_attach);
				
				attach_arr = new ARRAY(desc, conn, attach_rec);
			}
			else { //attach가 없어도 setting을 해줘야함
				attach_arr = new ARRAY(desc, conn, null);
			}			
			stmt.setArray(2, attach_arr);
			
			stmt.registerOutParameter(3, OracleType.VARCHAR2);
			stmt.executeQuery();
			
			seqno = stmt.getString(3);
		} catch (Exception e) {						
			e.printStackTrace();
		}finally {///////////////////
			resourceClose(conn, stmt);
		}		
		
		return seqno; 
	}
	
	public void insertThumbNail(String attach_no, AttachFile attachFile) {
		
		Connection conn = null;///////////////////////
		//썸네일 저장
		String sql = "INSERT INTO attachfile_thumb(no, filename, filesize, filepath, attach_no) "
		    + " VALUES (ATTACHFILE_THUMB_SEQ.NEXTVAL, ?,?,?,?)";
		
		PreparedStatement stmt = null;
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(sql);
			Thumbnail thumb = attachFile.getThumbnail();
			stmt.setString(1, thumb.getFileName());
			stmt.setString(2, thumb.getFileSize());
			stmt.setString(3, thumb.getFilePath());
			stmt.setString(4, attach_no);
			stmt.executeQuery();
		} catch (SQLException e) {			
			e.printStackTrace();
		}finally {///////////////////
			resourceClose(conn, stmt);
		}				
	}
	
	public String insertAttachFile(String seqno, AttachFile attachFile) {
		
		Connection conn = null;///////////////////////
		//첨부파일저장
		String sql = "INSERT INTO attachFile(no, filename, savefilename, filesize, filetype, filepath, board_seq)"
			+ " VALUES (ATTACHFILE_SEQ.NEXTVAL, ?,?,?,?,?,?)";
		PreparedStatement stmt = null;
		String attach_no = null;
		try {
			conn = ds.getConnection();///////////
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, attachFile.getFileName());
			stmt.setString(2, attachFile.getSaveFileName());
			stmt.setString(3, attachFile.getFileSize());
			stmt.setString(4, attachFile.getFiletype());
			stmt.setString(5, attachFile.getFilePath());
			stmt.setString(6, seqno);
			stmt.executeQuery();
			
			sql = "SELECT max(no) FROM attachFile";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			attach_no =rs.getString(1);			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {///////////////////
			resourceClose(conn, stmt);
		}		
		
		return attach_no;
	}		

	public void update(Board board, AttachFile attachFile) {
		Connection conn = null;///////////////////////
		//보드 update
		String sql="call p_updateBoard(?,?,?,?)";
		CallableStatement stmt = null;
		try {
			conn = ds.getConnection();
			stmt = conn.prepareCall(sql);
			stmt.setString(1, board.getTitle());
			stmt.setString(2, board.getContent());
			stmt.setString(3, board.getOpen());
			stmt.setString(4, board.getSeqno());
			stmt.executeQuery();
			//첨부파일
			if(attachFile != null) {				
				String attach_no = insertAttachFile(board.getSeqno(), attachFile);				
				String fileType = attachFile.getFiletype();				
				
				//썸네일
				if(fileType.substring(0, fileType.indexOf("/")).equals("image")) {
					insertThumbNail(attach_no, attachFile);
				}
			}			
		} catch (SQLException e) {			
			e.printStackTrace();
		}finally {///////////////////
			resourceClose(conn, stmt);
		}				
		
	}


	public int getTotalRec(Criteria cri) {
		int total =0;
		String search_title = null;
		String search_name = null;
		
		//제목검색
		if(cri.getSearchField() != null && cri.getSearchField().equals("title")) {
			search_title = cri.getSearchText();
		}
		
		//이름검색
		if(cri.getSearchField() != null && cri.getSearchField().equals("name")) {
			search_name = cri.getSearchText();
		}
		
		
		String sql="call p_getboardtotal(?,?,?)";
		CallableStatement stmt = null;
		Connection conn = null;///////////////////////
		
		try {
			conn = ds.getConnection();
			stmt=conn.prepareCall(sql);
			stmt.setString(1, search_name);
			stmt.setString(2, search_title);
			stmt.registerOutParameter(3, OracleTypes.INTEGER);
			stmt.executeQuery();
			
			total = stmt.getInt(3);		//총레코드 갯수를 여기에 저장하고 이걸리턴하면된다.
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {///////////////////
			resourceClose(conn, stmt);
		}		
				
		return total;
	}

	//게시물 삭제
	public Map<String, String> deleteByNo(String seqno) {
		Map<String, String> map = new HashMap<String, String>();
		CallableStatement stmt = null;
		Connection conn = null;///////////////////////
		String sql ="call p_deleteBaord(?,?,?,?)";
		try {
			conn = ds.getConnection();
			stmt = conn.prepareCall(sql);
			stmt.setString(1, seqno);
			stmt.registerOutParameter(2, OracleTypes.VARCHAR);
			stmt.registerOutParameter(3, OracleTypes.VARCHAR);
			stmt.registerOutParameter(4, OracleTypes.VARCHAR);
			stmt.executeQuery();
			map.put("savefilename", stmt.getString(2));
			map.put("filepath", stmt.getString(3));
			map.put("thumb_filename", stmt.getString(4));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {///////////////////
			resourceClose(conn, stmt);
		}		
		return map;
	}
	
	
	
	//자원반납
	private void resourceClose(Connection conn, PreparedStatement stmt) {
	      try {
	    	  if(stmt != null || conn != null) {
		         stmt.close();
		         conn.close();
	    	 }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
	   }
	   
	//자원반납
	private void resourceClose(Connection conn, CallableStatement stmt) {
	      try {
	    	  if(stmt != null || conn != null) {
			         stmt.close();
			         conn.close();
		    	 }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
	   }
	
}













