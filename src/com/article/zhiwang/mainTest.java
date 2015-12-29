package com.article.zhiwang;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

public class mainTest {
	public static List<String> readerFile(String filename) {
		List<String> list = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"utf-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void main(String[] args) {
		WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_11);
	    webClient.getOptions().setCssEnabled(false);  
        webClient.getOptions().setJavaScriptEnabled(false);  
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setAppletEnabled(false);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setUseInsecureSSL(false);
        webClient.getOptions().setTimeout(60000);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        
		String readepath = "E:\\分类后url\\logg.txt";
		String urlwritepath = "E:\\分类后url\\cnki_logg.txt";
		List<String> list = new ArrayList<String>();
		list = readerFile(readepath);
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(urlwritepath, true)));
			for (String string : list) {
				String[] split = string.split("\t",2);
				String name= split[0];
				String url = split[1];
				Map<String, String> contentByURL = CrawlerImplCnki.getContentByURL(url,webClient);
				StringBuffer sb = new StringBuffer();
				sb.append("name" + "\t" + name + "\r\n");
				sb.append("url" + "\t" + url + "\r\n");
				for (Entry<String, String> entry: contentByURL.entrySet()) {
					sb.append(entry.getKey() + "\t" + entry.getValue() +"\r\n");
//					System.out.println(entry.getKey() + "\t" + entry.getValue() +"\r\n");
				}
				sb.append("\r\n");
				bw.write(sb.toString());
				bw.flush();
//				System.out.println(sb.toString());
//				Thread.sleep(1000);
			}	
			webClient.closeAllWindows();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
