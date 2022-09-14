package com.kcm.www;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kcm.dto.Board;
import com.kcm.dto.Member;

@Controller
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
	
	
	@RequestMapping("doA") //매핑을 해보자! //doA라는 jsp를 만들고!
	public void doo() {		//간단하게 회원가입form같은거 띠울때 사용!! 데이터를 넘길 필요가 없으니까
		logger.info("doA called~!");
	}
	//리턴을 안주면 doA라는 jsp를 찾는다.spring이
	
	
	//리턴타입이 String인 경우
	@RequestMapping("doB")
	public String doB(@ModelAttribute("msg") String message, Model model) {    //객체를 넘기고 싶으면Spring 에서는 Model을 쓴다
		logger.info("doB called~!");
		logger.info("doB called.. {}", message); //변수의 값을 찍을때 이렇게 함 [get방식!]
		
		Member m = new Member(); //dto에 있는 Member
		m.setId("joy");
		m.setName("홍길동");
		
		Board b = new Board();
		b.setTitle("안녕자바");
		
		model.addAttribute("member", m); //객체를 넘기는 것 [키 , 값]
		model.addAttribute(b);
		model.addAttribute("msg", "곧 점심시간"); //이렇게 해서 데이터를 넘긴다. (키하고 값을넘긴다.)
		return "result";	//result.jsp를 하나 만들고~
	}
	
	
}






