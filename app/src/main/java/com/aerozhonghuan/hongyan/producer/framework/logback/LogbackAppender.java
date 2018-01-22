package com.aerozhonghuan.hongyan.producer.framework.logback;

import com.aerozhonghuan.foundation.log.Logger;

import org.slf4j.LoggerFactory;

/**
 * logback 实现 Logger
 * Created by zhangyunfei on 17/1/13.
 */

public class LogbackAppender implements Logger {

    @Override
    public void d(String tag, String msg) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(tag);
        logger.debug(msg);
    }

    @Override
    public void i(String tag, String msg) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(tag);
        logger.info(msg);
    }

    @Override
    public void e(String tag, String msg) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(tag);
        logger.error(msg);
    }

    @Override
    public void e(String tag, String msg, Exception ex) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(tag);
        logger.error(msg, ex);
    }

    public void w(String tag, String msg) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(tag);
        logger.warn(msg);
    }

    public void trace(String tag, String msg) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(tag);
        logger.trace(msg);
    }
}
