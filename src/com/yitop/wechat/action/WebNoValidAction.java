package com.yitop.wechat.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yitop.wechat.exception.WxException;
import com.yitop.wechat.server.WeixinToolKit;
import com.yitop.wechat.util.Configure;
import com.yitop.wechat.util.SessionLoggerFactory;
import com.yitop.wechat.util.StringUtils;

@Controller
@RequestMapping("/webNoValidAction")
public class WebNoValidAction extends BaseController{
	
	@Resource
	private WeixinToolKit weixinToolKit;
	
	private static final Logger logger = SessionLoggerFactory.getLogger(WebNoValidAction.class);

	@RequestMapping("/getWxUserInfo")
	public ModelAndView getWxUserInfo(HttpServletRequest request, HttpServletResponse response) throws WxException{
		logger.info("getWxUserInfo() - start");
		ModelAndView modelAndView = new ModelAndView("one/person");
		String openId = (String) request.getAttribute("openId");
		if (StringUtils.isEmptyByTrim(openId) && request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals("openId")) {
					logger.info("getWxUserInfo() - cookie.openId=" + cookie.getValue());
					openId = cookie.getValue();
					break;
				}
			}
		}
		modelAndView.addObject("openId", openId);
		Map<String,String> jssdkInfo =  weixinToolKit.creatJssdkInfo(Configure.shareHttps + "/getWxUserInfo");
        modelAndView.addObject("jssdkInfo",jssdkInfo);
		logger.info("getWxUserInfo() - openId = " +openId);
		return modelAndView;
	}
	
}
