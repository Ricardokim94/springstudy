package com.kcm.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kcm.dto.Member;
import com.kcm.service.MemberService;
import com.kcm.service.MemberServiceImp;

@Controller
@RequestMapping("/member/")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	//MemberService m = new MemberServiceImp(); 할필요x
	
	@Autowired //dispather가 생성자호출할때 memberservice라는 m을 호출함 (=나중에 고칠필요가 없음) =>알아서 객체를 singleton으로 만들어준
	private MemberService m;
	
	MemberController(MemberService m){
		this.m = m;	//이러면 new를 안해도 된다 [의존성주입]
	}
=======
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kcm.dto.Member;
import com.kcm.service.MemberService;
import com.kcm.service.MemberServiceImp;

@Controller
@RequestMapping("/member/")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	MemberService m = new MemberServiceImp();
	
>>>>>>> branch 'master' of https://github.com/Ricardokim94/springstudy.git
	
	@RequestMapping("memRegForm")
	public void memRegForm() {
		logger.info("회원가입 매핑");
	}
	
	@PostMapping("register")
	public String register(Member member) { //데이터 담는것을 메소드 매개변수에 넣으면 된다 [맴버를 담으면 자동으로 의존성주입처럼 객체를 생성해서 메모리에 올려준다]
		logger.info("회원가입처리 매핑");
		
		logger.info("아이디 :{}", member.getId()); //변수처리할때 {}를 쓴다.
		logger.info("이름 :{}", member.getName());
		
		m.insert(member);
		
		return "redirect:/";
	}
	@RequestMapping("idDoubleCheck") 
	public ResponseEntity<String> idDoubleCheck(@RequestParam("id") String id){ //js formcheck에 "id"
		logger.info("idDoubleCheck call~!");
		
		String rs = Integer.toString(m.idDoubleCheck(id));
		
		return new ResponseEntity<String>(rs, HttpStatus.OK); //상태값이랑 결과를 던져야함
	}
}









