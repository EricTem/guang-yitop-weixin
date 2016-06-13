package com.yitop.wechat.action;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yitop.wechat.exception.WxApiException;
import com.yitop.wechat.exception.WxJsonException;
import com.yitop.wechat.util.SessionLoggerFactory;
import com.yitop.wechat.util.Sign;

@Controller
@RequestMapping("/weixinAction")
public class WeixinAction extends BaseController{
	
	private static final Logger logger = SessionLoggerFactory.getLogger(WeixinAction.class);
	
//	@Resource
//	private IMessageService messageService;
//	
//	@Resource
//	private FxsOrderService fxsOrderService;
//	
//	@Resource
//	private FxsService fxsService;
//	
//	@Resource 
//	private OrderService orderService;
//	
//	@Resource
//	private WeixinToolKit weixinToolKit;
	
	@RequestMapping(value ="/entrance", method = RequestMethod.GET)
	public void signature(Sign sign,HttpServletResponse response){ 
		logger.info("校验签名开始..");
		
		try {
			
			logger.info("校验签名信息["+sign.getSignature()+"]["+sign.getTimestamp()+"]["+sign.getNonce()+"]");
	        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
	        if (sign.getEchostr()!=null&&!sign.getEchostr().isEmpty()) {
	        	logger.info("校验签名成功.");
	        	ServletOutputStream out = response.getOutputStream();
				out.print(sign.getEchostr());  
				out.flush();
				out.close();
	        }  
	        
		} catch (IOException e) {
			logger.info("校验签名异常.");
			e.printStackTrace();
		}  
		logger.info("校验签名结束"); 
	}
	
	@RequestMapping(value ="/entrance", method = RequestMethod.POST)
	public void command(HttpServletRequest request,HttpServletResponse response) throws WxApiException{
//		String respMessage = null;
//		BaseMessage baseMessage = null;
//		try {
//
//			// xml请求解析
//			String xmlString = new String(((TransDate)request.getAttribute("TransDate")).getRequestDate().getBady(), request.getCharacterEncoding()!=null?request.getCharacterEncoding():"UTF-8");
//			
//			Map<String, Object> requestMap = MessageUtil.getMapFromXML(xmlString);
//			// 消息类型
//			String msgType = (String) requestMap.get("MsgType");
//			
//			logger.info("消息类型:"+msgType);
//
//			// 文本消息
//			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
//				logger.info("消息类型:文本消息["+msgType+"]");
//				TextMessage baseMessageTmp = MessageUtil.getObjectFromXML(xmlString, TextMessage.class);
//				baseMessage = baseMessageTmp;
//				ConfigureManagerUtil.putLocalAppUserId(baseMessage.getToUserName());
//				respMessage = messageService.disposeTextMessage(baseMessageTmp);
//			}
//			// 图片消息
//			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
//				logger.info("消息类型:图片消息["+msgType+"]");
//				ImageMessage baseMessageTmp = MessageUtil.getObjectFromXML(xmlString, ImageMessage.class);
//				baseMessage = baseMessageTmp;
//				ConfigureManagerUtil.putLocalAppUserId(baseMessage.getToUserName());
//				respMessage = messageService.disposeImageMessage(baseMessageTmp);
//			}
//			// 地理位置消息
//			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
//				logger.info("消息类型:地理位置消息["+msgType+"]");
//				LocationMessage baseMessageTmp = MessageUtil.getObjectFromXML(xmlString, LocationMessage.class);
//				baseMessage = baseMessageTmp;
//				ConfigureManagerUtil.putLocalAppUserId(baseMessage.getToUserName());
//				respMessage = messageService.disposeLocationMessage(baseMessageTmp);
//			}
//			// 链接消息
//			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
//				logger.info("消息类型:链接消息["+msgType+"]");
//				LinkMessage baseMessageTmp = MessageUtil.getObjectFromXML(xmlString, LinkMessage.class);
//				baseMessage = baseMessageTmp;
//				ConfigureManagerUtil.putLocalAppUserId(baseMessage.getToUserName());
//				respMessage = messageService.disposeLinkMessage(baseMessageTmp);
//			}
//			// 音频消息
//			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
//				logger.info("消息类型:音频消息["+msgType+"]");
//				VoiceMessage baseMessageTmp = MessageUtil.getObjectFromXML(xmlString, VoiceMessage.class);
//				baseMessage = baseMessageTmp;
//				ConfigureManagerUtil.putLocalAppUserId(baseMessage.getToUserName());
//				respMessage = messageService.disposeVoiceMessage(baseMessageTmp);
//			}
//			// 事件推送
//			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
//				// 事件类型
//				String eventType = (String) requestMap.get("Event");
//				// 订阅
//				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
//					logger.info("事件类型:订阅["+msgType+"]["+eventType+"]");
//					if(requestMap.containsKey("Ticket")){
//						ScanAttentionMessag baseMessageTmp = MessageUtil.getObjectFromXML(xmlString, ScanAttentionMessag.class);
//						baseMessage = baseMessageTmp;
//						ConfigureManagerUtil.putLocalAppUserId(baseMessage.getToUserName());
//						messageService.disposeSubscribeMessage(baseMessageTmp);
//						respMessage = messageService.disposeScanAttentionMessag(baseMessageTmp);
//					}else{
//						SurAttentionMessag baseMessageTmp = MessageUtil.getObjectFromXML(xmlString, SurAttentionMessag.class);
//						baseMessage = baseMessageTmp;
//						ConfigureManagerUtil.putLocalAppUserId(baseMessage.getToUserName());
//						respMessage = messageService.disposeSubscribeMessage(baseMessageTmp);
//					}
//					
//				}
//				// 取消订阅
//				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
//					logger.info("事件类型:取消订阅["+msgType+"]["+eventType+"]");
//					SurAttentionMessag baseMessageTmp = MessageUtil.getObjectFromXML(xmlString, SurAttentionMessag.class);
//					baseMessage = baseMessageTmp;
//					ConfigureManagerUtil.putLocalAppUserId(baseMessage.getToUserName());
//					respMessage = messageService.disposeUnSubscribeMessage(baseMessageTmp);
//				}
//				// 自定义菜单点击事件
//				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {//http://localhost:8080/com.hotye.microWebsite/images/hotye_map.jpg
//					logger.info("事件类型:自定义事件["+msgType+"]["+eventType+"]");
//					ClickMessage baseMessageTmp = MessageUtil.getObjectFromXML(xmlString, ClickMessage.class);
//					baseMessage = baseMessageTmp;
//					ConfigureManagerUtil.putLocalAppUserId(baseMessage.getToUserName());
//					Configure configure=ConfigureManagerUtil.loadConfigure4Local();
//					respMessage = messageService.disposeClickMessage(baseMessageTmp,configure.getAppID());
//				// 微信上传地理位置
//				}else if(eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)){
//					logger.info("事件类型:微信上传地理位置["+msgType+"]["+eventType+"]");
//					LocationTransmitMessage baseMessageTmp = MessageUtil.getObjectFromXML(xmlString, LocationTransmitMessage.class);
//					baseMessage = baseMessageTmp;
//					ConfigureManagerUtil.putLocalAppUserId(baseMessage.getToUserName());
//					respMessage = messageService.disposeLocationTransmitMessage(baseMessageTmp);
//				}else if(eventType.equals(MessageUtil.EVENT_TYPE_SCAN)){
//					logger.info("事件类型:已关注扫二维码["+msgType+"]["+eventType+"]");
//					ScanAttentionMessag baseMessageTmp = MessageUtil.getObjectFromXML(xmlString, ScanAttentionMessag.class);
//					baseMessage = baseMessageTmp;
//					ConfigureManagerUtil.putLocalAppUserId(baseMessage.getToUserName());
//					respMessage = messageService.disposeScanAttentionMessag(baseMessageTmp);
//				}
//			}
//		} catch (Exception e) {
//			logger.error("信息分发异常",e);
//			String msg = "异常";
//			if (e instanceof WxException) {
//				WxException wxE = (WxException) e;
//				msg = wxE.getCode() + " : " + ExceptionEnum.toStatus(wxE.getCode());
//			} else {
//				msg = e.getMessage();
//			}
//			
//			String errorXml = "";
//			try {
//				errorXml = messageService.throwErrorTextMessage(baseMessage, msg);
//			} catch (Exception e1) {
//				logger.warn("wxapi error. " + e1.getMessage(), e1);
//			}
//			throw new WxApiException("R_01", errorXml);
//		} finally {
//			ConfigureManagerUtil.putLocalAppUserId(null);
//		}
//		
//		
//		try {
//			if(respMessage!=null) 
//			((TransDate)request.getAttribute("TransDate")).getResponseDate().setBady(respMessage.getBytes("UTF-8"));
//		} catch (UnsupportedEncodingException e) {
//			logger.error("信息返回日志输出转换异常",e);
//		}
//		
//		if (respMessage != null) {
//			response.setContentType("application/xml; charset=utf-8"); 
//			MessageUtil.WritOutStream(response, respMessage);
//		}

	}
	
	@RequestMapping(value ="/callback")
	public void payCallback(HttpServletRequest request,HttpServletResponse response) throws WxJsonException{
//		
//		ScanPayCallbackResDate re = new ScanPayCallbackResDate("FAIL","初始化未处理");
//		String appId=ConfigureManagerUtil.loadConfigure4Local().getAppID();
//		
//		try {
//			String xmlString = new String(((TransDate)request.getAttribute("TransDate")).getRequestDate().getBady(), request.getCharacterEncoding()!=null?request.getCharacterEncoding():"UTF-8");
//
//			ScanPayCallbackReqDate scanPayCallbackReqDate = MessageUtil.getObjectFromXML(xmlString, ScanPayCallbackReqDate.class);
//			
//			if(scanPayCallbackReqDate.getResult_code().equals("SUCCESS")){
//				Configure configure = ConfigureManagerUtil.loadConfigure4AppId(scanPayCallbackReqDate.getAppid());
//				ConfigureManagerUtil.putLocalAppUserId(configure.getAppUserId());
//				
//				fxsOrderService.updateOrderPaySuccess4Wx(Integer.valueOf(scanPayCallbackReqDate.getAttach()), 
//						ChannelSourceEnum.官微.getValue(), scanPayCallbackReqDate, new IBizCallback() {
//							
//							@Override
//							public void callback(Map params) {
//								String openId = (String) params.get("openId");
//								Integer orderId = (Integer) params.get("orderId");
//								String payUserName = (String) params.get("payUserName");
//								try {
//									weixinToolKit.notifyUserOrderOtherPayed(openId, orderId, payUserName);
//								} catch (WxException e) {
//									e.printStackTrace();
//								}
//							}
//						}, new IBizCallback() {
//							
//							@Override
//							public void callback(Map params) {
//								Integer msgId = (Integer) params.get("msgId");
//								String sjWxUserId = (String) params.get("sjWxUserId");
//								String noticeMsg = (String) params.get("noticeMsg");
//								Configure conf = ConfigureManagerUtil.loadConfigure4Local();
//								WxUserMsgTask wxUserInfoTask = new WxUserMsgTask(conf.getAppUserId(),sjWxUserId,
//										msgId, noticeMsg, fxsService, weixinToolKit);
//			        			ThreadUtil.addTask(wxUserInfoTask);
//							}
//						});
//			}else{
//				logger.info("支付回调接口失败：{},{}",scanPayCallbackReqDate.getResult_code(),scanPayCallbackReqDate.getReturn_msg());
//			}
//			
//		} catch (UnsupportedEncodingException e) {
//			logger.info("支付回调接口失败:{}",e.getMessage());
//			throw new WxJsonException("R_03", "信息回复输出流异常");
//		} catch (NumberFormatException | WxException e) { 
//			logger.info("支付回调接口失败:{}",e.getMessage());
//			throw new WxJsonException("R_03", "信息回复输出流异常");
//		} finally {
//			ConfigureManagerUtil.putLocalAppUserId(null);
//		}
//		
//		String respMessage = MessageUtil.getXMLFromObject(re);
//		
//		try {
//			if(respMessage!=null)
//			((TransDate)request.getAttribute("TransDate")).getResponseDate().setBady(respMessage.getBytes("UTF-8"));
//		} catch (UnsupportedEncodingException e) {
//			logger.error("信息返回日志输出转换异常",e);
//		}
//		
//		if (respMessage != null) {
//			response.setContentType("application/xml; charset=utf-8"); 
//			MessageUtil.WritOutStream(response, respMessage);
//		}

	}

}
