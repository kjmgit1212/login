package com.goodee.ex05.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class OpenApiServiceImpl implements OpenApiService {

	@Override
	public String dailyBoxOffice(String language) {
		
		// key
		String key = "ffc630a52a8ade57cb40378845df3118";
		
		// API URL with Parameter
		String apiURL = "https://api.themoviedb.org/3/movie/619803-2";
		apiURL += "?api_key=" + key + "&language=" + language;
		
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
		System.out.println(sb.toString());
		return sb.toString();
		
	}

}