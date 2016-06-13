package com.yitop.wechat.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 14:40
 * 这里放置各种配置数据
 */
public class Configure {
	
	private static final String lock = Configure.class.getName();
	
	private static String version = "20160318_3";
	public static String JsVersionTail = "?v=" + version;
	public static String CssVersionTail = "?v=" + version;
	
	//车小丫基础购买金额
	public static final Integer CXY_BASE_PRICE = 2880;
	
	//这个就是自己要保管好的私有Key了（切记只能放在自己的后台代码里，不能放在任何可能被看到源代码的客户端程序中）
	// 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
	// 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改

	
	public static String key;// = "20Yit15oPwei08xin30ikuek54rfjfeh";//"16a301c7939fdc362d1490d351d8fd59";
	
	// 与接口配置信息中的Token要一致  
	
	public static String secret;// = "85bcf0e86ea2bf1a09622f4c820f782d"; //"16a301c7939fdc362d1490d351d8fd59";

	//微信分配的公众号ID（开通公众号之后可以获取到）
	public static String appID;// = "wx0b6b183625dd1ce4"; //"wx56022bf3c4ac48df";
	
	public static String appUserId;

	//微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
	public static String mchID;// = "1254379901";
	
	public static String attentionUrl;
	
	public static String token;// = "weixin_yitop_test20150501";
	
	public static String who;// = "/xing-yitop-winxin";
	
	public static String serviceRoot;// = "http://wxtest.chexiaoy.com";
	
	public static String serviceRootUrl;// = serviceRoot + who;
	
	//接收支付异步通知回调地址
	public static  String notifyUrl;// = serviceRootUrl + "/cxy/weixinAction/callback";
	
	public static  String shareHttps;// = serviceRootUrl + "/cxy/webAction";
	
	
	public static boolean produce;
	
	public static String openkey="20Yit15oPwei09xin16ikuek54njiuhb";
	
	public static String innerGwUrl;

	
	public static String apiSecret = "yfoak@&nfJGUYFSAD73142NFLVZSucsFWE";
	
	//以下是几个API的路径：
	
	//1) 取网页授权access_token
	public static String OAuth_ACCESS_TOKEN_API = "https://api.weixin.qq.com/sns/oauth2/access_token";
	
	//2) 取用户信息
	public static String USERINFO_API = "https://api.weixin.qq.com/sns/userinfo";
	
	//3) 取普通access_token
	public static String ACCESS_TOKEN_API = "https://api.weixin.qq.com/cgi-bin/token";
	
	//4) 取普通ticket
	public static String TICKET_API = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	
	//5) 网页授权入口
	public static String AUTHORIZE_API = "https://open.weixin.qq.com/connect/oauth2/authorize";
	
	//6) 取普通ticket拉取用户信息及判断是否关注
	public static String USER_INFO_API = "https://api.weixin.qq.com/cgi-bin/user/info";
	
	//7) JsApI支付统计下单API
	public static String PAY_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	//8) JsApI查询订单API
	public static String PAYQUERY_API = "https://api.mch.weixin.qq.com/pay/orderquery";
	
	
	static {
		synchronized (lock) {
			InputStream ins = Configure.class.getClassLoader().getResourceAsStream("z-configure.properties");
			
			Properties properties = new Properties();
			try {
				properties.load(ins);
			} catch (IOException e) {
			}
			token = properties.getProperty("TOKEN");
			who = properties.getProperty("WHO");
			serviceRoot = properties.getProperty("SERVICEROOT");
			produce = Boolean.valueOf(properties.getProperty("PRODUCE"));
			serviceRootUrl = Configure.serviceRoot + Configure.who;
			notifyUrl = Configure.serviceRootUrl + "/cxy/weixinAction/callback";
			shareHttps = Configure.serviceRootUrl + "/cxy/webNoValidAction";
			
			innerGwUrl = properties.getProperty("INNERGWURL");
			appID = properties.getProperty("APPID");
			key = properties.getProperty("KEY");
			secret = properties.getProperty("SECRET");
			mchID = properties.getProperty("MCHID");
			appUserId = properties.getProperty("APPUSERID");
			attentionUrl = properties.getProperty("ATTENTIONURL");
		}
	}

}
