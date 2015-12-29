package com.article.wangfang;

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

public class mainTest {
	public static List<String> readerFile(String filename) {
		List<String> list = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
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
		System.out.println("please INPUT html file name and output file name:");
		List<String> list = new ArrayList<String>();
		list = readerFile(args[0]);
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(args[1], true)));
			for (String string : list) {
				String[] split = string.split("\t",2);
				String name= split[0];
				String url = split[1];
				Map<String, String> contentByURL = CrawlerImplWangfangdata.getContentByURL(url);
				StringBuffer sb = new StringBuffer();
				sb.append("name" + "\t" + name + "\r\n");
				sb.append("url" + "\t" + url + "\r\n");
				for (Entry<String, String> entry: contentByURL.entrySet()) {
					sb.append(entry.getKey() + "\t" + entry.getValue() +"\r\n");
				}
				sb.append("\r\n");
				bw.write(sb.toString());
				bw.flush();
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
