package com.yitop.wechat.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.yitop.wechat.exception.ExceptionEnum;
import com.yitop.wechat.exception.WxException;
import com.yitop.wechat.exception.WxJsonException;
import com.yitop.wechat.util.MessageUtil;
import com.yitop.wechat.util.SessionLoggerFactory;

import net.sf.json.JSONObject;

public class BaseController {
	
	private static final Logger logger = SessionLoggerFactory.getLogger(BaseController.class);
	
    /** 基于@ExceptionHandler异常处理 
     * @throws WxJsonException */  
   @ExceptionHandler(WxJsonException.class)
   public void wxJsonException(HttpServletRequest request,HttpServletResponse response, WxJsonException exception) throws WxJsonException {

	   JSONObject json = new JSONObject();
	   json.put("expCode",exception.getCode());
	   json.put("expMsg", exception.getMessage());
	   logger.warn("wxJsonException() - code=" + exception.getCode() 
	   			+ ", codeMsg=" + ExceptionEnum.toStatus(exception.getCode())
	   			+ ", expMsg=" + exception.getMessage(), exception);
	   exception = null;
	   response.setContentType("application/json; charset=utf-8"); 
	   MessageUtil.WritOutStream(response, json.toString());

   }
  
//   /** 基于@ExceptionHandler异常处理 
//    * @throws WxJsonException */  
//  @ExceptionHandler(WxApiException.class)
//  public void wxApiException(HttpServletRequest request,HttpServletResponse response, WxApiException exception) throws WxJsonException {
//
//	   logger.warn("wxApiException() - code=" + exception.getCode() 
//	   			+ ", codeMsg=" + ExceptionEnum.toStatus(exception.getCode())
//	   			+ ", expMsg=" + exception.getMessage(), exception);
//	   
//	   MessageUtil.WritOutStream(response, exception.getMessage());
//	   exception = null;
//
//  }
   
   /** 基于@ExceptionHandler异常处理 */  
  @ExceptionHandler({WxException.class,Exception.class})
  public ModelAndView wxException(HttpServletRequest request, Exception exception) {
	   
	   ModelAndView mav = new ModelAndView("Other/PageException");
	   mav.addObject("expCode", exception instanceof WxException? ((WxException)exception).getCode():"9999");
	   mav.addObject("expMsg", exception.getMessage());
	   logger.warn("wxException() - code=" + mav.getModelMap().get("expCode") 
			+ ", codeMsg=" + ExceptionEnum.toStatus((String) mav.getModelMap().get("expCode"))
			+ ", expMsg=" + exception.getMessage(), exception);
	   
	   exception = null;
	   
	   return mav;
  }
}
