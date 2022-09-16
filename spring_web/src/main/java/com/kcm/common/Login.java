package com.kcm.common;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kcm.dao.MemberDao;


@Controller
public class Login {
	
	private static Logger log = LoggerFactory.getLogger(Login.class);
	
	@PostMapping("login")
	public String login(@RequestParam("id") String id, 
						@RequestParam("pw") String pw,
						HttpSession sess,
						RedirectAttributes model) { //넘기는 값은 Model쓴다.
		MemberDao dao = new MemberDao();
		Map<String, String> map = dao.loginProc(id, pw);
		String viewPage = null;
		
		switch (map.get("login")) {
		case "ok" :	//로그인 성공
			//세션설정!
			LoginImpl loginUser = new LoginImpl(id, map.get("name"));
			sess.setAttribute("loginUser", loginUser);
			model.addFlashAttribute("msg", "loginOk");
			viewPage = "redirect:/";
			break; 
		default: //로그인실패
			model.addFlashAttribute("msg", "loginFail");
			viewPage = "index";
		
		}
		return viewPage;
		//오류났던것 : "viewPage"를 붙이면 JSP로 넘어간다.
	}

}













