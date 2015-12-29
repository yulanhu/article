package com.article.zhiwang;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author huyulan
 * @date 2015-11-9
 */
public class GetContentOfBaiduxueshu {
	public static void readerFile1(String filename) {
		BufferedReader br = null;
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("E:\\分类后url\\weipu\\weipu_final_1.txt", true)));
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"utf-8"));
			String line = null;
			Map<String,String> map= new LinkedHashMap<String,String>();
			String []str = {"name","url","标题","摘要","刊名","作者","作者单位","关键词"};
			while ((line = br.readLine()) != null) {
				
				String[] split = line.split("\t",2);
				if(split[0].equals("name")){
					map.put(split[0], split[1]);	
					while ((line = br.readLine()).length()!=0) {
						String[] split2 = line.split("\t",2);
						if(split2.length == 2 && split2[1].trim().length() != 0){
							map.put(split2[0], split2[1]);	
						}else{
							map.put(split2[0], "N/A");
						}
						
					}
				}
				StringBuffer sb = new StringBuffer();
				for (String string : str) {
					String mapString = map.get(string);
					if(mapString == null)
						mapString = "N/A";
					sb.append(mapString + "\t");	
				}
//				System.out.println(sb.toString().substring(0, sb.toString().length()-1));
				bw.write(sb.toString().substring(0, sb.toString().length()-1)+ "\r\n");
				bw.flush();
				map= new LinkedHashMap<String,String>();
				
//				System.out.println(">>>>>>");
				
				
		}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
public static void main(String[] args) {
	String str = "E:\\分类后url\\weipu\\weipu_1218.txt";
	readerFile1(str);
	}
}
