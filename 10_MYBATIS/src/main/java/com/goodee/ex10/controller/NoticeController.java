package com.goodee.ex10.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.goodee.ex10.domain.NoticeDTO;
import com.goodee.ex10.service.NoticeService;

@Controller
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/notice/list")
	public String list(Model model) {
		model.addAttribute("notices", noticeService.findNotices());
		return "notice/list";
	}
	
	@GetMapping("/notice/savePage")
	public String savePage() {
		return "notice/save";
	}
	
	
	@PostMapping("/notice/save")
	public String save(HttpServletRequest request, RedirectAttributes redirectAttributes) {
			NoticeDTO notice = new NoticeDTO();
			notice.setTitle(request.getParameter("title"));
			notice.setContent(request.getParameter("content"));
			int res = noticeService.save(request);
			
			// 성공.실패 메시지 처리를 담당하는 result.jsp를 만들고,
			// result.jsp로 redirect로 이동하는 방법을 사용한다.
			// result.jsp로 성공/실패 유무, 작업종류를 보내줘야 하는데,
			// rediect로 이동하는 경우에는 RedirectAttributes에 정의된 addFlashAttribute() 메소드를
			// 이용해 값을 전달할 수 있다.
			
			redirectAttributes.addFlashAttribute("kind", "insert");
			redirectAttributes.addFlashAttribute("res", res);
			
			return "redirect:/notice/afterDML";
			
			
			
			// 성공/실패 메시지가 없는 기존방법
			// return "redirect:/notice/list";		// redirect는 매핑(/notice/list)으로 이동!
	}
	
	
	@GetMapping("/notice/afterDML")
	public String afterDML() {
		return "notice/result";		// notice 폴더아래 result.jsp
	}
	
	
	@GetMapping("/notice/detail")
	public String detail(HttpServletRequest request, Model model) {
		
		model.addAttribute("notice", noticeService.findByNo(request));
		return "notice/detail";
	}
	
	@GetMapping("/notice/changePage")
	public String changePage(HttpServletRequest request, Model model) {
		model.addAttribute("notice", noticeService.findByNo(request));
		return "/notice/change";
	}
	
	@PostMapping("/notice/change")
	public String change(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("kind", "update");
		redirectAttributes.addFlashAttribute("res", noticeService.change(request));
		
		return "redirect:/notice/afterDML";
	}
	
	@GetMapping("/notice/removeOne")
	public String removeOne(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("kind", "deleteOne");
		redirectAttributes.addFlashAttribute("res", noticeService.removeOne(request));
		
		return "redirect:/notice/afterDML";
	}
	
	@GetMapping("/notice/removeList")
	public String removeList(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("kind", "deleteList");
		redirectAttributes.addFlashAttribute("res", noticeService.removeList2
				(request));
		
		return "redirect:/notice/afterDML";
	}
	
	
	
}
