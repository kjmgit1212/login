package com.goodee.gdlibrary.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.goodee.gdlibrary.domain.BookDTO;
import com.goodee.gdlibrary.mapper.BookMapper;
import com.goodee.gdlibrary.util.PageUtils;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookMapper bookMapper;

	
	@Override
	public void getBooksInfo() {

		
				// API URL with Parameter
			String key = "d99cfc0bc8e22bb018dd441c09f57af2624e907b48a7135e2bce67d44b6a0db3";
			
			// API URL with Parameter
	
			String apiURL = "https://nl.go.kr/NL/search/openApi/saseoApi.do";
			apiURL += "?key=" + key + "&startRowNumApi=1&endRowNemApi=10&drCode=11";
			
			// API URL Connection
			URL url = null;
			HttpURLConnection con = null;
			try {
				url = new URL(apiURL);
				con = (HttpURLConnection)url.openConnection();
				con.setRequestMethod("GET");  // 반드시 "GET" 대문자로 지정
			} catch (MalformedURLException e) {
				e.printStackTrace();  // 잘못된 apiURL
			} catch (IOException e) {
				e.printStackTrace();  // apiURL 연결 실패
			}
			
			// API 응답
			StringBuilder sb = new StringBuilder();
			try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
				String line = null;
				while((line = br.readLine()) != null) {
					sb.append(line);
				}
			} catch(IOException e) {
				e.printStackTrace();  // API 응답 실패
			}
			List<BookDTO> list = new ArrayList<BookDTO>();
			JSONObject books = XML.toJSONObject(sb.toString());
			JSONObject items = books.getJSONObject("channel");
			JSONArray item = items.getJSONArray("list");
			for(int i = 0; i < item.length(); i++) {
				JSONObject result = (JSONObject)item.get(i);
				JSONObject b = (JSONObject)result.get("item");
				BookDTO book = new BookDTO();	
				book.setBookIsbn(b.getString("recomcallno"));
				book.setBookTitle(b.getString("recomtitle"));
				book.setBookAuthor(b.getString("recomauthor"));
				book.setBookPublisher(b.getString("recompublisher"));
				book.setBookPubdate(b.getString("regdate"));
				if(b.getString("recomcontens").length() > 3000) {
					book.setBookDescription(b.getString("recomcontens").substring(0, 3000));
				}else {
					book.setBookDescription(b.getString("recomcontens"));
				}
				book.setBookImage(b.getString("recomfilepath"));
				book.setBookField(b.getString("drCodeName"));
				
				list.add(book);
			}
				
			bookMapper.getBooksInfo(list);
		
	}
	
	
	@Override
	public Map<String, Object> bookList(int page) {
		
		// page, totalRecord를 이용해서 페이징
				Integer totalRecord = bookMapper.selectBookCount();
				PageUtils p = new PageUtils();
				p.setPageEntity(totalRecord, page);
				
				
				// 목록은 beginRecord ~ endRecord 사이값을 가져온다.
				Map<String, Object> m = new HashMap<String, Object>();
				
				m.put("beginRecord", p.getBeginRecord());
				m.put("endRecord", p.getEndRecord());
				
				
				// 목록과 paging 정보를 반환
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("books", bookMapper.selectBookList(m));
				map.put("p", p);
			
				return map;
				
	}
	
	@Override
	public Map<String, Object> searchBook(HttpServletRequest request) {

		String query = request.getParameter("query");
		String column = request.getParameter("column");

		Map<String, Object> map = new HashMap<>();
		map.put("query", query);
		map.put("column", column);
		
		
		
		List<BookDTO> books = bookMapper.searchBook(map);
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("books", books);
		return resMap;
		
	}
	
	
	
	@Override
	public void detailBook(HttpServletRequest request, Model model) {
		Long bookNo = Long.parseLong(request.getParameter("bookNo"));
		model.addAttribute("book", bookMapper.detailBook(bookNo));
		
	}
	

	@Override
	public Map<String, Object> recomBook(Model model) {
		
		List<BookDTO> list = bookMapper.recomBook();
		
		Map<String, Object> map = new HashMap<>();
		map.put("recom", list);
		
		model.addAttribute("recom", map);
		

		return map;
	}
	
	
	
	
}	
	

