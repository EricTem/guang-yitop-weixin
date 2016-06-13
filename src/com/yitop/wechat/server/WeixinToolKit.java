package com.yitop.wechat.server;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.yitop.wechat.exception.WxException;
import com.yitop.wechat.util.Configure;
import com.yitop.wechat.util.HttpClientTools;
import com.yitop.wechat.util.SessionLoggerFactory;
import com.yitop.wechat.util.Signature;

import net.sf.json.JSONObject;

public class WeixinToolKit {
	
	private static final Logger logger = SessionLoggerFactory.getLogger(WeixinToolKit.class);
	
	public Map<String, String> creatJssdkInfo(String url) throws WxException{
		logger.info("creatJssdkInfo() - url=" + url);
		
		Map<String,String> params = new HashMap<String, String>();
		JSONObject result = null;
		String ticket = null;
		String token = null;
		for (int i = 0; i < 2; i++) {
			if(token==null){
				
				params.put("grant_type","client_credential");
				params.put("appid",Configure.appID);
				params.put("secret",Configure.secret);
				
				result = HttpClientTools.GetRequst(params, Configure.ACCESS_TOKEN_API);
				
				if(result.containsKey("errcode")&&!result.getString("errcode").equals("0")){
					throw new WxException(result.getString("errcode"),result.getString("errmsg"));
				}else{
					token = result.getString("access_token");
				}
			}
			logger.info("creatJssdkInfo() - token=" + token);
			
			if(ticket==null){
				params.clear();
				params.put("access_token", token);
				params.put("type","jsapi");
				
				result = HttpClientTools.GetRequst(params, Configure.TICKET_API);
				
				if(result.containsKey("errcode")&&!result.getString("errcode").equals("0")){
					if (i == 1) {
						throw new WxException(result.getString("errcode"),result.getString("errmsg"));
					} else {
						token = null;
					}
				}else{
					ticket = result.getString("ticket");
				}
			} else {
				break;
			}
		}

		logger.info("creatJssdkInfo() - ticket=" + ticket);
		
		params.clear();
		params.put("appId", Configure.appID);
		params.putAll(Signature.sign(ticket, url));
		
		logger.info("creatJssdkInfo() - params=" + params);
		return params;
	}
	
}
