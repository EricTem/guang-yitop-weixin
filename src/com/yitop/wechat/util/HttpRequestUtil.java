package com.yitop.wechat.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestUtil {
	
//	private static final Logger logger = SessionLoggerFactory.getLogger(HttpRequestUtil.class);

	public static String httpRequestUrl(HttpServletRequest request) {
		return Configure.serviceRootUrl
				+ request.getRequestURI().replaceFirst(
						request.getContextPath(), "");
	}

	public static String redirectAuthorize(String redirect_uri, String scope,
			String state, Map<String, String[]> parameterMap)
			throws UnsupportedEncodingException {
		StringBuffer buffer = new StringBuffer(Configure.AUTHORIZE_API);
		buffer.append("?appid=").append(Configure.appID);
		if (parameterMap != null && !parameterMap.isEmpty()) {
			StringBuffer bufferUri = new StringBuffer(redirect_uri + "?");
			for (String str : parameterMap.keySet()) {
				if (str.equals("code") || str.equals("state")) {
					continue;
				}
				bufferUri.append(str + "=" + parameterMap.get(str)[0]).append(
						"&");
			}
			redirect_uri = bufferUri.deleteCharAt(bufferUri.length() - 1)
					.toString();
		}
		buffer.append("&redirect_uri=").append(
				URLEncoder.encode(redirect_uri, "UTF-8"));
		buffer.append("&response_type=code&scope=").append(scope);
		if (StringUtils.isNotEmptyByTrim(state)) {
			buffer.append("&state=").append(state);
		}
		buffer.append("#wechat_redirect");
		return buffer.toString();
	}
	
	public static String redirectUrl(String redirect_uri,Map<String, String[]> parameterMap) throws UnsupportedEncodingException{
		StringBuffer buffer = new StringBuffer();
		if (parameterMap != null && !parameterMap.isEmpty()) {
			StringBuffer bufferUri = new StringBuffer(redirect_uri + "?");
			for (String str : parameterMap.keySet()) {
				bufferUri.append(str + "=" + parameterMap.get(str)[0]).append(
						"&");
			}
			redirect_uri = bufferUri.deleteCharAt(bufferUri.length() - 1)
					.toString();
			buffer.append(redirect_uri);
		}
		return buffer.toString();
	}
	
	public static String loadRequestContent(HttpServletRequest request) {
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			is = request.getInputStream();
			baos = new ByteArrayOutputStream();
			byte[] data = new byte[1024*1024];
			int n = 0;
			int total = 0;
			while ((n = is.read(data)) != -1) {
				total += n;
				baos.write(data, 0, n);
			}
			if (total > 0) {
				return new String(baos.toByteArray());
			}
		} catch (IOException e) {
			//e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.close();
					baos = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return "";
	}
}
