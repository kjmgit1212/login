package com.goodee.ex13.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.goodee.ex13.domain.FreeBoardDTO;
import com.goodee.ex13.mapper.FreeBoardMapper;
import com.goodee.ex13.util.PageUtils;

@Service
public class FreeBoardServiceImpl implements FreeBoardService {

	@Autowired
	private FreeBoardMapper freeBoardMapper;
	
	@Override
	public void findFreeBoards(HttpServletRequest request, Model model) {
		
		// totalRecord(DB), page(Parameter)
		int totalRecord = freeBoardMapper.selectFreeBoardCount();
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		
		// PageEntity 계산
		PageUtils pageUtils = new PageUtils();
		pageUtils.setPageEntity(totalRecord, page);
		
		// Map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginRecord", pageUtils.getBeginRecord());
		map.put("endRecord", pageUtils.getEndRecord());
		
		// 목록 가져오기
		List<FreeBoardDTO> freeBoards = freeBoardMapper.selectFreeBoardList(map);
		
		// free/list.jsp로 전달할 데이터
		model.addAttribute("freeBoards", freeBoards);
		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("paging", pageUtils.getPaging(request.getContextPath() + "/freeBoard/list"));

	}

		@Override
		public int saveFreeBoard(HttpServletRequest request) {
			// 게시글 작성자
			String writer = request.getParameter("writer");
			String content = request.getParameter("content");
			
			// 작성ip
			Optional<String> opt = Optional.ofNullable(request.getHeader("X-Forwarded-For"));
			String ip = opt.orElse(request.getRemoteAddr());
			
			FreeBoardDTO freeBoard = new FreeBoardDTO();
			freeBoard.setWriter(writer);
			freeBoard.setContent(content);
			freeBoard.setIp(ip);
			
			return freeBoardMapper.insertFreeBoard(freeBoard);
		}
		
		@Transactional	// saveReply 메소드 내부에서 update와 insert 호출을 하고 있으므로 *select는 제외
		@Override
		public int saveReply(HttpServletRequest request) {
		
			
			
		// 작성자, 내용
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
		
		
		// 원글의 depth groupNo groupOrd
		int depth = Integer.parseInt(request.getParameter("depth"));
		Long groupNo = Long.parseLong(request.getParameter("groupNo"));
		int groupOrd = Integer.parseInt(request.getParameter("groupOrd"));
			
		// 작성ip
		Optional<String> opt = Optional.ofNullable(request.getHeader("X-Forwarded-For"));
		String ip = opt.orElse(request.getRemoteAddr());
		
		
		// 원글의 depth groupNo groupOrd 이용해서 댓글의 댓글의 depth groupNo groupOrd 계산
		// 댓글의 depth는 원글 depth + 1
		// 댓글의 groupNo : 원글의 groupNo와 같음
		// 댓글의 groupOrd : 같은그룹의 기존 댓글 모두 groupOrd + 1 해준 후 원글 groupOrd + 1
		
		// 같은그룹의 기존 댓글 모두 groupOrd + 1 처리를 위해서는 
		// FreeBoardMapper에 원글의 정보를 넘겨야 한다.
		
		// 원글DTO
		FreeBoardDTO freeBoard = new FreeBoardDTO();
		freeBoard.setGroupNo(groupNo);
		freeBoard.setGroupOrd(groupOrd);
		
		freeBoardMapper.updatePreviousReply(freeBoard);
		
		// 하나의 서비스에서 mapper두개 호출 -> transaction!!
		// 삽일할 댓글 dto
		FreeBoardDTO reply = new FreeBoardDTO();
		reply.setWriter(writer);
		reply.setContent(content);
		reply.setDepth(depth + 1);
		reply.setGroupNo(groupNo);
		reply.setGroupOrd(groupOrd + 1);
		reply.setIp(ip);
		
			return freeBoardMapper.insertReply(reply);
		}
	
		@Override
		public int removeFreeBoard(Long freeBoardNo) {
			
			
			return freeBoardMapper.deleteFreeBoard(freeBoardNo);
		}

}