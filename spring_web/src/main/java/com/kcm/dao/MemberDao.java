package com.kcm.dao;

import java.util.List;
import java.util.Map;

import com.kcm.dto.Member;

public interface MemberDao { //의존성주입때문에 [유지보수] interface를 쓴다

	public Map<String, String> loginProc(String id, String pw);
	
	public int insertMember(Member member);
	
	public int selectByid(String id);
	
	public List<Member> getMember();

}
