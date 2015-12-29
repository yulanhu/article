package com.article.zhiwang;


import java.io.IOException;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;

public class CookiesUtils {
	private static final String LogincnkiUrl = "http://epub.cnki.net/KNS/logindigital.aspx?ParentLocation=/kns";

	public CookiesUtils() {
	}

	public static Map<String, String> cookies(String websiteName) {
		if (websiteName.equals("知网")) {
			return getcookies(LogincnkiUrl);
		}else
			return null;
	}

	private  static Map<String, String> getcookies(String url) {
		Map<String, String> cookies = null;
		Response res;
		try {
			res = Jsoup.connect(url).timeout(60000).execute();
			cookies = res.cookies();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cookies;
	}

}
