package com.kcm.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kcm.common.LoginImpl;
import com.kcm.dto.Board;
import com.kcm.dto.Criteria;
import com.kcm.dto.Page;
import com.kcm.service.BoardService;
import com.kcm.service.BoardServiceImp;

@Controller
@RequestMapping(value="/board/")
public class BoardController {
	
	@Autowired //의존성 자동으로 하라는것 = 필드에다가 의존성 주입을 함 //자동생성
	BoardService boardService; 
	
	//리스트에 구현을 해봄
		//@GetMapping("boardList")
		//@PostMapping("boardList")
	@RequestMapping(value="boardList", method= {RequestMethod.POST, RequestMethod.GET})
	public String list(Criteria cri, Model model) {

		if(cri.getCurrentPage() == 0) cri.setCurrentPage(1);
		if(cri.getRowPerPage() == 0) cri.setRowPerPage(3);
		
		List<Board> board = boardService.list(cri);
		
		model.addAttribute("pageMaker", new Page(boardService.getTotalRec(cri), cri)); //페이지분할
		model.addAttribute("boardList",board);
		return "/board/boardList";
	}
	
	//등록 페이지
	@GetMapping("boardForm")
	public void boardForm() {
	}
	
	@PostMapping("register")
	public String register(Board board, 
						   MultipartFile filename, 
						   HttpSession sess,
						   RedirectAttributes rttr){ //redirect할때는 이 객체 써서 넘겨야 된다.
		
		board.setId(((LoginImpl)sess.getAttribute("loginUser")).getId()); //login.java에 id저장되어 있음
		rttr.addFlashAttribute("seqno",boardService.insertBoard(board, filename));
		
		return "redirect:/board/detail"; //등록하고 다시보여줘야되니까 seq넘겨서 => redirect써야됨
	}
	
	//디테일 페이지
	@GetMapping("detail")
	public String detail(@ModelAttribute("seqno") String seqno,
						 Model model,
						 RedirectAttributes rttr) {
		
		model.addAttribute("board",boardService.searchBoard(seqno)); //값을 넘길때 model로 넘김
		
		return "/board/detail";
	}
	
	
	
	//리스트 혼자 한번 해봄
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
