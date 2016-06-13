package com.yitop.wechat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionLoggerFactory {

	@SuppressWarnings("rawtypes")
	public static Logger getLogger(Class cls) {
		Logger logger = LoggerFactory.getLogger(cls);
		SessionLogger sessionLogger = new SessionLogger(logger);
		return sessionLogger;
	}
	
	public static Logger getLogger(String name) {
		Logger logger = LoggerFactory.getLogger(name);
		SessionLogger sessionLogger = new SessionLogger(logger);
		return sessionLogger;
	}
}
