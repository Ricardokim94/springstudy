package com.kcm.service;

import java.util.List;
import java.util.Map;

import com.kcm.dto.Member;


public interface MemberService {

	
	Map<String, String> login(String id, String pw);

	int insert(Member member);
	
	public int idDoubleCheck(String id);

	public List<Member> list();
	
}
