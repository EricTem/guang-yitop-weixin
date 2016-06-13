package com.yitop.wechat.exception;

public class WxJsonException extends WxException {

	private static final long serialVersionUID = 3723997183036238665L;

	public WxJsonException(String code) {
		super(code);
	}

	public WxJsonException(String code, String msg) {
		super(code, msg);
	}


}
