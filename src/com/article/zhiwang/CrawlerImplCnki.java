package com.article.zhiwang;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author huyulan
 * @date 2015-11-10
 */
public class CrawlerImplCnki {
	static Map<String, String> cookies = CookiesUtils.cookies("知网");
	static Map<String, String> map;
	static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	
	public static Map<String, String> getContentByURL(String url,WebClient webClient){
		map = new LinkedHashMap<String, String>();
        HtmlPage page = null;
		try {
			Document doc = null;
			page = (HtmlPage)webClient.getPage(url);
			webClient.closeAllWindows();
			doc = Jsoup.parse(page.asXml());
			String html = doc.html();
				if(html.contains("<h1 class=\"xx_title\">")){
					String title = doc.getElementsByClass("xx_title").text();
					map.put("标题", title);
				}
				
				if(html.contains("<div style=\"text-align:center; width:740px; height:30px;\">")){
					String author = doc.getElementsByAttributeValue("style", "text-align:center; width:740px; height:30px;")
							.text();
					map.put("作者", author);	
				}

				if(html.contains("<div style=\"float:left;\">")){
					String journal = doc.getElementsByAttributeValue("style", "float:left;").text();
					map.put("刊名", journal);	
				}

				if(html.contains("class=\"xx_font\">")){
					Elements abs = doc.getElementsByClass("xx_font");
					map.put("摘要", abs.first().text());
//					Elements select = abs.last().select("font");
//					for (Element element : select) {
//						System.out.println(element.text());
//					}
//					regex(abs.text());
				}

		} catch (Exception e) {
			System.out.println("err: " + url);
			e.printStackTrace();
		}
		return map;
		
	}
	public static void regex(String info) {
		info = new String(info.getBytes());// 用GBK编码
		String regex = "[【][\u4e00-\u9fa5]+[】][：]";
		String[] sp = info.split(regex);
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(info);
		int flag = 1;
		while (m.find()) {
			String ss = m.group();
			String re = ss.substring(1, ss.length() - 2);
//			map.put(re, sp[flag]);
			map.put(re, ss);
			flag++;
		}
	}

}
