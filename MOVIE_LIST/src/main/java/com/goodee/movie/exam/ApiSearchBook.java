package com.goodee.movie.exam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiSearchBook {
	
    public static void main(String[] args) {
    	
    	String clientId = "s11xuwxWdEJ_nqr4vP8Z";
		String clientSecret = "FMyUkSUirs";
        try {
       	String query = JOptionPane.showInputDialog("책 관련 검색어를 입력하세요.");
        String text = URLEncoder.encode(query, "UTF-8");
        	String apiURL = "https://openapi.naver.com/v1/search/book?query=" + text + "&display=100";
            Map<String, String> requestHeaders = new HashMap<>();
            requestHeaders.put("X-Naver-Client-Id", clientId);
            requestHeaders.put("X-Naver-Client-Secret", clientSecret);
            URL url = new URL(apiURL);
        	HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }
            InputStream in = null;
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            	in = con.getInputStream();
            } else {
            	in = con.getErrorStream();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                responseBody.append(line);
            }
            br.close();
            JSONObject obj = new JSONObject(responseBody.toString());
            System.out.println(obj);
    }catch (Exception e) {
		e.printStackTrace();
	}
}
}
