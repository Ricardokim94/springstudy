package com.kcm.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kcm.dto.Board;
import com.kcm.dto.Criteria;
import com.kcm.dto.Page;
import com.kcm.service.BoardService;
import com.kcm.service.BoardServiceImp;

@Controller
@RequestMapping(value="/board/*")
public class BoardController {
	
	@Autowired //의존성 자동으로 하라는것 = 필드에다가 의존성 주입을 함 //자동생성
	BoardService boardService; 
	
	//리스트에 구현을 해봄
		//@GetMapping("boardList")
		//@PostMapping("boardList")
	@RequestMapping(value="boardList", method= {RequestMethod.POST, RequestMethod.GET})
	public String list(Criteria cri, Model model) {
		String searchfield = cri.getSearchField();
		String searchText = cri.getSearchText();
		int currentPage =cri.getCurrentPage();
		int rowPerPage = cri.getRowPerPage();
		
		if(currentPage == 0) currentPage =1;
		if(rowPerPage == 0) rowPerPage =3;
		
		Criteria cri2 = new Criteria(currentPage, rowPerPage);
		cri2.setSearchField(searchfield);
		cri2.setSearchText(searchText);
		List<Board> board = boardService.list(cri2);
		
		model.addAttribute("pageMaker", new Page(boardService.getTotalRec(cri2), cri2)); //페이지분할
		model.addAttribute("boardList",board);
		return "/board/boardList";
	}
	
	
	
	
	
	
	
	
//	BoardServiceImp b = new BoardServiceImp();
//	
//	@RequestMapping("boardList")
//	public ModelAndView boardList(ModelAndView m, HttpServletRequest req) {
//		String searchfield = req.getParameter("search_field");	//검색에
//		String searchText = req.getParameter("search_text");	//검색에
//		
//		String currentPage =req.getParameter("currentPage");
//		String rowPerPage = req.getParameter("rowPerPage");
//		if(currentPage == null) currentPage ="1";
//		if(rowPerPage == null) rowPerPage ="3";
//		
//		Criteria c = new Criteria(Integer.parseInt(currentPage), Integer.parseInt(rowPerPage));
//		c.setSearchField(searchfield);
//		c.setSearchText(searchText);
//		
//		m.addObject("pageMaker", new Page(b.getTotalRec(c), c));
//		m.addObject("boardList",b.list(c));
//
//		return m;
//	} 
	
}
