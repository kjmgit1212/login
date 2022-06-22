package com.goodee.movie.exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

public class apiMain3 {

	public static void main(String[] args) {
		// key
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
					
				JSONObject a = XML.toJSONObject(sb.toString());
				JSONObject b = a.getJSONObject("channel");
				JSONArray items = b.getJSONArray("list");
			//	JSONObject c = b.getJSONObject("list");
				System.out.println(items);
		}
	}

