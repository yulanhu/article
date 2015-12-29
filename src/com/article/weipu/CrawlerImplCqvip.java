
package com.article.weipu;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author huyulan
 *
 */
public class CrawlerImplCqvip {
	static Map<String,String> cookies = CookiesUtils.cookies("维普");
	static Map<String, String> map;
	static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	
	public static Map<String, String> getContentByURL(String url) {
		try {
			map = new LinkedHashMap<String,String>();
			Document doc = null;
			Connection con = Jsoup.connect(url)
					  .userAgent(USER_AGENT)
					  .cookies(cookies)
					  .timeout(60000);
			if(con.execute().statusCode() == 200){
				 doc = con.get();
				 Elements section_baseinfo = doc.getElementsByClass("detailtitle");
					
					if(section_baseinfo.html().contains("<h1>")&& section_baseinfo.html().contains("</h1>")){
						String title = section_baseinfo.first().select("h1").text();
						map.put("标题", title);
					}else
						map.put("标题", " ");
					if(section_baseinfo.html().contains("<strong>")&& section_baseinfo.html().contains("</strong>")){
						String info = section_baseinfo.select("strong").first().text();
						String [] journalAnAauthor = info.split(" \\| ",2);
						map.put("刊名", journalAnAauthor[0]);
						String[] split = journalAnAauthor[1].split("   ",2);
						if(split.length == 2){
							map.put("作者", split[0]);
							map.put("作者单位", split[1]);	
						}
					}
					if(doc.html().contains("<td class=\"sum\" colspan=\"2\">")){
						Elements abstr = doc.getElementsByClass("sum");
						map.put("摘要", abstr.text());
					}
					
					
					if(doc.getElementsByAttributeValue("class", "detailinfo").html().contains("datainfo f14")){
						Element datainfo = doc.getElementsByAttributeValue("class", "datainfo f14").last();
						if(datainfo.html().contains("<tr>")&& datainfo.html().contains("</tr>")){
							Elements tr = datainfo.select("tr");
							for (Element detail: tr) {
								Elements td = detail.getElementsByTag("td");
								String key = td.get(0).text();
								map.put(key.substring(1, key.length()-1), td.get(1).text());
							}
						}
					}
				 }

		} catch (IOException e) {
			System.out.println("err: " + url);
			e.printStackTrace();
		} 
		return map;
	}

}
