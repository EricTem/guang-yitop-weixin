package com.yitop.wechat.exception;

public class WxApiException extends WxException {

	private static final long serialVersionUID = 8512328437854633630L;

	public WxApiException(String code) {
		super(code);
	}

	public WxApiException(String code, String msg) {
		super(code, msg);
	}


}
