package com.article.wangfang;


import java.io.IOException;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;

public class CookiesUtils {
	private static final String LoginwangfangUrl = "http://login.wanfangdata.com.cn/login.aspx";
	public CookiesUtils() {
	}

	public static Map<String, String> cookies(String websiteName) {
		if (websiteName.equals("万方")) {
			return getcookies(LoginwangfangUrl);
		}
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
