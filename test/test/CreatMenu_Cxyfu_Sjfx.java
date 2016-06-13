package test;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;


/**@author zhaoXL
 * 2014年1月10日
 */
public class CreatMenu_Cxyfu_Sjfx {
	
	private static String APPID = "wx975c37448f9ba7df";
	private static String SECRET = "7139ca4b3323b9f55850021bff2728d2";
	
	public static void main(String[] args) {
		CreatMenu_Cxyfu_Sjfx example = new CreatMenu_Cxyfu_Sjfx();
		String access_token = "o-5yaAqp4-il3hX7HoSa0QSc_WFNQOUxrdroxTRqDpJdoK1oRvo7FxMFm1uRc4KuwWbB9_FSoACAb5XsoD5hB6MHz6lLocMIcrgSmObmySIlWQdb6zIVtPno5qeAvHO4HOZaACAMTE";
//		example.GetOpenidMethod(access_token);
//		example.GetDelMenuMethod(access_token);//删除菜单
//		example.postCreatMunuMethod(access_token);//生成菜单
//		example.PostCreatQRCodeMethod(access_token); //生成二维码
		example.GetMenuMethod(access_token); //查询菜单

	}

	
	/**
	 * 创建菜单
	 */
	private String postCreatMunuMethod(String token){
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+token;
		
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		
		HttpPost httpPost = new HttpPost(url);
		String dateTree = "{\"button\":[{\"name\":\"卡罗拉\",\"sub_button\":[{\"type\":\"view\",\"name\":\"雷凌\",\"url\":\"http://wxtest.chexiaoy.com/yitop-weixin/cxy/webAction/showZnhsj?pid="+APPID+"\"},{\"type\":\"view\",\"name\":\"皇冠\",\"url\":\"http://wxtest.chexiaoy.com/yitop-weixin/cxy/webAction/preBuyCxy?pid="+APPID+"\"},{\"type\":\"view\",\"name\":\"安装指引\",\"url\":\"http://wxtest.chexiaoy.com/yitop-weixin/cxy/webNoValidAction/loadInstallGuide\"},{\"type\":\"click\",\"name\":\"锐志\",\"key\":\"MENU_FAQ\"}]},{\"type\":\"view\",\"name\":\"缤智\",\"url\":\"http://wxtest.chexiaoy.com/guang-yitop-weixin/cxy/webNoValidAction/getWxUserInfo\"},{\"type\":\"view\",\"name\":\"雅阁\",\"url\":\"http://wxtest.chexiaoy.com/yitop-weixin/cxy/webAction/wxHomeInfo?pid="+APPID+"\"}]}";
		StringEntity reqEntity = new StringEntity(dateTree,"utf-8");
		httpPost.setEntity(reqEntity);
		
		String errcode = "";
		try {
			//执行get请求
			CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpPost);
			//获取响应消息实体
			HttpEntity entity = httpResponse.getEntity();
			//响应状态
			System.out.println("status:" + httpResponse.getStatusLine());
			//判断响应实体是否为空
			if (entity != null) {
				System.out.println("contentEncoding:" + entity.getContentEncoding());
				String result = EntityUtils.toString(entity, "UTF-8");
				System.out.println("response content:" + result +"\r\n");
				JSONObject jobect =JSONObject.fromObject(result);
				errcode = jobect.getString("errcode");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				//关闭流并释放资源
				closeableHttpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
	  return errcode;
	}
	
	/**
	 * 取access_token
	 * @return
	 */
	private String GetTokenMethod(){
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+SECRET; // wx831d2680e1d5e987  68bce767be867e18fb7162a2944bec1a

		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(url);
		
		String access_token = "";
		
		try {
			//执行get请求
			CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpGet);
			//获取响应消息实体
			HttpEntity entity = httpResponse.getEntity();
			//响应状态
			System.out.println("status:" + httpResponse.getStatusLine());
			//判断响应实体是否为空
			if (entity != null) {
				System.out.println("contentEncoding:" + entity.getContentEncoding());
				String result = EntityUtils.toString(entity, "UTF-8");
				System.out.println("response content:" + result +"\r\n");
				JSONObject jobect =JSONObject.fromObject(result);
				access_token = jobect.getString("access_token");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				//关闭流并释放资源
				closeableHttpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	  return access_token;
	}
	
	/**
	 * 查询菜单
	 * @param token
	 * @return
	 */
	private String GetMenuMethod(String token){
		String url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token="+token;

		CloseableHttpClient client = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(url);
		
		String MenuTree = "";
		
		try {
			CloseableHttpResponse resp = client.execute(httpGet);
			HttpEntity entity = resp.getEntity();
			System.out.println("status:" + resp.getStatusLine());
			if(entity != null) {
				System.out.println("contentEncoding:" + entity.getContentEncoding());
				String result = EntityUtils.toString(entity, "UTF-8");
				System.out.println("response content:" + result +"\r\n");
				JSONObject jobect =JSONObject.fromObject(result);
				MenuTree = jobect.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

	  return MenuTree;
	}
	
	/**
	 * 刪除菜单
	 * @param token
	 * @return
	 */
	private String GetDelMenuMethod(String token){
		String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+token;
		
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(url);
		
		String errcode = "";
		
		try {
			//执行get请求
			CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpGet);
			//获取响应消息实体
			HttpEntity entity = httpResponse.getEntity();
			//响应状态
			System.out.println("status:" + httpResponse.getStatusLine());
			//判断响应实体是否为空
			if (entity != null) {
				System.out.println("contentEncoding:" + entity.getContentEncoding());
				String result = EntityUtils.toString(entity, "UTF-8");
				System.out.println("response content:" + result +"\r\n");
				JSONObject jobect =JSONObject.fromObject(result);
				errcode = jobect.getString("errcode");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				//关闭流并释放资源
				closeableHttpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	  return errcode;
	}
	
	/**
	 * 生成二维码图标
	 *//*
	private String PostCreatQRCodeMethod(String token){
		def url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+token;
		
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		
		HttpPost httpPost = new HttpPost(url);
		def dateTree = """{"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_id": 10000}}}""";
		StringEntity reqEntity = new StringEntity(dateTree);
		httpPost.setEntity(reqEntity);
		
		def ticket = "";
		try {
			//执行get请求
			CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpPost);
			//获取响应消息实体
			HttpEntity entity = httpResponse.getEntity();
			//响应状态
//			System.out.println("status:" + httpResponse.getStatusLine());
			//判断响应实体是否为空
			if (entity != null) {
				System.out.println("contentEncoding:" + entity.getContentEncoding());
				String result = EntityUtils.toString(entity, "UTF-8");
				System.out.println("response content:" + result +"\r\n");
				JSONObject jobect =JSONObject.fromObject(result);
				ticket = jobect.getString("ticket");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				//关闭流并释放资源
				closeableHttpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ticket;
	}*/
	
	/**
	 * 取access_token
	 * @return
	 */
	private String GetOpenidMethod(String token){
		String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token="+token+"&next_openid=oQjrGuDh4Q5gp7EcX3dRUBZOl6uY";

		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(url);
		
		String access_token = "";
		
		try {
			//执行get请求
			CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpGet);
			//获取响应消息实体
			HttpEntity entity = httpResponse.getEntity();
			//响应状态
			System.out.println("status:" + httpResponse.getStatusLine());
			//判断响应实体是否为空
			if (entity != null) {
				System.out.println("contentEncoding:" + entity.getContentEncoding());
				String result = EntityUtils.toString(entity, "UTF-8");
				System.out.println("response content:" + result +"\r\n");
				JSONObject jobect =JSONObject.fromObject(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				//关闭流并释放资源
				closeableHttpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	  return access_token;
	}
}
