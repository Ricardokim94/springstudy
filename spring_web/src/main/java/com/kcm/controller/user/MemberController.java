package com.kcm.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kcm.dto.Member;

@Controller
@RequestMapping("/member/")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@RequestMapping("memRegForm")
	public void memRegForm() {
		logger.info("회원가입 매핑");
	}
	
	@PostMapping("register")
	public String register(Member member) { //데이터 담는것을 메소드 매개변수에 넣으면 된다 [맴버를 담으면 자동으로 의존성주입처럼 객체를 생성해서 메모리에 올려준다]
		logger.info("회원가입처리 매핑");
		
		logger.info("아이디 :{}", member.getId()); //변수처리할때 {}를 쓴다.
		logger.info("이름 :{}", member.getName());
		
		return "redirect:/";
	}
}
