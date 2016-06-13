package com.yitop.wechat.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;

public class Signature {
	
	private static final Logger logger = SessionLoggerFactory.getLogger(Signature.class);

    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }
    
	private static String byteToHex(final byte[] hash) {
	  Formatter formatter = new Formatter();
	  for (byte b : hash)
	  {
	      formatter.format("%02x", b);
	  }
	  String result = formatter.toString();
	  formatter.close();
	  return result;
	}
    
	 public static String create_nonce_str() {
	  return UUID.randomUUID().toString();
	}

	
	public static String create_timestamp() {
	  return Long.toString(System.currentTimeMillis() / 1000);
	}
    
//    public static Map<String, String> signEditAddress(String appId, String accessToken,
//    		String url) {
//        Map<String, String> ret = new HashMap<String, String>();
//        String timeStamp = create_timestamp();
//        String nonceStr = create_nonce_str();
//        String addSign = "";
//        String string1;
//
//        //注意这里参数名必须全部小写，且必须有序
//        string1 = "accesstoken=" + accessToken +
//                  "&appid=" + appId +
//                  "&noncestr=" + nonceStr +
//                  "&timestamp=" + timeStamp +
//                  "&url=" + url;
////        System.out.println(string1);
//
//        try
//        {
//            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
//            crypt.reset();
//            crypt.update(string1.getBytes("UTF-8"));
//            addSign = byteToHex(crypt.digest());
//        }
//        catch (NoSuchAlgorithmException e)
//        {
//            e.printStackTrace();
//        }
//        catch (UnsupportedEncodingException e)
//        {
//            e.printStackTrace();
//        }
//
//        ret.put("appId", appId);
//        ret.put("scope", "jsapi_address");
//        ret.put("signType", "sha1");
//        ret.put("addrSign", addSign);
//        ret.put("timeStamp", timeStamp);
//        ret.put("nonceStr", nonceStr);
//
//        return ret;
//    }
//
//    public static Map<String, String> sign_winxin7moor(String token, String openId) {
//        Map<String, String> ret = new HashMap<String, String>();
//        String nonce_str = create_nonce_str();
//        String timestamp = create_timestamp();
//        String string1;
//        String signature = "";
//
//        String[] array = new String[] { token, timestamp, nonce_str, openId };
//		StringBuffer sb = new StringBuffer();
//		// 字符串排序
//		Arrays.sort(array);
//		for (int i = 0; i < 4; i++) {
//			sb.append(array[i]);
//		}
//		string1 = sb.toString();
//        try
//        {
//            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
//            crypt.reset();
//            crypt.update(string1.getBytes("UTF-8"));
//            signature = byteToHex(crypt.digest());
//        }
//        catch (NoSuchAlgorithmException e)
//        {
//            e.printStackTrace();
//        }
//        catch (UnsupportedEncodingException e)
//        {
//            e.printStackTrace();
//        }
//
//        ret.put("nonce", nonce_str);
//        ret.put("timestamp", timestamp);
//        ret.put("msg_signature", signature);
//
//        return ret;
//    }
//    
//    public static Map<String, String> sign_winxin7moor(String token, String openId, String nonce_str, String timestamp) {
//        Map<String, String> ret = new HashMap<String, String>();
//        String string1;
//        String signature = "";
//
//        String[] array = new String[] { token, timestamp, nonce_str, openId };
//		StringBuffer sb = new StringBuffer();
//		// 字符串排序
//		Arrays.sort(array);
//		for (int i = 0; i < 4; i++) {
//			sb.append(array[i]);
//		}
//		string1 = sb.toString();
//        try
//        {
//            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
//            crypt.reset();
//            crypt.update(string1.getBytes("UTF-8"));
//            signature = byteToHex(crypt.digest());
//        }
//        catch (NoSuchAlgorithmException e)
//        {
//            e.printStackTrace();
//        }
//        catch (UnsupportedEncodingException e)
//        {
//            e.printStackTrace();
//        }
//
//        ret.put("nonce", nonce_str);
//        ret.put("timestamp", timestamp);
//        ret.put("msg_signature", signature);
//
//        return ret;
//    }
//    
//    /**
//     * 获取一定长度的随机字符串
//     * @param length 指定字符串长度
//     * @return 一定长度的字符串
//     */
//    public static String getRandomStringByLength(int length) {
//        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
//        Random random = new Random();
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < length; i++) {
//            int number = random.nextInt(base.length());
//            sb.append(base.charAt(number));
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 签名算法
//     * @param o 要参与签名的数据对象
//     * @return 签名
//     * @throws IllegalAccessException
//     */
//    public static String getSign(Object o) throws IllegalAccessException {
//        ArrayList<String> list = new ArrayList<String>();
//        Class<? extends Object> cls = o.getClass();
//        Field[] fields = cls.getDeclaredFields();
//        for (Field f : fields) {
//            f.setAccessible(true);
//            if (f.get(o) != null && f.get(o) != "") {
//                list.add(f.getName() + "=" + f.get(o) + "&");
//            }
//        }
//        int size = list.size();
//        String [] arrayToSort = list.toArray(new String[size]);
//        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
//        StringBuilder sb = new StringBuilder();
//        for(int i = 0; i < size; i ++) {
//            sb.append(arrayToSort[i]);
//        }
//        String result = sb.toString();
//        result += "key=" + configure.getKey();
//        logger.info("Sign Before MD5:" + result);
//        result = MD5.MD5Encode(result).toUpperCase();
//        logger.info("Sign Result:" + result);
//        return result;
//    }
//
//    
//    public static String getSign(Map<String,Object> map){
//    	
//        ArrayList<String> list = new ArrayList<String>();
//        for(Map.Entry<String,Object> entry:map.entrySet()){
//            if(entry.getValue()!=""){
//                list.add(entry.getKey() + "=" + entry.getValue() + "&");
//            }
//        }
//        int size = list.size();
//        String [] arrayToSort = list.toArray(new String[size]);
//        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
//        StringBuilder sb = new StringBuilder();
//        for(int i = 0; i < size; i ++) {
//            sb.append(arrayToSort[i]);
//        }
//        String result = sb.toString();
//        Configure configure = ConfigureManagerUtil.loadConfigure4Local();
//        result += "key=" + configure.getKey();
//        //logger.log("Sign Before MD5:" + result);
//        result = MD5.MD5Encode(result).toUpperCase();
//        //logger.log("Sign Result:" + result);
//        return result;
//    }
//
//    /**
//     * 从API返回的XML数据里面重新计算一次签名
//     * @param responseString API返回的XML数据
//     * @return 新鲜出炉的签名
//     * @throws ParserConfigurationException
//     * @throws IOException
//     * @throws SAXException
//     */
//    public static String getSignFromResponseString(String responseString) throws IOException, SAXException, ParserConfigurationException {
//        Map<String,Object> map = MessageUtil.getMapFromXML(responseString);
//        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
//        map.put("sign","");
//        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
//        return Signature.getSign(map);
//    }
//
//    /**
//     * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
//     * @param responseString API返回的XML数据字符串
//     * @return API签名是否合法
//     * @throws ParserConfigurationException
//     * @throws IOException
//     * @throws SAXException
//     */
//    public static boolean checkIsSignValidFromResponseString(String responseString) throws ParserConfigurationException, IOException, SAXException {
//
//        Map<String,Object> map = MessageUtil.getMapFromXML(responseString);
//        logger.info(map.toString());
//        if (map.containsKey("appid")) {
//        	String appId = map.get("appid").toString();
//        	Configure configure = ConfigureManagerUtil.loadConfigure4AppId(appId);
//			ConfigureManagerUtil.putLocalAppUserId(configure.getAppUserId());
//        }
//        String signFromAPIResponse = map.get("sign").toString();
//        if(signFromAPIResponse=="" || signFromAPIResponse == null){
//            logger.info("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
//            return false;
//        }
//        logger.info("服务器回包里面的签名是:" + signFromAPIResponse);
//        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
//        map.put("sign","");
//        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
//        String signForAPIResponse = Signature.getSign(map);
//
//        if(!signForAPIResponse.equals(signFromAPIResponse)){
//            //签名验不过，表示这个API返回的数据有可能已经被篡改了
//            logger.info("API返回的数据签名验证不通过，有可能被第三方篡改!!!");
//            return false;
//        }
//        logger.info("恭喜，API返回的数据签名验证通过!!!");
//        return true;
//    }
//    
//    public static boolean checkIsSignValidFromHb(String time,String hbId,String nor){
//		String jmStr = time + "_" + hbId + "_" + SignUtil.hbSecret;
//		return StringUtils.md5(jmStr).equals(nor);
//    }
//    
//    public static String creatNor(String time,String hbId){
//		String jmStr = time + "_" + hbId + "_" + SignUtil.hbSecret;
//		String nor = StringUtils.md5(jmStr);
//		return nor;
//    }
//    
}
