package com.kcm.dao;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kcm.common.OracleConn;
import com.kcm.dto.Member;

import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;



public class MemberDao {

	private final Connection conn = OracleConn.getInstance().getConn();
	PreparedStatement stmt = null;

	public Map<String, String> loginProc(String id, String pw){
		Map<String, String> status= new HashMap<String, String>();

		//2.sql문 작성
		String sql = "Select * from member where id = ?";  
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery(); 

			if(rs.next()) {
				if(rs.getString("pw").equals(pw)){ 
					status.put("login", "ok"); 
					status.put("name", rs.getString("name"));
				}else{
					status.put("login", "pwFail"); 
				}
			}
			else {
				status.put("login", "fail");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return status;
	}

	public int insertMember(HttpServletRequest request) {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");

		String hobby[] = request.getParameterValues("hobby");
		
		String email = request.getParameter("eid") + "@" + request.getParameter("domain");
		String intro = request.getParameter("intro");
		
		
		//프로시저 만들기
		CallableStatement stmt;
		int rs = 0;
		try {
			String sql = "call p_insert_Member(?,?,?)";
			stmt = conn.prepareCall(sql);
			//오브젝트로 바꿔서 넘기기
			StructDescriptor st_desc = StructDescriptor.createDescriptor("OBJ_MEMBER", conn);
			Object[] obj_member = {id, pw, name, gender, email, intro }; //하나의 레코드지만 object배열로 선언을 해줘야 된다! 주의!
			STRUCT member_rec = new STRUCT(st_desc, conn, obj_member); //OBJ_MEMBER를  obj_member 에 담기위해서
			stmt.setObject(1, member_rec);
			
			ArrayDescriptor desc = ArrayDescriptor.createDescriptor("STRING_NT", conn);
			ARRAY hobby_arr = new ARRAY(desc, conn, hobby); //오라클타입 + 자바문자열로들어있는체 를 변환해주는 역활을 한다. = 바인딩 한다(타입을)
			stmt.setArray(2, hobby_arr); //set ARRAY!!주의 setSting 아님!
			stmt.registerOutParameter(3, OracleTypes.INTEGER);
			stmt.executeUpdate();
			rs = stmt.getInt(3);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}

	public int selectByid(String id) {
		CallableStatement stmt = null;
		int rs = 0;
		String sql ="call p_idDoubleCheck(?, ?)";
		try {
			stmt = conn.prepareCall(sql);
			stmt.setNString(1, id);
			stmt.registerOutParameter(2, OracleTypes.INTEGER);
			stmt.executeQuery();
			
			rs = stmt.getInt(2);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}

	public List<Member> getMember() {
		CallableStatement stmt = null;
		
		List<Member> member = new ArrayList<Member>();
		
		String sql = "call p_get_member(?)";
		
		try {
			stmt = conn.prepareCall(sql);
			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.executeQuery();
			
     		ResultSet rs = (ResultSet)stmt.getObject(1);
     		while(rs.next()){
     			Member m = new Member();
     			m.setId(rs.getString("id"));
     			m.setName(rs.getString("name"));
     			m.setGender(rs.getString("gender"));
     			m.setWdate(rs.getString("wdate"));
     			//취미
     			if(rs.getArray("hobby_nm") != null) {
     				//컬렉션 중첩테이블 데이터 가져오기
     				ARRAY h_arr = ((OracleResultSet)rs).getARRAY("hobby_nm");
     				System.out.println("취미 타입 :" + h_arr.getSQLTypeName());
     				System.out.println("취미 타입 코드 :" + h_arr.getBaseType());
     				System.out.println("취미 갯수 :" + h_arr.length());
     				
     				String[] h_val = (String[])h_arr.getArray();
     				for(int i=0; i< h_val.length; i++) {
     					String hobby_str = h_val[i];
     					System.out.println(">>>취미["+i+"]=" + hobby_str);
     				}
     				
     				
     				m.setHobby(Arrays.toString(h_val));
     				
     			}
     			
     			member.add(m);
     		}
     		
     		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return member;
	}

	
	
	
	
	
	//	private void resourceClose() {
	//		//자원반납
	//		try {
	//			stmt.close();
	//			conn.close();
	//		} catch (SQLException e) {
	//			e.printStackTrace();
	//		}
	//	}
}



