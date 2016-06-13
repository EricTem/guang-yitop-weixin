package com.yitop.wechat.exception;


public enum ExceptionEnum  {
	数据库异常("E001"),
	参数异常("E002"),
	
	红包已被别人领了异常("E011"),
	红包你已经领了异常("E012"),
	
	红包已被别人使用了异常("E021"),
	红包你已经使用了异常("E022"),
	
	红包规则没有生效异常("E031"),
	
	用户没关注异常("E041"),
	
	没找到渠道异常("E051"),
	渠道密码异常("E052"),
	
	APP订单无记录异常("E061"),
	APP订单已安装异常("E062"),
	
	提现余额不足问题异常("E071"),
	提现当天只能提一次异常("E072"),
	
	找不到订单("E081"),
	
	其它异常("E1000");
	
	private String code;
	
	private ExceptionEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	public static ExceptionEnum toStatus(String code) {
		ExceptionEnum[] values = ExceptionEnum.values();
		
		for (ExceptionEnum exceptionEnum : values) {
			if(exceptionEnum.getCode().equals(code)) {
				return exceptionEnum;
			}
		}
		return null;
	}
}
