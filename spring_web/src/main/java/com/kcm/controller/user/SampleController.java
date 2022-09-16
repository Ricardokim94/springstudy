package com.kcm.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kcm.dto.Board;
import com.kcm.dto.Member;

@Controller
public class SampleController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
	
	
	@RequestMapping("doA") //매핑을 해보자! //doA라는 jsp를 만들고!
	public ModelAndView doo() {		//간단하게 회원가입form같은거 띠울때 사용!! 데이터를 넘길 필요가 없으니까
		
		ModelAndView mv = new ModelAndView(); //스프링에서 제공하는 클래스
		
		mv.setViewName("member/memRegForm"); //뷰페이지 이름을 적어주면됨
		mv.addObject("msg", "회원가입폼");
		
		return mv;
		
		
	}
	//리턴을 안주면 doA라는 jsp를 찾는다.spring이
	
	
	//리턴타입이 String인 경우												
	//@ModelAttribute은 get방식으로 넘어온 값을 자동으로 해당객체를 뷰까지 전달한다.
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
		//model.addAttribute("msg", "곧 점심시간"); //이렇게 해서 데이터를 넘긴다. (키하고 값을넘긴다.)
		return "doC";	//result.jsp를 하나 만들고~
	}
	
	@RequestMapping("doC")
	public String doC(RedirectAttributes rttr) { //RedirectAttributes  = 스프링에서 제공해주는 클래스
		
		Member m = new Member(); //dto에 있는 Member
		m.setId("joy");
		m.setName("홍길동");
		
		Board b = new Board();
		b.setTitle("안녕자바");
		
		rttr.addFlashAttribute(m);
		rttr.addFlashAttribute(b);
		
		return "redirect:/doA";
	}
	
	
	//json 라이브러리 추가   ex) [ {A,B},{c,d}]
	@RequestMapping("doJASON")
	public @ResponseBody Member dojason() {
		
		Member m = new Member(); //dto에 있는 Member
		m.setId("joy");

//		m.setHobby_str("테니스, 골프");

		m.setHobby_str("테니스, 골프");
		m.setName("홍길동");
		
		return m;
	}
	
}






