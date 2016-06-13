package com.yitop.wechat.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import com.yitop.wechat.util.SessionLoggerFactory;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class MessageUtil {
	
	private static final Logger logger = SessionLoggerFactory.getLogger(MessageUtil.class);
	
	/**
	 * 返回消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型：音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型：图片
	 */
	public static final String RESP_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 返回消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	
	/**
	 * 请求消息类型：客服
	 */
	public static final String REQ_MESSAGE_TYPE_CUSTOMER = "transfer_customer_service";

	/**
	 * 请求消息类型：推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";

	/**
	 * 事件类型：地理位置上传
	 */
	public static final String EVENT_TYPE_LOCATION = "LOCATION";

	/**
	 * 事件类型：已关注扫二维码
	 */
	public static final String EVENT_TYPE_SCAN = "SCAN";

	@SuppressWarnings("unchecked")
	public static <T> T getObjectFromXML(String xml, Class<T> tClass) {
		// 将从API返回的XML数据映射到Java对象
		XStream xstream = getInstance();
		xstream.processAnnotations(tClass);
		xstream.ignoreUnknownElements();// 暂时忽略掉一些新增的字段
		return (T) xstream.fromXML(xml);
	}

	/**
	 * 文本消息对象转换成xml
	 *
	 * @param textMessage
	 *            文本消息对象
	 * @return xml
	 */
	public static String getXMLFromObject(Object object) {
		XStream xstream = getInstance();
		xstream.processAnnotations(object.getClass());
		xstream.ignoreUnknownElements();
		return xstream.toXML(object);
	}

	/**
	 * 扩展xstream，使其支持CDATA块
	 * 
	 * @date 2013-05-19
	 */
	public static XStream getInstance() {
		XStream xstream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")) {
			public PrettyPrintWriter createWriter(Writer out) {
				return new PrettyPrintWriter(out,getNameCoder()) {
					// 对所有xml节点的转换都增加CDATA标记
					protected void writeText(QuickWriter writer, String text) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					}
				};
			}
		});
		return xstream;
	};

	public static Map<String, Object> getMapFromXML(String xmlString)throws ParserConfigurationException, IOException, SAXException {

		// 这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputStream is = getStringStream(xmlString);
		Document document = builder.parse(is);

		// 获取到document里面的全部结点
		NodeList allNodes = document.getFirstChild().getChildNodes();
		Node node;
		Map<String, Object> map = new HashMap<String, Object>();
		int i = 0;
		while (i < allNodes.getLength()) {
			node = allNodes.item(i);
			if (node instanceof Element) {
				map.put(node.getNodeName(), node.getTextContent());
			}
			i++;
		}
		return map;

	}
	
    public static InputStream getStringStream(String sInputString) throws UnsupportedEncodingException {
        ByteArrayInputStream tInputStringStream = null;
        if (sInputString != null && !sInputString.trim().equals("")) {
            tInputStringStream = new ByteArrayInputStream(sInputString.getBytes("UTF-8"));
        }
        return tInputStringStream;
    }

	public static byte[] readInput(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int len = 0;
		byte[] buffer = new byte[1024];
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		out.close();
		in.close();
		return out.toByteArray();
	}

	public static void main(String[] args) {
		/*
		 * HashMap<String,String> map = new HashMap<String,String>();
		 * map.put(firstLetterToUpper("EventKey"),"你好");
		 * map.put(firstLetterToUpper("Event"),"点击");
		 * 
		 * ClickMessage message = transMap2Bean(map,ClickMessage.class);
		 * System.out.println(message.getEventKey());
		 * System.out.println(message.getEvent());
		 */

	}
	
	public static void WritOutStream(HttpServletResponse response, String text) {
		ServletOutputStream outPut = null;
		try {
			outPut = response.getOutputStream();
			outPut.write(text.getBytes("UTF-8"));
			outPut.flush();
			outPut.close();
			outPut = null;
		} catch (UnsupportedEncodingException e) {
			logger.error("信息回复字节转换异常", e);
		} catch (IOException e) {
			logger.error("信息回复输出流异常", e);
		} finally {
			if (outPut != null) {
				try {
					outPut.close();
				} catch (IOException e) {
					logger.error("关闭输出流异常", e);
				}
				outPut = null;
			}
		}
	}
	
	public static String format(String unformattedXml) {
		try {
			final Document document = parseXmlFile(unformattedXml);
			OutputFormat format = new OutputFormat(document);
			format.setLineWidth(65);
			format.setIndenting(true);
			format.setIndent(2);
			Writer out = new StringWriter();
			XMLSerializer serializer = new XMLSerializer(out, format);
			serializer.serialize(document);
			return out.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	  
	public static Document parseXmlFile(String in) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(in));
			return db.parse(is);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


}
