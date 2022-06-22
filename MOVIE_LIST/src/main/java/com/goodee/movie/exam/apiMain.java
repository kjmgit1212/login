package com.goodee.movie.exam;

import java.io.BufferedInputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class apiMain {

	public static void main(String[] args) {
		// key
				String key = "d99cfc0bc8e22bb018dd441c09f57af2624e907b48a7135e2bce67d44b6a0db3";
				
				// API URL with Parameter

				String apiURL = "https://nl.go.kr/NL/search/openApi/saseoApi.do";
				apiURL += "?key=" + key + "&startRowNumApi=1&endRowNemApi=10&drCode=11";
				
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				try {
					DocumentBuilder builder = factory.newDocumentBuilder();
					StringBuffer urlPharm = new StringBuffer();
					urlPharm.append("https://nl.go.kr/NL/search/openApi/saseoApi.do");
					urlPharm.append("?key=" + key + "&startRowNumApi=1&endRowNemApi=10&drCode=11"); 
					
					URL url = new URL(urlPharm.toString());
					
					BufferedInputStream xml = new BufferedInputStream(url.openStream());
					
					Document document = builder.parse(xml);
					
					Element root = document.getDocumentElement();
					
					NodeList list = root.getElementsByTagName("item");
					
					for (int i = 0; i < list.getLength(); i++) {
						Node node = list.item(i);
						NodeList item_child = node.getChildNodes();
						for(int j=0; j < item_child.getLength(); j++) {
							Node items = item_child.item(j);
							
						}
					}
					
					
				}catch (Exception e) {
					e.printStackTrace();
				}

	}

}
