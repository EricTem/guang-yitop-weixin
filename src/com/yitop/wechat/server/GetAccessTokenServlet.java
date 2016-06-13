package com.yitop.wechat.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yitop.wechat.util.Configure;
import com.yitop.wechat.util.HttpClientTools;

public class GetAccessTokenServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7992289012323626171L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = "https://api.weixin.qq.com/cgi-bin/token";
		Map<String,String> reqparam = new HashMap<String,String>();
		reqparam.put("grant_type", "client_credential");
		reqparam.put("appid", Configure.appID);
		reqparam.put("secret", Configure.secret);
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().print(HttpClientTools.GetRequst(reqparam,url).get("access_token").toString()); 
	}

}
