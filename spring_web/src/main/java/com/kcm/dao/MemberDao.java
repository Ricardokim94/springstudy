package com.kcm.dao;


import java.util.List;
import java.util.Map;

import com.kcm.dto.Member;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.kcm.common.OracleConn;

import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleTypes;



public interface MemberDao {

	public Map<String, String> loginProc(String id, String pw);
	
	 public int insertMember(Member member);
	 
	 public int selectByid(String id);
	 
	 public List<Member> getMember();
	 
	 
}


