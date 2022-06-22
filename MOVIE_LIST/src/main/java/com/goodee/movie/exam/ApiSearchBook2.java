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

public class ApiSearchBook2 {
	
    public static void main(String[] args) {
    	
    	String serviceKey = "1f071407-df7a-40cb-a0b7-cccf22d24396";
        try {
      // 	String query = JOptionPane.showInputDialog("책 관련 검색어를 입력하세요.");
       // String text = URLEncoder.encode(query, "UTF-8");
        	String apiURL = "http://api.kcisa.kr/openapi/service/rest/meta13/getNLSF0401?serviceKey=" + serviceKey;
        //    Map<String, String> requestHeaders = new HashMap<>();
        //    requestHeaders.put("X-Naver-Client-Id", clientId);
         //   requestHeaders.put("X-Naver-Client-Secret", clientSecret);
            URL url = new URL(apiURL);
        	HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
         //   for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
        //        con.setRequestProperty(header.getKey(), header.getValue());
         //   }
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
            System.out.println(obj.toString());
            }
            
        	catch (Exception e) {
        	
        	}
        }
    }

