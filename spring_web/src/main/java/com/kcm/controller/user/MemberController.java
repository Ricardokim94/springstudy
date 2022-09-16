package com.kcm.controller.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.kcm.dto.Member;
import com.kcm.service.MemberService;


@Controller
@RequestMapping("/member/")
public class MemberController {
   private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
   //private MemberServiceImp ms =new MemberServiceImp();
   
   @Autowired
   private MemberService ms;
   
//   public MemberController(MemberService ms) {
//      this.ms = ms;
//   }
//   
   @RequestMapping("memRegForm")
   public void memRegForm() {
      logger.info("회원가입폼 매핑");
   }
   
   //@PostMapping() 포스트방식 매핑
   //@PostMapping("register")
   @RequestMapping(value="register", method=RequestMethod.POST)
   public String register(Member member) {
      logger.info("회원가입 처리 매핑");
      logger.info("아이디 : {}",member.getId());
      logger.info("이름 : {}",member.getName());
      ms.insert(member);
      return "redirect:/";
   }
   
   @GetMapping("idDoubleCheck")
   public ResponseEntity<String>  idDoubleCheck(@RequestParam("id") String id){
      logger.info("idDoublieCheck called...");
      String rs = Integer.toString(ms.idDoubleCheck(id));
      return new ResponseEntity<String>(rs,HttpStatus.OK);
   }
   
   
}