package com.kcm.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

	
	@GetMapping("/chat")
	public String regForm() {
		return "chat/chat";
	}
	
	
}
