package com.kcm.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kcm.dto.Criteria;
import com.kcm.dto.Page;
import com.kcm.service.BoardServiceImp;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	
	BoardServiceImp b = new BoardServiceImp();
	
	@RequestMapping("boardList")
	public ModelAndView boardList(ModelAndView m, HttpServletRequest req) {
		String searchfield = req.getParameter("search_field");	//검색에
		String searchText = req.getParameter("search_text");	//검색에
		
		String currentPage =req.getParameter("currentPage");
		String rowPerPage = req.getParameter("rowPerPage");
		if(currentPage == null) currentPage ="1";
		if(rowPerPage == null) rowPerPage ="3";
		
		Criteria c = new Criteria(Integer.parseInt(currentPage), Integer.parseInt(rowPerPage));
		c.setSearchField(searchfield);
		c.setSearchText(searchText);
		
		m.addObject("pageMaker", new Page(b.getTotalRec(c), c));
		m.addObject("boardList",b.list(c));

		return m;
	} 
	
}
