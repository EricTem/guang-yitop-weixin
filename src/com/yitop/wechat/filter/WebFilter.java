package com.yitop.wechat.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yitop.wechat.util.Configure;
import com.yitop.wechat.util.HttpClientTools;
import com.yitop.wechat.util.HttpRequestUtil;
import com.yitop.wechat.util.SessionLoggerFactory;

import net.sf.json.JSONObject;

public class WebFilter implements HandlerInterceptor {
	
	private static final Logger logger = SessionLoggerFactory.getLogger(WebFilter.class);
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object hele) throws Exception {
//		String sessionId = request.getSession().getId();
//		String publicAppId = request.getParameter("pid");
		//请求的路径
		String rdUrl = HttpRequestUtil.httpRequestUrl(request);
		Map<String, String[]> pMap = new HashMap<String, String[]>();
		pMap.putAll(request.getParameterMap());
		if (pMap.containsKey("pid")) {
			pMap.remove("pid");
		}
		String code = request.getParameter("code");
		String openId = null;
//		openId="oUc8ms35dgeJQCKCxnT2hj6_tUFo";
//		SessionUtil.setAttr(sessionId, "openId", openId);
		//缓存没openId，去cookies拿
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals("openId")) {
					logger.info("preHandle() - openId is null,cookie.openId=" + cookie.getValue());
					openId = cookie.getValue();
					break;
				}
			}
		}
	
		logger.info("preHandle() web openId=" + openId + ", code=" + code);
		if(code!=null){
			JSONObject json = new JSONObject();
			Map<String,String> params = new HashMap<String, String>();
			params.put("appid",Configure.appID);
			params.put("secret",Configure.secret);
			params.put("code",code);
			params.put("grant_type","authorization_code");
			
			json = HttpClientTools.GetRequst(params, Configure.OAuth_ACCESS_TOKEN_API);
			logger.info("getWxUserInfo() - json = " + json);
			openId = json.getString("openid");
			//将openId放入cookie
			logger.info("preHandle() - set cookie.openId=" +openId);
			request.setAttribute("openId", openId);
			Cookie cookie = new Cookie("openId", openId);
			cookie.setMaxAge(60 * 60 * 24 * 5);
			cookie.setPath("/");
			response.addCookie(cookie);
		}else{
			if(openId==null){
				String url = HttpRequestUtil.redirectAuthorize(rdUrl,"snsapi_base", "2", pMap);
				logger.info("preHandle() - redirect url=" + url);
				response.sendRedirect(url);
				return false;
			}
		}

//		if(weiXinUser==null){
//			@SuppressWarnings("unchecked")
//			String url = HttpRequestUtil.redirectAuthorize(HttpRequestUtil.httpRequestUrl(request),"snsapi_userinfo", "0", pMap);
//			logger.info("preHandle() - redirect url=" + url);
//			response.sendRedirect(url);
//			return false;
//		}
//		
//		if(StringUtils.isEmpty(weiXinUser.getName())){
//			@SuppressWarnings("unchecked")
//			String url = HttpRequestUtil.redirectAuthorize(HttpRequestUtil.httpRequestUrl(request),"snsapi_userinfo", "1", pMap);
//			logger.info("preHandle() - redirect url=" + url);
//			response.sendRedirect(url);
//			return false;
//		}
//		
//		if(StringUtils.isEmpty(openId)){
//			for (int i = 0; i < 10; i++) {
//				SessionUtil.setAttr(sessionId, "openId", weiXinUser.getUserId());
//				openId = (String) SessionUtil.getAttr(sessionId, "openId");
//				if (StringUtils.isNotEmpty(openId)) {
//					break;
//				} else {
//					logger.warn("preHandle() - sessionId=" + sessionId + ", set openId failed. ");
//					ThreadUtil.sleep(150);
//				}
//			}
//		}
		
		return true;
	}
	
}
