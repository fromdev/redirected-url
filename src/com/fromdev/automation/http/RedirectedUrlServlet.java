package com.fromdev.automation.http;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fromdev.automation.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
public class RedirectedUrlServlet extends HttpServlet {
	private Gson gson = new Gson();

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String url = req.getParameter("url");
		System.out.println(req.getParameterMap());
		System.out.println(url);
		if (StringUtil.notNullOrEmpty(url)) {
			url = URLDecoder.decode(url, "UTF-8");
			System.out.println("decoded: " + url);
			url = StringUtil.getRedirectedUrl(url);
		} else {
			url = "";
		}
		JsonObject json = new JsonObject();
		json.addProperty("url", url);
		String callback = req.getParameter("callback");
		if (StringUtil.notNullOrEmpty(callback)) {
			resp.setContentType("text/javascript");
			resp.getWriter().println(callback + "(" + gson.toJson(json) + ");");

		} else {
			resp.setContentType("application/json");
			resp.getWriter().println(gson.toJson(json));
		}
		System.out.println("done");
	}
}
