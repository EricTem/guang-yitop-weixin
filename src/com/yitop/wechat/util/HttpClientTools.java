/**
 * 
 */
package com.yitop.wechat.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import com.yitop.wechat.util.SessionLoggerFactory;

import net.sf.json.JSONObject;

/**
 * @author zhaoXL
 * @date 2014年11月11日
 * @version 1.0  
 * @since 1.0
 */
public class HttpClientTools {
	
	private static final Logger logger = SessionLoggerFactory.getLogger(HttpClientTools.class);
	
	final static private CloseableHttpClient httpClient;
	
	static {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		//连接池最大生成连接数200
		cm.setMaxTotal(200);
	
		// 创建httpClient
		httpClient = HttpClients.custom().setConnectionManager(cm).build();
	}
	
	
	public static JSONObject GetRequst(Map<String,String> Params,String Url){
		
		JSONObject result = new JSONObject();
		result.put("code", "9999");
		
		StringBuffer bufferUrl = new StringBuffer(Url);
		if(Params!=null&&!Params.isEmpty()){
			bufferUrl.append("?");
			for (String s : Params.keySet()) {
				bufferUrl.append(s).append("=").append(Params.get(s)).append("&");
			}
			bufferUrl.deleteCharAt(bufferUrl.length()-1);
		}
		HttpGet httpGet = new HttpGet(bufferUrl.toString());
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(2000).build();
		httpGet.setConfig(requestConfig);
		
		try {
			//执行get请求
			logger.info("开始发送请求...");
			logger.info("请求URL信息：{}",bufferUrl);
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			//获取响应消息实体
			HttpEntity entity = httpResponse.getEntity();
			logger.info("发送返回.");
			//响应状态
			logger.info("status:" + httpResponse.getStatusLine());
			//判断响应实体是否为空
			if (entity != null) {
				logger.info("contentEncoding:" + entity.getContentEncoding());
				String entityStr = EntityUtils.toString(entity, "UTF-8");
				result = JSONObject.fromObject(entityStr);
				logger.info("response content:\r\n" + result +"\r\n");
			}
		} catch (IOException e) {
			result.put("code", "-1");
			result.put("msg", "请求失败");
			logger.info(e.getMessage());
		} finally {
			//关闭流并释放资源
			httpGet.releaseConnection();
		} 
		return result;
	}
	
	public static JSONObject PostRequst(Map<String, String> postParam,String url){
		
		JSONObject result = new JSONObject();
		result.put("code", "9999");
		result.put("msg", "失败");
		
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(2000).build();
		httpPost.setConfig(requestConfig);
		
		if (postParam != null) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for (String key : postParam.keySet()) {
				params.add(new BasicNameValuePair(key, postParam.get(key)));
			}
			try {
				HttpEntity reqEntity = new UrlEncodedFormEntity(params, "utf-8");
				httpPost.setEntity(reqEntity);
			} catch (Exception e) {
				result.put("code", "1");
				result.put("msg", "请求数据有误");
			}
		}

		try {
			//执行get请求
			logger.info("开始发送请求...");
			logger.info("请求URL信息：{}",url);
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			//获取响应消息实体
			HttpEntity entity = httpResponse.getEntity();
			logger.info("发送返回.");
			//响应状态
			logger.info("status:" + httpResponse.getStatusLine());
			//判断响应实体是否为空
			if (entity != null) {
				logger.info("contentEncoding:" + entity.getContentEncoding());
				String entityStr = EntityUtils.toString(entity, "UTF-8");
				result = JSONObject.fromObject(entityStr);
				logger.info("response content:\r\n" + result +"\r\n");
			}
			
		} catch (IOException e) {
			result.put("code", "-1");
			result.put("msg", "请求失败");
			logger.info(e.getMessage());
		} finally {
			//关闭流并释放资源
			httpPost.releaseConnection();
		}
		return result;
		
	}
	
	public static String PostRequst(String StringObj,String url){
		
		String result = null;
		
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(2000).build();
		httpPost.setConfig(requestConfig);
		
		if (StringObj != null) {
			logger.info("requst content:\r\n" + StringObj +"\r\n");
			HttpEntity reqEntity = new StringEntity(StringObj, "UTF-8");
			httpPost.addHeader("Content-Type", "text/xml");
			httpPost.setEntity(reqEntity);
		}

		try {
			//执行get请求
			logger.info("开始发送请求...");
			logger.info("请求URL信息：{}",url);
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			//获取响应消息实体
			HttpEntity entity = httpResponse.getEntity();
			logger.info("发送返回.");
			//响应状态
			logger.info("status:" + httpResponse.getStatusLine());
			//判断响应实体是否为空
			if (entity != null) {
				logger.info("contentEncoding:" + entity.getContentEncoding());
				result = EntityUtils.toString(entity, "UTF-8");
				logger.info("response content:\r\n" + result +"\r\n");
			}
			
		} catch (IOException e) {
			logger.info(e.getMessage());
		} finally {
			//关闭流并释放资源
			httpPost.abort();
			httpPost.releaseConnection();
		}
		return result;
		
	}
	
	
}
