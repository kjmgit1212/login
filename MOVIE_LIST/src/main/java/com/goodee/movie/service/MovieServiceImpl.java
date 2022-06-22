package com.goodee.movie.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.goodee.movie.mapper.MovieMapper;

public class MovieServiceImpl implements MovieService {
	@Autowired
	private MovieMapper movieMapper;
	@Override
	public void dailyBoxOffice(HttpServletRequest request, Model model) {
		
		// key
				String key = "ffc630a52a8ade57cb40378845df3118";
				
				// API URL with Parameter
				String apiURL = "https://api.themoviedb.org/3/movie/619803-2";
				apiURL += "?api_key=" + key + "&language=ko-KR";
				
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
				
				JSONObject obj = new JSONObject(sb.toString());
				Map<String, Object> map = new HashMap<>();
				map.put("movie", obj);
				
				model.addAttribute("movies", map);
				System.out.println(model);
	}
	
}
