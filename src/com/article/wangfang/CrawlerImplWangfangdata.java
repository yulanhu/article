package com.article.wangfang;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * 
 * @author huyulan
 * @date 2015-11-10
 * 
 */

public class CrawlerImplWangfangdata{
	static Map<String, String> map;
	public static Map<String, String> getContentByURL(String htmlFileName){
		map = new LinkedHashMap<String,String>();
		
		try {
			File input = new File(htmlFileName);
			Document doc = Jsoup.parse(input, "UTF-8");
			String html = doc.html();
			if(html.contains("<div class=\"section-baseinfo\">")){
				Elements section_baseinfo = doc.getElementsByAttributeValue("class", "section-baseinfo");
				String title = section_baseinfo.first().select("h1").text();
				map.put("标题", title);
			}else
				map.put("标题", null);
			
			if(html.contains("<div class=\"row clear zh\">")){
				String abstrCH = doc.getElementsByAttributeValue("class", "row clear zh").text();
				map.put("摘要", abstrCH);
			}
			
			if(html.contains("fixed-width baseinfo-feild")){
				Elements baseinfo = doc.getElementsByAttributeValue("class", "fixed-width baseinfo-feild");
				Elements info = baseinfo.select("div");
				if(info.size() > 2){
					for (int i = 1; i < info.size();i++) {
						Element infoo = info.get(i);
						String infoii[] =infoo.text().split("：",2);
						if(infoii[0].equals("刊 名")){
							map.put("刊名", infoii[1]);	
						}else{
							map.put(infoii[0], infoii[1]);	
						}
						
					}
				}
			}
			
		} catch (IOException e) {
			System.out.println("err: " + htmlFileName);
			e.printStackTrace();
		}
		return map;
		
	}

}
